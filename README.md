SuperRecyclerView
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.malinskiy/superrecyclerview/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.malinskiy/superrecyclerview) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SuperRecyclerView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1239)
==============

##Description

This is an attempt to make RecyclerView easier to use.

Features built in:
- ProgressBar while adapter hasn't been set
- EmptyView if adapter is empty
- SwipeRefreshLayout (Google's one)
- Infinite scrolling, when you reach the X last item, load more of them.
- Swipe To Dismiss
- Sticky headers (via [Eowise][eowise-sticky-headers], see sample)

##Integration

Just add it to you dependencies

```
    compile 'com.malinskiy:superrecyclerview:1.0.0'
```

##Usage

-	Use directly SuperRecyclerView:

```xml
   <com.malinskiy.superrecyclerview.SuperRecyclerView
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:recyclerClipToPadding="false"
            app:layout_empty="@layout/emptyview"
            app:layout_moreProgress="@layout/view_more_progress"
            app:scrollbarStyle="outsideOverlay" >
    </com.malinskiy.superrecyclerview.SuperRecyclerView>
```

-   Current Attributes supported:
```xml
        <attr name="layout_recyclerSelector" format="reference"/>
        <attr name="layout_empty" format="reference"/>
        <attr name="layout_moreProgress" format="reference"/>
        <attr name="layout_progress" format="reference"/>
        <attr name="recyclerClipToPadding" format="boolean"/>
        <attr name="recyclerPadding" format="dimension"/>
        <attr name="recyclerPaddingTop" format="dimension"/>
        <attr name="recyclerPaddingBottom" format="dimension"/>
        <attr name="recyclerPaddingLeft" format="dimension"/>
        <attr name="recyclerPaddingRight" format="dimension"/>
        <attr name="scrollbarStyle">
            <flag name="insideOverlay" value="0x0"/>
            <flag name="insideInset" value="0x01000000"/>
            <flag name="outsideOverlay" value="0x02000000"/>
            <flag name="outsideInset" value="0x03000000"/>
        </attr>

        <attr name="mainLayoutId" format="reference"/>
```

##SuperRecyclerView Java Usage

```java
    recycler.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener {
      @Override
      public void onRefresh() {
          // Do your refresh
      });

    // when there is only 10 items to see in the recycler, this is triggered
    recycler.setupMoreListener(new OnMoreListener() {
      @Override
      public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        // Fetch more from Api or DB
      }}, 10);

    recycler.setupSwipeToDismiss(new SwipeDismissListViewTouchListener.DismissCallbacks() {
      @Override
      public boolean canDismiss(int position) {
        return true
      }

      @Override
      public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
        // Do your stuff like call an Api or update your db
      }});

```


####[Sample java][sample java]

##Thanks
[Jake Warthon][jake-swipe-to-dismiss] for implementation of SwipeToDismiss via NineOldAndroids

[Eowise][eowise-sticky-headers] for implementation of sticky headers

[Quentin Dommerc][superlistview] for inspiration


##License

    Copyright (c) 2014 Anton Malinskiy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    Come on, don't tell me you read that.

[sample java]:https://github.com/Malinskiy/SuperRecyclerView/blob/master/SuperRecyclerView-sample/src/main/java/com/malinskiy/superrecyclerview/sample/BaseActivity.java
[jake-swipe-to-dismiss]:https://github.com/JakeWharton/SwipeToDismissNOA
[eowise-sticky-headers]:https://github.com/eowise/recyclerview-stickyheaders
[superlistview]:https://github.com/dommerq/SuperListview
