package com.zxtc.collectiontools.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyApplication;
import com.zxtc.collectiontools.application.MyConstant;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.ui.ahead.AheadDemoActivity;
import com.zxtc.collectiontools.ui.controller.ControllerDemoActivity;
import com.zxtc.collectiontools.ui.function.FunctionDemoActivity;
import com.zxtc.collectiontools.ui.list.ListDemoActivity;
import com.zxtc.collectiontools.utils.ToastUtils;

import java.util.List;

/**
 * 作者：KY on 2017/4/26 13:14
 * 描述：
 */

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    long waitTime = 2000;   //双击退出间隔时间
    long touchTime = 0;        //点击时间

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawer;
    private ImageView iv_head;
    private ListView listView;

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("主界面");
        getSubTitle().setText(null);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
        iv_sidemenu.setOnClickListener(this);
        iv_head = (ImageView) findViewById(R.id.left_head_iv);
        iv_head.setOnClickListener(this);

        mDrawerLayout.closeDrawer(mDrawer);

        listView = (ListView) findViewById(R.id.main_list);
    }

    @Override
    public void doBusiness(Context mContext) {
        List<String> date = MyConstant.getDate(getResources().getStringArray(R.array.main_ui_type));
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,date));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position){
            case 0:
                intent = new Intent(MainActivity.this,AheadDemoActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this,ListDemoActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this,ControllerDemoActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(MainActivity.this,FunctionDemoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 返回键
        if (KeyEvent.KEYCODE_BACK == keyCode) {

            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                ToastUtils.showShort("再按一次，退出程序");
                touchTime = currentTime;
            } else {
                ((MyApplication) getApplicationContext()).removeAll();
            }

            return true;
        }
        return false;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_sidemenu:
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.left_head_iv:
                Toast.makeText(MainActivity.this, "点你个头啊", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}