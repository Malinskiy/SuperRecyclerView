package co.nullindustries.ultrarecyclerview;

public interface OnMoreListener {
    /**
     * @param overallItemsCount overallItemsCount
     * @param itemsBeforeMore itemsBeforeMore
     * @param maxLastVisiblePosition for staggered grid this is max of all spans
     */
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition);
}
