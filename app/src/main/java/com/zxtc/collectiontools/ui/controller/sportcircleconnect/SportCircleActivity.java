package com.zxtc.collectiontools.ui.controller.sportcircleconnect;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.ui.controller.sportcircleconnect.sportcircleweight.MISportsConnectView;
import com.zxtc.collectiontools.ui.controller.sportcircleconnect.sportcircleweight.SportsData;

/**
 * 作者：KY
 * 创建时间：2017/10/30 11:58
 * 描述: 仿小米运动环形
 */

public class SportCircleActivity extends BaseActivity {

    private Handler handler;
    boolean connect = false;
    private MISportsConnectView miSportsConnectView;

    @Override
    public int bindLayout() {
        return R.layout.activity_sportcircle;
    }

    @Override
    public void initView(View mContextView) {
        miSportsConnectView = (MISportsConnectView) findViewById(R.id.mi_sports_loading_view);
    }

    @Override
    public void doBusiness(Context mContext) {
        SportsData sportsData = new SportsData();
        sportsData.step = 89757;
        sportsData.distance = 1700;
        sportsData.calories = 34;
        sportsData.progress = 75;
        miSportsConnectView.setSportsData(sportsData);

        handler = new Handler();
        final Button connectButton = (Button) findViewById(R.id.connect_button);
        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        connect = !connect;
                        miSportsConnectView.setConnected(connect);
                        connectButton.setText(connect? getString(R.string.disconnect) : getString(R.string.connect));
                    }
                }, 500);
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
