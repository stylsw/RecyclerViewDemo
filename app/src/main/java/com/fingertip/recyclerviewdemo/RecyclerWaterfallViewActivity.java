package com.fingertip.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fingertip.recyclerviewdemo.adapter.RecyclerListViewAdapter;
import com.fingertip.recyclerviewdemo.adapter.viewholder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sweet on 2016/11/25.
 */

public class RecyclerWaterfallViewActivity extends AppCompatActivity {
    private RecyclerView mRecycleWv;
    private List mList;
    private RecyclerView.Adapter mAdapter;
    private List<Integer> mHeights;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_waterfallview);
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList();
        for (int i = 0; i < 50; i++) {
            mList.add(R.drawable.a);
        }
        mHeights = new ArrayList();
        for (int i = 0 ; i < mList.size() ; i++){
            mHeights.add( (int) (100 + Math.random() * 300));
        }
    }

    private void initView() {
        mRecycleWv = (RecyclerView) findViewById(R.id.mRecycleWv);
        mRecycleWv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRecycleWv.addItemDecoration(mDividerItemDecoration);
        mAdapter = new RecyclerListViewAdapter<Integer>(this, mList,R.layout.item_recycle_listview2) {
            @Override
            public void onBindItemView(RecyclerViewHolder recyclerViewHolder, Integer data, int position) {
                ImageView mIv = recyclerViewHolder.findViewById(R.id.mIv);
                mIv.setImageResource(data);
                ViewGroup.LayoutParams lp =  mIv.getLayoutParams();
                lp.height = mHeights.get(position);
                mIv.setLayoutParams(lp);
            }
        };
        mRecycleWv.setAdapter(mAdapter);
    }
}
