package com.malinskiy.superrecyclerview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        findViewById(R.id.button_list_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_grid_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GridActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_staggered_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StaggeredGridActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_swipe_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SwipeActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_header_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HeaderAdapterActivity.class);
                startActivity(intent);
            }
        });
    }
}
