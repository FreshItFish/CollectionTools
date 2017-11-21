package com.zxtc.collectiontools.ui.ahead;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyConstant;
import com.zxtc.collectiontools.ui.ahead.guide.CountDownTimeLauncher;
import com.zxtc.collectiontools.ui.ahead.guide.GuideActivity;
import com.zxtc.collectiontools.ui.ahead.login.LoginActivity;
import com.zxtc.collectiontools.base.BaseActivity;

import java.util.List;

public class AheadDemoActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    public int bindLayout() {
        return R.layout.view_listview;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("引导集合");
        getSubTitle().setText(null);

        listView = (ListView) findViewById(R.id.mylist);
    }

    @Override
    public void doBusiness(Context mContext) {
        List<String> date = MyConstant.getDate(getResources().getStringArray(R.array.ahead_ui_type));
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
                intent = new Intent(AheadDemoActivity.this,GuideActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(AheadDemoActivity.this, CountDownTimeLauncher.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(AheadDemoActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /** 给定各菜单项对应ID值，以区别之，能保证这些值不同就行了 */
    /*public static final int ADD_ID = Menu.FIRST;// 添加命令对应ID值
    public static final int DELETE_ID = Menu.FIRST + 1;// 删除命令对应ID值
    public static final int EXIT_ID = Menu.FIRST + 2;// 退出命令对应ID值

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean b = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, ADD_ID, Menu.NONE, R.string.menu_add);
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.menu_delete);
        menu.add(Menu.NONE, EXIT_ID, Menu.NONE, R.string.menu_exit);
        return b;// 如果返回值不为true,则Menu菜单将不会显示
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ADD_ID:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                break;
            case DELETE_ID:
                Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
                break;
            case EXIT_ID:
                finish();// 退出程序
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
