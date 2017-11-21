package com.zxtc.collectiontools.ui.ahead.guide;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zxtc.collectiontools.R;

/**
 * 欢迎界面
 */

public class CountDownTimeLauncher extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_launcher);
        tv = (TextView) findViewById(R.id.launcher_tv);
        mThread.start();
    }

    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                timer.start();  //开始倒计时
                Thread.sleep(3000); //线程睡眠3秒
                /*startActivity(new Intent(CountDownTimeLauncher.this, LoginActivity.class));*/
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private CountDownTimer timer = new CountDownTimer(5000, 1000) {

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
