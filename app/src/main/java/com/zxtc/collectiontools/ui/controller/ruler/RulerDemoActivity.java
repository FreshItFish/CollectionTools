package com.zxtc.collectiontools.ui.controller.ruler;

import android.content.Context;
import android.view.View;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.ui.controller.ruler.rulerweight.BooheeRuler;
import com.zxtc.collectiontools.ui.controller.ruler.rulerweight.KgNumberLayout;

/**
 * 作者：KY
 * 创建时间：2017/10/30 11:01
 * 描述: 刻度尺
 */

public class RulerDemoActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_rulerdemo;
    }

    @Override
    public void initView(View mContextView) {
        BooheeRuler mBooheeRuler = (BooheeRuler) findViewById(R.id.br);
        KgNumberLayout mKgNumberLayout = (KgNumberLayout) findViewById(R.id.knl);
        mKgNumberLayout.bindRuler(mBooheeRuler);
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
}
