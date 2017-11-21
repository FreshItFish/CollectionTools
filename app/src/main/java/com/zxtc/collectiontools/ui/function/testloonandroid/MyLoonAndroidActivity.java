package com.zxtc.collectiontools.ui.function.testloonandroid;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.zxtc.collectiontools.R;

/**
 * 作者：Administrator on 2017/4/24 12:06
 * 描述：
 */
@InLayer(R.layout.activity_launcher)
public class MyLoonAndroidActivity extends AppCompatActivity {

    @InView(R.id.launcher_tv)
    TextView tv;

    @Init
    @InBack
    protected void init() throws InterruptedException {
        timer.start();  //开始倒计时
        Thread.sleep(3000);
        /*startActivity(new Intent(MyLoonAndroidActivity.this, LoginActivity.class));*/
        finish();
    }

    protected CountDownTimer timer = new CountDownTimer(5000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tv.setText(millisUntilFinished / 1000 +"");
        }

        @Override
        public void onFinish() {
            /*tv.setEnabled(true);
            tv.setText("获取验证码");*/
        }
    };
}
