package com.fingertip.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingertip.recyclerviewdemo.adapter.RecyclerDividerItemDecoration;
import com.fingertip.recyclerviewdemo.adapter.RecyclerListViewAdapter;
import com.fingertip.recyclerviewdemo.adapter.viewholder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sweet on 2016/11/25.
 */

public class RecyclerListViewActivity extends AppCompatActivity {

    private RecyclerView mRecycleLv;
    private List<Object> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_listview);
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 'a'; i < 'z'; i++) {
            mList.add((char) i + "");
//            mList.add(R.mipmap.ic_launcher);
        }
    }

    private void initView() {
        mRecycleLv = (RecyclerView) findViewById(R.id.mRecycleLv);

//        setVertcalView();
        setHorizontalView();

        mRecycleLv.setAdapter(new RecyclerListViewAdapter<Object>(this, mList, new RecyclerListViewAdapter.ItemType(R.layout.item_recycle_listview1, String.class), new RecyclerListViewAdapter.ItemType(R.layout.item_recycle_listview2, Integer.class)) {
            @Override
            public void onBindItemView(RecyclerViewHolder recyclerViewHolder, Object data, int position) {
                switch (recyclerViewHolder.getItemViewType()) {
                    case R.layout.item_recycle_listview1:
                        TextView mTv = recyclerViewHolder.findViewById(R.id.mTv);
                        mTv.setText((String) data);
                        break;
                    case R.layout.item_recycle_listview2:
                        ImageView mIv = recyclerViewHolder.findViewById(R.id.mIv);
                        mIv.setImageResource((int) data);
                        break;
                }
            }
        });

    }

    private void setVertcalView() {
        mRecycleLv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false
        ));
        RecyclerDividerItemDecoration dividerItemDecoration = new RecyclerDividerItemDecoration(this, RecyclerDividerItemDecoration.DIVIDER_VERTICAL);
        dividerItemDecoration.setDrawableResource(R.drawable.divider_bg);
        dividerItemDecoration.setMarginLeft(15).setMarginRight(10);
        mRecycleLv.addItemDecoration(dividerItemDecoration);
    }

    private void setHorizontalView() {
        mRecycleLv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false
        ));
        RecyclerDividerItemDecoration dividerItemDecoration = new RecyclerDividerItemDecoration(this, RecyclerDividerItemDecoration.DIVIDER_HORIZONTAL);
        dividerItemDecoration.setDrawableResource(R.drawable.divider_bg);
        dividerItemDecoration.setMarginTop(30).setMarginBottom(10);
        mRecycleLv.addItemDecoration(dividerItemDecoration);
    }

}
