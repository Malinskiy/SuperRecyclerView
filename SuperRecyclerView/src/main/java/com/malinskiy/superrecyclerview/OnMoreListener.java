package com.malinskiy.superrecyclerview;

public interface OnMoreListener {
    /**
     * @param overallItemsCount
     * @param itemsBeforeMore
     * @param maxLastVisiblePosition for staggered grid this is max of all spans
     */
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition);
}
