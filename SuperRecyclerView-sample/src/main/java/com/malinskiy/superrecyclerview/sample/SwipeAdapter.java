package com.malinskiy.superrecyclerview.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.malinskiy.superrecyclerview.swipe.SwipeLayout;

import java.util.ArrayList;

public class SwipeAdapter extends BaseSwipeAdapter<SwipeAdapter.ViewHolder> {

    private ArrayList<String> mData;

    public SwipeAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.item_swipeable, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        SwipeLayout swipeLayout = viewHolder.swipeLayout;
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(viewHolder.getPosition());
                Toast.makeText(v.getContext(), "Deleted " + viewHolder.getPosition(), Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(String string) {
        insert(string, mData.size());
    }

    public void insert(String string, int position) {
        closeAllExcept(null);

        mData.add(position, string);

        notifyItemInserted(position);
    }

    public void remove(int position) {
        mData.remove(position);

        closeItem(position);

        notifyItemRemoved(position);
    }

    public static class ViewHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder {
        public TextView textView;
        public Button   deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.position);
            deleteButton = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
