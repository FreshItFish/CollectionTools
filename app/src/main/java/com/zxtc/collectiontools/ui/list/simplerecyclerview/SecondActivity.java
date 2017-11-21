package com.zxtc.collectiontools.ui.list.simplerecyclerview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 *  简单的recyclerView实现
 */

public class SecondActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public int bindLayout() {
        return R.layout.activity_second;
    }

    @Override
    public void initView(View mContextView) {
        getToolbarTitle().setText(R.string.title_activity_second);
        getSubTitle().setText(R.string.title_activity_setting);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.second_swip);
        mRecyclerView = (RecyclerView) findViewById(R.id.second_rlist);
    }

    @Override
    public void doBusiness(Context mContext) {
        //SwipeRefreshLayout 旋转圈颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
//        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟下拉刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);   //停止动画
                        Toast.makeText(SecondActivity.this, "没有更多数据了哦！", Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置item增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //普通的recyclerView
//        SecondAdapter mSecondAdapter = new SecondAdapter(this, getDate());
//        mRecyclerView.setAdapter(mSecondAdapter);

        //带显示动画的recyclerView
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(new SecondAdapter(this, getDate()));
        slideAdapter.setFirstOnly(false);   //设置仅在第一次显示条目执行动画 否
        slideAdapter.setDuration(500);  //动画加载间隔
        mRecyclerView.setAdapter(slideAdapter);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private List getDate(){
        List list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("我是第 "+i+" 号");
        }
        return list;
    }
}
