package com.zxtc.collectiontools.ui.list.recyclerview;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView上滑加载更多
 */

public class MyRecyclerViewActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private BaseAdapter mAdapter;
    private int loadCount;

    @Override
    public int bindLayout() {
        return R.layout.activity_myrecycler;
    }

    @Override
    public void initView(View mContextView) {
        getToolbarTitle().setText("上滑加载更多测试");
        getSubTitle().setText(null);

        mRecyclerView = (RecyclerView) findViewById(R.id.myreecycler_rlist);

    }

    @Override
    public void doBusiness(Context mContext) {
        //创建被装饰者类实例
        final MyAdapter adapter = new MyAdapter(this);
        //创建装饰者实例，并传入被装饰者和回调接口
        mAdapter = new LoadMoreAdapterWrapper(adapter, new LoadMoreAdapterWrapper.OnLoad() {
            @Override
            public void load(final int pagePosition, final int pageSize, final LoadMoreAdapterWrapper.ILoadCallback callback) {
                /*String url = MyConstant.net_address + "";
                OkGo.get(url)
                        .tag(this)
                        .params("pagesize",pagePosition)
                        .execute(new JsonCallback<ResponseResult<List<PostImageBean>>>() {
                            @Override
                            public void onSuccess(ResponseResult<List<PostImageBean>> listResponseResult, Call call, Response response) {
                                List<PostImageBean> data = listResponseResult.data;
                                //数据的处理最终还是交给被装饰的adapter来处理
                                adapter.appendData(data);
                                callback.onSuccess();
                                if (dataSet.size() == 0 || dataSet.isEmpty() || pagePosition > pageSize) {
                                    //数据为空立即显示
                                    callback.onFailure();
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });*/

                //此处模拟做网络操作，2s延迟，将拉取的数据更新到adpter中
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> dataSet = new ArrayList();
                        for (int i = 0; i < 14; i++) {
                            dataSet.add("我是第"+i+"条数据");
                        }
                        //数据的处理最终还是交给被装饰的adapter来处理
                        adapter.appendData(dataSet);
                        callback.onSuccess();

                        if (dataSet.size() == 0 || dataSet.isEmpty() || pagePosition > pageSize) {
                            //数据为空立即显示
                            callback.onFailure();
                        }else {
                            //模拟加载到没有更多数据的情况，触发onFailure
                            if (loadCount++ == 3) {
                                callback.onFailure();
                            }
                        }
                    }
                }, 1500);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
