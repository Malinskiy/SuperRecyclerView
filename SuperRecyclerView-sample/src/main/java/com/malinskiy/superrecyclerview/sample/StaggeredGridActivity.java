package com.malinskiy.superrecyclerview.sample;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class StaggeredGridActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_sample;
    }

    @Override
    protected boolean isSwipeToDismissEnabled() {
        return false;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
    }
}
