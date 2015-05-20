package co.nullindustries.ultrarecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import co.nullindustries.ultrarecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

public class UltraRecyclerView extends FrameLayout {

    public enum LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    //<editor-fold desc="Attributes">

    protected RecyclerView mRecyclerView;
    protected ViewStub mProgressViewStub;
    protected ViewStub mMoreProgressViewStub;
    protected ViewStub mEmptyViewStub;
    protected View mProgressView;
    protected View mMoreProgressView;
    protected View mEmptyView;

    protected int mEmptyId;
    protected int mMoreProgressId;
    private int mProgressId;

    protected boolean mClipToPadding;
    protected int mPadding;
    protected int loadMoreItemsCount = 10;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mScrollbarStyle;
    protected boolean isLoadingMore;
    protected int mSuperRecyclerViewMainLayout;

    protected LayoutManagerType layoutManagerType;
    protected RecyclerView.OnScrollListener mInternalOnScrollListener;
    protected RecyclerView.OnScrollListener mExternalOnScrollListener;
    private RecyclerView.OnScrollListener mSwipeDismissScrollListener;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver;
    protected OnMoreListener mOnMoreListener;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    //</editor-fold>

    //<editor-fold desc="Initializers">

    public UltraRecyclerView(Context context) {
        super(context);
        initView();
    }

