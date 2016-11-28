package com.fingertip.recyclerviewdemo.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sweet on 2016/11/25.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mItemView;
    private Context mContext;

    public static RecyclerViewHolder createViewHolder(Context context,
                                                      ViewGroup parent, int itemLayoutId) {
        View itemView = LayoutInflater.from(context).inflate(itemLayoutId, parent,
                false);
        RecyclerViewHolder holder = new RecyclerViewHolder(context,itemView);
        return holder;
    }

    private RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mItemView = itemView;
        mViews = new SparseArray<>();
    }

    public <T extends View> T  findViewById(int resViewId) {
        View view = mViews.get(resViewId);
        if (view == null)
        {
            view = mItemView.findViewById(resViewId);
            mViews.put(resViewId, view);
        }
        return (T) view;
    }

    public View getItemView() {
        return mItemView;
    }


}
