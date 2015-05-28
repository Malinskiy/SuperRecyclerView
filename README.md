##Description

This is an attempt to make RecyclerView easier to use.

Features built in:
- ProgressBar while adapter hasn't been set
- EmptyView if adapter is empty
- SwipeRefreshLayout (Google's one)
- Infinite scrolling, when you reach the X last item, load more of them.
- Swipe To Dismiss or Swipe To Remove
- Sticky headers (via [Eowise][eowise-sticky-headers], see sample)

Please check [CHANGELOG](CHANGELOG.md) for updates.

##Integration *GRADLE*

```
dependencies {
    compile 'co.nullindustries:ultrarecyclerview:1.1.1'
}
```

##Usage

-	Use directly UltraRecyclerView:

```xml
   <co.nullindustries.ultrarecyclerview.UltraRecyclerView
            android:id="@+id/list"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_empty="@layout/emptyview"
            app:layout_moreProgress="@layout/view_more_progress"
            app:mainLayoutId="@layout/layout_recyclerview_verticalscroll"
            app:recyclerClipToPadding="false"
            app:recyclerPadding="16dp"
            app:scrollbarStyle="insideInset"/>
```

-   Current Attributes supported:
```xml
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

##UltraRecyclerView Java Usage

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

##Scrollbars
RecyclerView currently doesn't support setting scrollbars from code that's why I can't parse custom attributes.
You have to use appropriate `mainLayoutId` attribute to have scrollbars visible.

### Vertical scrollbars
```xml
<co.nullindustries.ultrarecyclerview.UltraRecyclerView
     app:mainLayoutId="@layout/layout_recyclerview_verticalscroll"
     app:scrollbarStyle="insideInset"
     .../>
```

### Horizontal scrollbars
```xml
<co.nullindustries.ultrarecyclerview.UltraRecyclerView
     app:mainLayoutId="@layout/layout_recyclerview_horizontalscroll"
     app:scrollbarStyle="insideInset"
     .../>
```

##Swipe layout
1. You should always assign ```android:id``` to ```@id/recyclerview_swipe```. Note that it's a reference to an already defined id, so don't use ```@+id```

2. The `SwipeLayout` can only have 2 children that are instances of [`ViewGroup`](http://developer.android.com/reference/android/view/ViewGroup.html), e.g. `LinearLayout`, `RelativeLayout`, `GridLayout`

3. The first child is the bottom view, the second child is the top view

4. Your adapter should extend `BaseSwipeAdapter` class

5. Do not setup swipe to dismiss and use swipe layout at the same time. If you do - a kitten will cry somewhere.

Item example:

```xml
<co.nullindustries.ultrarecyclerview.swipe.SwipeLayout
    android:id="@id/recyclerview_swipe"
    xmlns:swipe="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    swipe:horizontalSwipeOffset="16dp">

    <LinearLayout
        ...
    </LinearLayout>

    <LinearLayout
        ...
    </LinearLayout>

</co.nullindustries.ultrarecyclerview.swipe.SwipeLayout>
```

Custom attributes supported:
```xml
    <attr name="drag_edge" format="enum">
        <enum name="left" value="0"/>
        <enum name="right" value="1"/>
        <enum name="top" value="2"/>
        <enum name="bottom" value="3"/>
    </attr>
    <attr name="horizontalSwipeOffset" format="dimension"/>
    <attr name="verticalSwipeOffset" format="dimension"/>
    <attr name="show_mode" format="enum">
        <enum name="lay_down" value="0"/>
        <enum name="pull_out" value="1"/>
    </attr>
```

# Commit Format

Commit comments are composed by: `type` `<empty_space>` `message`.

Types:
* :muscle: : UPDATE
* :neckbeard: : ADD 
* :shipit: : REMOVE
* :clap:: : FIX
* :umbrella: : CLEAN

Example:

`:muscle: change the dialogs style`: means there was an update over the dialogs style
`:neckbeard: new dialogs library`: means you added something
`:clap: showing dialogs crash`: means that you fixed something
`:clap: showing dialogs crash`: means that you fixed something
`:clap: showing dialogs crash`: means that you fixed something


####[Sample java][sample java]

##Thanks
[Malinskiy](https://github.com/Malinskiy/UltraRecyclerView) for the base version of this library.

[Jake Warthon][jake-swipe-to-dismiss] for implementation of SwipeToDismiss via NineOldAndroids.

[Eowise][eowise-sticky-headers] for implementation of sticky headers.

[Quentin Dommerc][superlistview] for inspiration.

[代码家][swipelayout] for swipe layout implementation.

##License

    Copyright (c) 2015 Null Industries

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

[sample java]:https://github.com/nullindustries/UltraRecyclerView/blob/master/UltraRecyclerView-sample/src/main/java/co/nullindustries/superrecyclerview/sample/BaseActivity.java
[jake-swipe-to-dismiss]:https://github.com/JakeWharton/SwipeToDismissNOA
[eowise-sticky-headers]:https://github.com/eowise/recyclerview-stickyheaders
[superlistview]:https://github.com/dommerq/SuperListview
[swipelayout]:https://github.com/daimajia/AndroidSwipeLayout