    public UltraRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public UltraRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.superrecyclerview);
        try {
            mSuperRecyclerViewMainLayout = a.getResourceId(R.styleable.superrecyclerview_mainLayoutId, R.layout.layout_progress_recyclerview);
            mClipToPadding = a.getBoolean(R.styleable.superrecyclerview_recyclerClipToPadding, false);
            mPadding = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPadding, -1.0f);
            mPaddingTop = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingRight, 0.0f);
            mScrollbarStyle = a.getInt(R.styleable.superrecyclerview_scrollbarStyle, -1);
            mEmptyId = a.getResourceId(R.styleable.superrecyclerview_layout_empty, 0);
            mMoreProgressId = a.getResourceId(R.styleable.superrecyclerview_layout_moreProgress, R.layout.layout_more_progress);
            mProgressId = a.getResourceId(R.styleable.superrecyclerview_layout_progress, R.layout.layout_progress);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        View layout = LayoutInflater.from(getContext()).inflate(mSuperRecyclerViewMainLayout, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.ptr_layout);
        mSwipeRefreshLayout.setEnabled(false);

        mProgressViewStub = (ViewStub) layout.findViewById(android.R.id.progress);
        mProgressViewStub.setLayoutResource(mProgressId);
        mProgressView = mProgressViewStub.inflate();

        mMoreProgressViewStub = (ViewStub) layout.findViewById(R.id.more_progress);
        mMoreProgressViewStub.setLayoutResource(mMoreProgressId);
        if (mMoreProgressId != 0) mMoreProgressView = mMoreProgressViewStub.inflate();
        mMoreProgressViewStub.setVisibility(View.GONE);

        mEmptyViewStub = (ViewStub) layout.findViewById(R.id.empty);
        mEmptyViewStub.setLayoutResource(mEmptyId);
        if (mEmptyId != 0) mEmptyView = mEmptyViewStub.inflate();
        mEmptyViewStub.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list);
        if (mRecyclerView != null) {
            mRecyclerView.setClipToPadding(mClipToPadding);
            mInternalOnScrollListener = new RecyclerView.OnScrollListener() {
                private int[] lastPositions;

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();

                    int lastVisibleItemPosition = -1;
                    if (layoutManagerType == null) {
                        if (layoutManager instanceof LinearLayoutManager) {
                            layoutManagerType = LayoutManagerType.LINEAR;
                        } else if (layoutManager instanceof GridLayoutManager) {
                            layoutManagerType = LayoutManagerType.GRID;
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            layoutManagerType = LayoutManagerType.STAGGERED_GRID;
                        } else {
                            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                        }
                    }

                    switch (layoutManagerType) {
                        case LINEAR:
                            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                            break;
                        case GRID:
                            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                            break;
                        case STAGGERED_GRID:
                            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                            if (lastPositions == null)
                                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];

                            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                            lastVisibleItemPosition = findMax(lastPositions);
                            break;
                    }

                    if (((totalItemCount - lastVisibleItemPosition) <= loadMoreItemsCount ||
                            (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)
                            && !isLoadingMore) {

                        isLoadingMore = true;
                        if (mOnMoreListener != null) {
                            mMoreProgressViewStub.setVisibility(View.VISIBLE);
                            mOnMoreListener.onMoreAsked(mRecyclerView.getAdapter().getItemCount(), loadMoreItemsCount, lastVisibleItemPosition);

                        }
                    }

                    if (mExternalOnScrollListener != null)
                        mExternalOnScrollListener.onScrolled(recyclerView, dx, dy);
                    if (mSwipeDismissScrollListener != null)
                        mSwipeDismissScrollListener.onScrolled(recyclerView, dx, dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (mExternalOnScrollListener != null)
                        mExternalOnScrollListener.onScrollStateChanged(recyclerView, newState);
                    if (mSwipeDismissScrollListener != null)
                        mSwipeDismissScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            };
            mRecyclerView.setOnScrollListener(mInternalOnScrollListener);

            if (mPadding != -1.0f) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }

            if (mScrollbarStyle != -1) {
                mRecyclerView.setScrollBarStyle(mScrollbarStyle);
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="Setters/Getters">

    /**
     * Set the adapter to the recycler
     * If adapter is empty, then the emptyview is shown
     *
     * @param adapter adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);

        if (adapter == null) {
            manageEmptyViewVisibility();
            return;
        }

        if (mAdapterDataObserver == null) {
            mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    update();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    update();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    update();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    update();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    update();
                }

                private void update() {
                    isLoadingMore = false;
                    hideProgressViews();
                    manageEmptyViewVisibility();
                }
            };
        }
        adapter.registerAdapterDataObserver(mAdapterDataObserver);
    }

    public SwipeRefreshLayout getSwipeToRefresh() {
        return mSwipeRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void setSwipeToDismiss(final SwipeDismissRecyclerViewTouchListener.DismissCallbacks listener) {
        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(mRecyclerView, new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return listener.canDismiss(position);
                    }

                    @Override
                    public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        listener.onDismiss(recyclerView, reverseSortedPositions);
                    }
                });
        mSwipeDismissScrollListener = touchListener.makeScrollListener();
        mRecyclerView.setOnTouchListener(touchListener);
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setRefreshingColorResources(@ColorRes int colRes1, @ColorRes int colRes2, @ColorRes int colRes3, @ColorRes int colRes4) {
        mSwipeRefreshLayout.setColorSchemeResources(colRes1, colRes2, colRes3, colRes4);
    }

    public void setRefreshingColor(int col1, int col2, int col3, int col4) {
        mSwipeRefreshLayout.setColorSchemeColors(col1, col2, col3, col4);
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListener = listener;
    }

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.addOnItemTouchListener(listener);
    }

    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.removeOnItemTouchListener(listener);
    }

    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    /**
     * Sets the More listener
     *
     * @param onMoreListener listener
     * @param max            Number of items before loading more
     */
    public void setMoreListener(OnMoreListener onMoreListener, int max) {
        mOnMoreListener = onMoreListener;
        loadMoreItemsCount = max;
    }

    public void setOnMoreListener(OnMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    public void setNumberBeforeMoreIsCalled(int max) {
        loadMoreItemsCount = max;
    }

    /**
     * Enable/Disable the More event
     *
     * @param isLoadingMore boolean
     */
    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    public void removeMoreListener() {
        mOnMoreListener = null;
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mRecyclerView.setOnTouchListener(listener);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecyclerView.addItemDecoration(itemDecoration, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.removeItemDecoration(itemDecoration);
    }

    public View getProgressView() {
        return mProgressView;
    }

    public View getMoreProgressView() {
        return mMoreProgressView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public boolean isRefresing(){
        return mSwipeRefreshLayout.isRefreshing();
    }
    //</editor-fold>

    //<editor-fold desc="Visibility">

    /**
     * Show the progressbar
     */
    public void showProgressBar() {
        if (mEmptyId != 0) mEmptyViewStub.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressViewStub.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progressbar and show the recycler
     */
    public void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressViewStub.setVisibility(View.GONE);
    }

    public void showMoreProgress() {
        mMoreProgressViewStub.setVisibility(View.VISIBLE);

    }

    public void hideMoreProgress() {
        mMoreProgressViewStub.setVisibility(View.GONE);

    }

    /**
     * Hide the progressbar
     */
    public void hideProgressViews() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressViewStub.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void manageEmptyViewVisibility() {
        if (mEmptyId != 0 && mRecyclerView.getAdapter().getItemCount() == 0) {
            mEmptyViewStub.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hide the recycler
     */
    public void hideRecycler() {
        mRecyclerView.setVisibility(View.GONE);
    }

    //</editor-fold>

    private int findMax(int[] values) {
        int max = Integer.MIN_VALUE;
        for (int value : values) {
            if (value > max)
                max = value;
        }
        return max;
    }

    /**
     * Remove the adapter from the recycler and clear all the listeners
     */
    public void clear() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter != null && mAdapterDataObserver != null) {
            adapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        }

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(null);
        }

        mRecyclerView.setAdapter(null);
    }
}
