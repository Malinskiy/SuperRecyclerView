package com.malinskiy.superrecyclerview.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeItemManagerInterface;

import java.util.ArrayList;

public class SwipeActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    private SuperRecyclerView mRecycler;
    private SwipeAdapter      mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_sample);
        mRecycler = (SuperRecyclerView) findViewById(R.id.list);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> list = new ArrayList<>();
        mAdapter = new SwipeAdapter(list);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setMode(SwipeItemManagerInterface.Mode.Single);
        mRecycler.addOnItemTouchListener(new RecyclerUtils.RecyclerItemClickListener(this, new RecyclerUtils.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SwipeActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
            }
        }));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecycler.setAdapter(mAdapter);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.add("More stuff");
                        mAdapter.add("More stuff");
                        mAdapter.add("More stuff");
                    }
                });
            }
        });
        thread.start();

        mRecycler.setRefreshListener(this);
        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRecycler.setupMoreListener(this, 1);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();

        mAdapter.closeAllExcept(null);

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
}
