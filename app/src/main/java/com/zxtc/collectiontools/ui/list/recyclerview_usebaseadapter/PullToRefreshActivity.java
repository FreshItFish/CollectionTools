package com.zxtc.collectiontools.ui.list.recyclerview_usebaseadapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.github.library.listener.RequestLoadMoreListener;
import com.github.library.view.LoadType;
import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用第三方的 recyclerview 适配器
 */
public class PullToRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RequestLoadMoreListener, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private BaseRecyclerAdapter<String> mAdapter;

    private MyHandler mHandler = new MyHandler(this);

    //delayMillis
    private static final int DELAY_MILLIS = 1500;

    private static final int TOTAL_COUNT = 50;

    @Override
    public int bindLayout() {
        return R.layout.activity_pull_refresh;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("第三方adapter");
        getSubTitle().setText("CUSTOM");

        getSubTitle().setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(this);
        //recyclerView 布局设置  垂直向下
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void doBusiness(Context mContext) {
        if (!getItemDatas().isEmpty() || getItemDatas().size() > 0){
            mRecyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<String>(this, getItemDatas(), R.layout.item_cardview) {
                @Override
                protected void convert(BaseViewHolder helper, String item) {
                    helper.setText(R.id.cardview_tv, item);

                    //item点击监听（方式一）
                    /*helper.setOnClickListener(R.id.cardview_tv, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view, "your click item", Snackbar.LENGTH_SHORT).show();
                        }
                    });*/
                }
            });
            //加载更多
            openLoadMore();
        } else {
            mRecyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<String>(this, new ArrayList<String>(), R.layout.item_cardview) {
                @Override
                protected void convert(BaseViewHolder helper, String item) {
                }
            });
            //加载空布局
            addEmptyView();
        }

        //item点击监听（方式二）
        mAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(view, "your click "+ position, Snackbar.LENGTH_SHORT).show();
            }
        });

//        addHeaderView();
    }

    private void openLoadMore() {
        mAdapter.openLoadingMore(true);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getItemDatas().size() > 0) {
                    mAdapter.setData(getItemDatas());
                }
                mRefreshLayout.setRefreshing(false);
            }
        }, DELAY_MILLIS);
    }

    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() >= TOTAL_COUNT) {
                    mAdapter.notifyDataChangeAfterLoadMore(false);
                    mAdapter.addNoMoreView();
                } else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataChangeAfterLoadMore(addDatas(), true);
                        }
                    }, DELAY_MILLIS);
                }
            }
        });

    }

    /*private void addHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.item_no_more, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "your click headerView", Snackbar.LENGTH_SHORT).show();
            }
        });
    }*/

    /**
     * 错误提示界面
     */
    private void addEmptyView() {
        View emptyView=getLayoutInflater().inflate(R.layout.view_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.addEmptyView(emptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "your click empty", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    public static List<String> getItemDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mList.add("欢迎光临我的世界");
        }
        return mList;
    }

    public static List<String> addDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mList.add("我是新增条目" + (i + 1));
        }
        return mList;
    }

    @Override
    public void onClick(View v) {

        final String[] stringItems = {"CUSTOM", "CUBES", "SWAP", "EAT_BEANS"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false)
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                    case 0:
                        mAdapter.setLoadMoreType(LoadType.CUSTOM);
                        break;
                    case 1:
                        mAdapter.setLoadMoreType(LoadType.CUBES);
                        break;
                    case 2:
                        mAdapter.setLoadMoreType(LoadType.SWAP);
                        break;
                    case 3:
                        mAdapter.setLoadMoreType(LoadType.EAT_BEANS);
                        break;
                }
                getSubTitle().setText(stringItems[position]);
                mRecyclerView.setAdapter(mAdapter);
                dialog.dismiss();
            }
        });

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private static class MyHandler extends Handler {
        private WeakReference<PullToRefreshActivity> activityWeakReference;

        public MyHandler(PullToRefreshActivity activity) {
            activityWeakReference = new WeakReference<PullToRefreshActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PullToRefreshActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }
        }
    }

}
