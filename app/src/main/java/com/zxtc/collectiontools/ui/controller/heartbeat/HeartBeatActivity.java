package com.zxtc.collectiontools.ui.controller.heartbeat;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;

import java.util.ArrayList;

/**
 * 作者：KY on 2017/4/27 11:19
 * 描述：
 */

public class HeartBeatActivity extends BaseActivity {

    private TextView heartbeat_tv;
    private ArrayList<View> mViewList;
    private HeatbeatThread heartbeatThread;

    @Override
    public int bindLayout() {
        return R.layout.activity_heartbeat;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("模拟心跳动画");
        getSubTitle().setText(null);

        heartbeat_tv = (TextView) this.findViewById(R.id.heartbeat_tv);
        FrameLayout single_fl_01 = (FrameLayout) this.findViewById(R.id.heartbeat_fl_01);
        FrameLayout single_fl_02 = (FrameLayout) this.findViewById(R.id.heartbeat_fl_02);
        FrameLayout single_fl_03 = (FrameLayout) this.findViewById(R.id.heartbeat_fl_03);

        mViewList = new ArrayList<>();
//        mViewList.add(heartbeat_tv);
        mViewList.add(single_fl_01);
        mViewList.add(single_fl_02);
        mViewList.add(single_fl_03);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {
        startHeartBeat();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopHeartBeat();
    }

    @Override
    public void destroy() {

    }


    /**
     * 开始心跳
     */
    private void startHeartBeat() {
        if (heartbeatThread == null) {
            heartbeatThread = new HeatbeatThread();
        }
        if (!heartbeatThread.isAlive()) {
            heartbeatThread.start();
        }
    }

    /**
     * 停止心跳
     */
    private void stopHeartBeat() {
        if (heartbeatThread != null && heartbeatThread.isInterrupted()) {
            heartbeatThread.interrupt();
            heartbeatThread = null;
            System.gc();
        }
    }

    private class HeatbeatThread extends Thread {
        public void run() {
            try {
                sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            while (true) {
                runOnUiThread(new Runnable() {
                    public void run() {
//                        playHeartbeatAnimation(heartbeat_tv);
                        for (View view : mViewList) {
                            playHeartbeatAnimation(view);
                        }
                    }
                });
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    // 按钮模拟心脏跳动
    private void playHeartbeatAnimation(final View heartbeatView) {
        AnimationSet swellAnimationSet = new AnimationSet(true);
        swellAnimationSet.addAnimation(new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f));
        swellAnimationSet.addAnimation(new AlphaAnimation(1.0f, 0.3f));

        swellAnimationSet.setDuration(500);
        swellAnimationSet.setInterpolator(new AccelerateInterpolator());
        swellAnimationSet.setFillAfter(true);

        swellAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet shrinkAnimationSet = new AnimationSet(true);
                shrinkAnimationSet.addAnimation(new ScaleAnimation(1.8f, 1.0f, 1.8f, 1.0f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                shrinkAnimationSet.addAnimation(new AlphaAnimation(0.3f, 1.0f));
                shrinkAnimationSet.setDuration(1000);
                shrinkAnimationSet.setInterpolator(new DecelerateInterpolator());
                shrinkAnimationSet.setFillAfter(false);
                heartbeatView.startAnimation(shrinkAnimationSet);// 动画结束时重新开始，实现心跳的View
            }
        });

        heartbeatView.startAnimation(swellAnimationSet);
    }
}
