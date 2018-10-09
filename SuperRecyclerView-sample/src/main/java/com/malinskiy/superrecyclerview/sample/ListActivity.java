package com.malinskiy.superrecyclerview.sample;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ListActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vertical_sample;
    }

    @Override
    protected boolean isSwipeToDismissEnabled() {
        return true;
    }

    @Override
    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}