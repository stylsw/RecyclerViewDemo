package com.fingertip.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fingertip.recyclerviewdemo.adapter.viewholder.RecyclerViewHolder;

import java.util.List;

/**
 * Created by sweet on 2016/11/25.
 */

public abstract class RecyclerListViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    protected int mItemLayoutId;
    private SparseArray<Class> mItemTypeArray;

    public RecyclerListViewAdapter(Context mContext, List<T> mDatas, int mItemLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemLayoutId = mItemLayoutId;
    }
    public RecyclerListViewAdapter(Context mContext, List<T> mDatas, ItemType... mItemTypes) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mItemTypeArray = new SparseArray<Class>();
        for(ItemType tempItemType : mItemTypes)
        {
            mItemTypeArray.put(tempItemType.getItemLayoutId(),tempItemType.getDataClass());
        }
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemLayoutId = null == mItemTypeArray ? mItemLayoutId : viewType;
        return RecyclerViewHolder.createViewHolder(mContext,parent,itemLayoutId);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position, List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }else{
            //TODO: 2016/11/26  刷新单个Item回调
        }
    }

    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        onBindItemView(holder,mDatas.get(position),position);

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener == null)
                    return;
                mOnItemClickListener.onItemClick(holder,view,position);
            }
        });

        holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mOnItemLongClickListener == null)
                    return false;
                mOnItemLongClickListener.onItemLongClick(holder,view,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void onBindItemView(RecyclerViewHolder recyclerViewHolder, T data, int position);


        @Override
    public int getItemViewType(int position) {
        if(null == mItemTypeArray)
            return super.getItemViewType(position);

        return mItemTypeArray.keyAt(mItemTypeArray.indexOfValue(mDatas.get(position).getClass()));
    }

    public interface OnItemClickListener{
        void onItemClick(RecyclerViewHolder recyclerViewHolder, View itemView, int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(RecyclerViewHolder recyclerViewHolder, View itemView, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void addItem(T t){
        addItem(mDatas.size(),t);
    }

    public void addItem(int position ,T t){
        mDatas.add(position,t);
        notifyItemInserted(position);
    }

    public void deleteItem(T t){
        deleteItem(mDatas.indexOf(t));
    }

    public void deleteItem(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
    public void updateItem(int position){
        notifyItemChanged(position);
    }
    public void updateItem(int position,T t){
        mDatas.remove(position);
        mDatas.add(position,t);
        notifyItemChanged(position,"update");
    }
    public void refreshData(List<T> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }
    public static class ItemType {
        private int mItemLayoutId;
        private Class mDataClass;

        public ItemType(int mItemLayoutId, Class mDataClass) {
            this.mItemLayoutId = mItemLayoutId;
            this.mDataClass = mDataClass;
        }

        public int getItemLayoutId() {
            return mItemLayoutId;
        }

        public void setItemLayoutId(int mItemLayoutId) {
            this.mItemLayoutId = mItemLayoutId;
        }

        public Class getDataClass() {
            return mDataClass;
        }

        public void setDataClass(Class mDataClass) {
            this.mDataClass = mDataClass;
        }

        @Override
        public String toString() {
            return "ItemType{" +
                    "mItemLayoutId=" + mItemLayoutId +
                    ", mDataClass=" + mDataClass +
                    '}';
        }
    }
}
