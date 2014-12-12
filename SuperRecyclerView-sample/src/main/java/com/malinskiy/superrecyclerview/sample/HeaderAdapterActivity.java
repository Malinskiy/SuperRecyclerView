package com.malinskiy.superrecyclerview.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class HeaderAdapterActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private SuperRecyclerView mRecycler;

    private boolean rock = true;
    private StringListAdapter           mAdapter;
    private String[]                    rocks;
    private String[]                    classics;
    private StickyHeadersItemDecoration top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sample);

        mRecycler = (SuperRecyclerView) findViewById(R.id.list);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setRefreshListener(this);
        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        ArrayList<String> data = new ArrayList<>();
        rocks = getResources().getStringArray(R.array.rocks);
        classics = getResources().getStringArray(R.array.classics);
        data.addAll(Arrays.asList(rocks));
        mAdapter = new StringListAdapter(data);
        top = new StickyHeadersBuilder()
                .setAdapter(mAdapter)
                .setRecyclerView(mRecycler.getRecyclerView())
                .setStickyHeadersAdapter(new InitialHeaderAdapter(data))
                .build();

        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(top);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();
        mAdapter.clear();
        mAdapter.addAll(rock ? classics : rocks);
        rock = !rock;
    }
}
