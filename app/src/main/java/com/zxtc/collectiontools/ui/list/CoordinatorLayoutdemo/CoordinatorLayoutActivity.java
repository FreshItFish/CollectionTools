package com.zxtc.collectiontools.ui.list.CoordinatorLayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyConstant;
import com.zxtc.collectiontools.callback.JsonCallback;
import com.zxtc.collectiontools.entity.PostImageBean;
import com.zxtc.collectiontools.entity.ResponseResult;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import okhttp3.Call;
import okhttp3.Response;

/**
 * CoordinatorLayout 简单测试
 */

public class CoordinatorLayoutActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout);

        findView();
        initView();
    }

    private void findView() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.coordinator_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initView() {
        collapsingToolbarLayout.setTitle("CoordinatorLayout 简单测试");

        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        showAdapter();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //替代Toast，底部弹出展示界面
                Snackbar.make(view,"FloatingActionButton", Snackbar.LENGTH_LONG)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        //取消所有请求
        OkGo.getInstance().cancelAll();
    }

    public void showAdapter(){
        String url = MyConstant.net_address + "getUserInofPojos";

        OkGo.get(url)
                .tag(this)
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new JsonCallback<ResponseResult<List<PostImageBean>>>() {
                    @Override
                    public void onSuccess(ResponseResult<List<PostImageBean>> listResponseResult, Call call, Response response) {
                        List<PostImageBean> listBean = listResponseResult.data;
                        //使用带动画的 recyclerview
                        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(new MyCardViewAdapter(getBaseContext(),listBean));
                        slideAdapter.setFirstOnly(false);   //设置仅在第一次显示条目执行动画 否
                        slideAdapter.setDuration(500);  //动画加载间隔
                        recyclerView.setAdapter(slideAdapter);
                    }
                });

        /*OkGo.get(url)
                .tag(this)
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new JsonLinkCallback<ImageBean>() {
                    @Override
                    public void onSuccess(ImageBean imageBean, Call call, Response response) {
                        //使用带动画的 recyclerview
                        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(new MyCardViewAdapter(getBaseContext(),imageBean));
                        slideAdapter.setFirstOnly(false);   //设置仅在第一次显示条目执行动画 否
                        slideAdapter.setDuration(500);  //动画加载间隔
                        recyclerView.setAdapter(slideAdapter);
                    }
                });*/


//        List<String> mList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            mList.add("CardView "+i);
//        }
//        return mList;
    }

}
