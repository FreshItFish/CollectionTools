package com.zxtc.collectiontools.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyApplication;

import java.lang.ref.WeakReference;

/**
 * base Activity
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    //整个应用Applicaiton
    private MyApplication mApplication = null;
    //当前Activity的弱引用，防止内存泄露
    private WeakReference<Activity> context = null;
    //当前Activity渲染的视图View
    private View mContextView = null;
    private TextView mToolbarTitle,mToolbarSubTitle;
    private Toolbar mToolbar;
    protected ImageView iv_sidemenu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置渲染视图View
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);

        //界面进入显示动画
        overridePendingTransition(R.anim.anim_in,0);

        //初始化toolbar
        initToolBar();

        //获取应用Application
        mApplication = (MyApplication) getApplicationContext();

        //将当前Activity压入栈
        context = new WeakReference<Activity>(this);
        mApplication.pushTask(context);

        //初始化控件
        initView(mContextView);

        //业务操作
        doBusiness(this);
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_sidemenu = (ImageView) findViewById(R.id.toolbar_sidemenu);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);

        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 在onstart里面判断
     */
    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if(null != getToolbar()){
            if (isShowBacking()) {
                showBack();
                iv_sidemenu.setVisibility(View.GONE);
            }else {
                if(isShowMenuImage()) {
                    //显示侧边栏按钮
                    iv_sidemenu.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 获取头部标题的TextView
     */
    public TextView getToolbarTitle(){
        return mToolbarTitle;
    }
    /**
     * 获取头部标题的TextView
     */
    public TextView getSubTitle(){
        return mToolbarSubTitle;
    }

    /**
     * 设置头部标题
     */
    public void setToolBarTitle(CharSequence title) {
        if(mToolbarTitle != null){
            mToolbarTitle.setText(title);
        }else{
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack(){
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.mipmap.icon_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     */
    protected boolean isShowBacking(){
        return true;
    }

    /**
     * 是否显示左侧菜单按钮,默认显示,可在子类重写该方法.
     */
    protected boolean isShowMenuImage(){
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        destroy();
        mApplication.removeTask(context);
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //界面退出显示动画
        overridePendingTransition(0, R.anim.anim_out);
    }

    /**
     * 获取当前Activity
     */
    protected Activity getContext(){
        if(null != context)
            return context.get();
        else
            return null;
    }

}
