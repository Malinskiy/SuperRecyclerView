package com.malinskiy.superrecyclerview.sample;

import android.support.v7.widget.LinearLayoutManager;

public class ListActivity extends BaseActivity {

    @Override
    protected boolean isSwipeToDismissEnabled() {
        return true;
    }

    @Override
    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}