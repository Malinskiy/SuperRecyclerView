package com.malinskiy.superrecyclerview.swipe;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

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
