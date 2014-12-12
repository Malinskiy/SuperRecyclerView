package com.malinskiy.superrecyclerview.sample;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class StaggeredGridActivity extends BaseActivity {
    @Override
    protected boolean isSwipeToDismissEnabled() {
        return false;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
    }
}
