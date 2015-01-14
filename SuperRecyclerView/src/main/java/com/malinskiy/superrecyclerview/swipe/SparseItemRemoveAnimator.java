package com.malinskiy.superrecyclerview.swipe;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

public class SparseItemRemoveAnimator extends DefaultItemAnimator {

    private boolean skipNext = false;

    public void setSkipNext(boolean skipNext) {
        this.skipNext = skipNext;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        if (!skipNext) {
            return super.animateRemove(holder);
        } else {
            dispatchRemoveFinished(holder);
            skipNext = false;
            return false;
        }
    }
}
