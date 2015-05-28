package co.nullindustries.ultrarecyclerview.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import co.nullindustries.ultrarecyclerview.OnMoreListener;
import co.nullindustries.ultrarecyclerview.UltraRecyclerView;
import co.nullindustries.ultrarecyclerview.swipe.SparseItemRemoveAnimator;
import co.nullindustries.ultrarecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

public abstract class BaseActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {

    private UltraRecyclerView mRecycler;
    private StringListAdapter mAdapter;
    private SparseItemRemoveAnimator mSparseAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ArrayList<String> list = new ArrayList<>();
        mAdapter = new StringListAdapter(list);

        mRecycler = (UltraRecyclerView) findViewById(R.id.list);
        mRecycler.setLayoutManager(getLayoutManager());
        mRecycler.setAdapter(mAdapter);

        boolean dismissEnabled = isSwipeToDismissEnabled();
        if (dismissEnabled) {
            mRecycler.setSwipeToDismiss(this);
            mSparseAnimator = new SparseItemRemoveAnimator();
            mRecycler.getRecyclerView().setItemAnimator(mSparseAnimator);
        }

        mRecycler.setRefreshListener(this);
        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRecycler.setMoreListener(this, 1);
    }

    protected void startAddingItems() {
        mAdapter.add("More stuff");
        mAdapter.add("More stuff");
        mAdapter.add("More stuff");
    }

    protected abstract int getLayoutId();

    protected abstract boolean isSwipeToDismissEnabled();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mAdapter.insert("New stuff", 0);
            }
        }, 2000);
    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        Toast.makeText(this, "More", Toast.LENGTH_LONG).show();

        mAdapter.add("More asked, more served");
    }

    @Override
    public boolean canDismiss(int position) {
        return true;
    }

    @Override
    public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mSparseAnimator.setSkipNext(true);
            mAdapter.remove(position);
        }
    }
}
