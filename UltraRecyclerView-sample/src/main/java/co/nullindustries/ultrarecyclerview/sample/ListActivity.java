package co.nullindustries.ultrarecyclerview.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


public class ListActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.button).setOnClickListener(this);
    }

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

    @Override
    public void onClick(View v) {
        startAddingItems();
    }

}