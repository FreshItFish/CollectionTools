package com.zxtc.collectiontools.ui.controller.progressbar.progressweight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;

/**
 * 作者：KY
 * 创建时间：2017/10/30 16:39
 * 描述:
 */

public class FlikerProgressActivity extends BaseActivity implements View.OnClickListener,Runnable {

    private FlikerProgressBar flikerProgressBar,roundProgressbar;	//自定义view的progress

    Thread downLoadThread;	//模拟下载

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flikerProgressBar.setProgress(msg.arg1);
            roundProgressbar.setProgress(msg.arg1);
            if(msg.arg1 == 100){
                flikerProgressBar.finishLoad();
                roundProgressbar.finishLoad();
            }
        }
    };

    @Override
    public int bindLayout() {
        return R.layout.activity_flikerprogress;
    }

    @Override
    public void initView(View mContextView) {
        flikerProgressBar = (FlikerProgressBar) findViewById(R.id.flikerbar);
        roundProgressbar = (FlikerProgressBar) findViewById(R.id.round_flikerbar);
        flikerProgressBar.setOnClickListener(this);
        roundProgressbar.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        downLoad();
    }

    public void reLoad(View view) {
        downLoadThread.interrupt();
        // 重新加载
        flikerProgressBar.reset();
        roundProgressbar.reset();

        downLoad();
    }

    private void downLoad() {
        downLoadThread = new Thread(this);
        downLoadThread.start();
    }

    @Override
    public void onClick(View v) {
        if(!flikerProgressBar.isFinish()){
            flikerProgressBar.toggle();
            roundProgressbar.toggle();

            if(flikerProgressBar.isStop()){
                downLoadThread.interrupt();
            } else {
                downLoad();
            }
        }
    }

    @Override
    public void run() {
        try {
            while( ! downLoadThread.isInterrupted()){
                float progress = flikerProgressBar.getProgress();
                progress  += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if(progress == 100){
                    break;
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resume() {

    }
    @Override
    public void destroy() {

    }
}
