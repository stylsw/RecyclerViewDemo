package com.fingertip.recyclerviewdemo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.fingertip.recyclerviewdemo.adapter.RecyclerDividerItemDecoration;
import com.fingertip.recyclerviewdemo.adapter.RecyclerListViewAdapter;
import com.fingertip.recyclerviewdemo.adapter.viewholder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sweet on 2016/11/25.
 */

public class RecyclerGridViewActivity extends AppCompatActivity {

    private RecyclerView mRecycleGv;
    private List mList;
    private RecyclerView.Adapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_gridview);
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList();
        for(int i = 0; i < 30 ; i ++){
            mList.add(R.drawable.a);
        }
    }

    private void initView() {
        mRecycleGv = (RecyclerView) findViewById(R.id.mRecycleGv);
        mRecycleGv.setLayoutManager(new GridLayoutManager(this,3));

        RecyclerDividerItemDecoration dividerItemDecoration = new RecyclerDividerItemDecoration(this,RecyclerDividerItemDecoration.DIVIDER_ALL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider_bg));
        mRecycleGv.addItemDecoration(dividerItemDecoration);

        mAdapter = new RecyclerListViewAdapter<Integer>(this, mList,R.layout.item_recycle_listview2) {
            @Override
            public void onBindItemView(RecyclerViewHolder recyclerViewHolder, Integer data, int position) {
                ImageView mIv = recyclerViewHolder.findViewById(R.id.mIv);
                mIv.setImageResource(data);
            }
        };
        mRecycleGv.setAdapter(mAdapter);
    }
}
