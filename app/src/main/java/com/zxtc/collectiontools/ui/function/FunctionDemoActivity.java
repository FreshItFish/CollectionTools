package com.zxtc.collectiontools.ui.function;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyConstant;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.ui.function.camerademo.CameraShowActivity;
import com.zxtc.collectiontools.ui.function.testloonandroid.MyLoonAndroidActivity;
import com.zxtc.collectiontools.ui.function.upload.UploadActivity;
import com.zxtc.collectiontools.utils.ConstantUtils;
import com.zxtc.collectiontools.utils.EmailTool;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

/**
 * 作者：KY on 2017/4/26 14:26
 * 描述： 功能类集合
 */

public class FunctionDemoActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView listView;

    @Override
    public int bindLayout() {
        return R.layout.view_listview;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("功能类集合");
        getSubTitle().setText(null);

        listView = (ListView) findViewById(R.id.mylist);
    }

    @Override
    public void doBusiness(Context mContext) {
        List<String> date = ConstantUtils.getDate(getResources().getStringArray(R.array.function_ui_type));
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
                /*mThread.start();*/
                Toast.makeText(this, "心情不好，别点我！", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                intent = new Intent(FunctionDemoActivity.this, MyLoonAndroidActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(FunctionDemoActivity.this, UploadActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(FunctionDemoActivity.this, CameraShowActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    //附件地址
    String path = Environment.getExternalStorageDirectory()+ "/zxsc/picCache" + File.separator + "report.csv";
    //发送邮件
    Thread  mThread = new Thread(new Runnable() {
        @Override
        public void run() {

            String from = "1251314347@qq.com";
            String pwd = "utqiqkxhespxihbb";
            String to = "812207679@qq.com";

            String subject="fody文件";
            String content="csv附件";

            String filePath[]=new String[]{path};
            try {
                EmailTool email = EmailTool.getInstance(EmailTool.ContentType.ATTACH).host("smtp.qq.com").auth("true").sockPort(465)
                        .sockClass("javax.net.ssl.SSLSocketFactory").protocol("smtp").login(from, pwd, null);
                email.send(new String[]{to}, null, null, subject, content, filePath);

                Toast.makeText(FunctionDemoActivity.this, "邮件发送成功！", Toast.LENGTH_SHORT).show();

            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    });
}
