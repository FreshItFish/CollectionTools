package com.zxtc.collectiontools.ui.controller.openmapguide;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.utils.OpenLocalMapUtil;
import com.zxtc.collectiontools.utils.ToastUtils;

import java.net.URISyntaxException;

/**
 * 作者：KY
 * 创建时间：2017/11/6 13:55
 * 描述:
 */

public class OpenGuideMapActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public int bindLayout() {
        return R.layout.activity_openguidemap;
    }

    @Override
    public void initView(View mContextView) {
        findViewById(R.id.activity_openguidemap_open_GD).setOnClickListener(this);
        findViewById(R.id.activity_openguidemap_open_BD).setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

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
            case R.id.activity_openguidemap_open_GD:
                try {
                    /*Intent intent = new Intent(OpenLocalMapUtil.getGdMapUri("高德地图","28.1903","113.031738","我的位置","28.187519","113.029713","终点位置"));*/
                    Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname"+"&sname="+"我的位置"+"&dname="+"天安门广场"+"&dev=0&m=0&t=1");
                    if (OpenLocalMapUtil.isGdMapInstalled()){
                        startActivity(intent);
                        ToastUtils.showShort("正在打开高德地图");
                    }else {
                        ToastUtils.showShort("未安装高德地图！");
                    }
                    //起点终点都是名称
                    /*Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname"+"&sname="+"万家丽国际Mall"+"&dname="+"东郡华城广场|A座"+"&dev=0&m=0&t=1");
                    if (OpenLocalMapUtil.isGdMapInstalled()){
                        startActivity(intent);
                    }else {
                        showToast("未安装高德地图！");
                    }*/
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.activity_openguidemap_open_BD:
                try {
                    Intent intent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=王府井&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    if(OpenLocalMapUtil.isBaiduMapInstalled()){
                        startActivity(intent);
                        ToastUtils.showShort("正在打开百度地图");
                    }else {
                        ToastUtils.showShort("没有安装百度地图客户端");
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
