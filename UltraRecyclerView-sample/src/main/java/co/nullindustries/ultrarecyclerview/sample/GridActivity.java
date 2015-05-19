package co.nullindustries.ultrarecyclerview.sample;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


public class GridActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_vertical_sample;
    }

    @Override
    protected boolean isSwipeToDismissEnabled() {
        return false;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(this, 3);
    }
}
