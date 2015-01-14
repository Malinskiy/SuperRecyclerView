package com.malinskiy.superrecyclerview.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.ViewHolder> {

    public ArrayList<String> data;

    public StringListAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public StringListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StringListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(String string) {
        insert(string, data.size());
    }

    public void insert(String string, int position) {
        data.add(position, string);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(String[] strings) {
        int startIndex = data.size();
        data.addAll(startIndex, Arrays.asList(strings));
        notifyItemRangeInserted(startIndex, strings.length);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
