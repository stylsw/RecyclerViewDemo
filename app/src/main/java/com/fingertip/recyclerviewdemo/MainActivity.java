package com.fingertip.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mRecycleListViewBtn;
    private Button mRecycleGridViewBtn;
    private Button mRecycleWaterfallViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mRecycleListViewBtn = (Button) findViewById(R.id.mRecycleListViewBtn);
        mRecycleGridViewBtn = (Button) findViewById(R.id.mRecycleGridViewBtn);
        mRecycleWaterfallViewBtn = (Button) findViewById(R.id.mRecycleWaterfallViewBtn);

        mRecycleListViewBtn.setOnClickListener(this);
        mRecycleGridViewBtn.setOnClickListener(this);
        mRecycleWaterfallViewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.mRecycleListViewBtn:
                intent.setClass(this,RecyclerListViewActivity.class);
                break;
            case R.id.mRecycleGridViewBtn:
                intent.setClass(this,RecyclerGridViewActivity.class);
                break;
            case R.id.mRecycleWaterfallViewBtn:
                intent.setClass(this,RecyclerWaterfallViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
