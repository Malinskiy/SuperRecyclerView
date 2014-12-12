package com.malinskiy.superrecyclerview.sample;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GridActivity extends BaseActivity {
    @Override
    protected boolean isSwipeToDismissEnabled() {
        return false;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(this, 3);
    }
}
