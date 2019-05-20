package com.malinskiy.superrecyclerview.sample;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

public class BehaviorActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_behavior;
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