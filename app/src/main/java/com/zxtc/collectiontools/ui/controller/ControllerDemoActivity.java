package com.zxtc.collectiontools.ui.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyConstant;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.ui.controller.heartbeat.HeartBeatActivity;
import com.zxtc.collectiontools.ui.controller.openmapguide.OpenGuideMapActivity;
import com.zxtc.collectiontools.ui.controller.progressbar.ProgressBarActivity;
import com.zxtc.collectiontools.ui.controller.ruler.RulerDemoActivity;
import com.zxtc.collectiontools.ui.controller.sportcircleconnect.SportCircleActivity;

import java.util.List;

/**
 * 作者：KY on 2017/4/26 14:26
 * 描述：控件集合
 */

public class ControllerDemoActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView listView;

    @Override
    public int bindLayout() {
        return R.layout.view_listview;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("控件集合");
        getSubTitle().setText(null);

        listView = (ListView) findViewById(R.id.mylist);
    }

    @Override
    public void doBusiness(Context mContext) {
        List<String> date = MyConstant.getDate(getResources().getStringArray(R.array.controller_ui_type));
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,date));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position){
            case 0:
                intent = new Intent(ControllerDemoActivity.this,ProgressBarActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(ControllerDemoActivity.this,HeartBeatActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(ControllerDemoActivity.this,RulerDemoActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(ControllerDemoActivity.this,SportCircleActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(ControllerDemoActivity.this,OpenGuideMapActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
