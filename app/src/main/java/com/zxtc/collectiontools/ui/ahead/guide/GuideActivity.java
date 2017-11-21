package com.zxtc.collectiontools.ui.ahead.guide;

import android.content.Context;
import android.view.View;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.widget.ImageIndicatorView;


/**
 * 引导界面
 */
public class GuideActivity extends BaseActivity {

	private ImageIndicatorView imageIndicatorView;
	@Override
	public int bindLayout() {
		return R.layout.activity_guide;
	}

	@Override
	public void initView(View view) {
		imageIndicatorView = (ImageIndicatorView) findViewById(R.id.guide_indicate_view);
		//滑动监听器
		imageIndicatorView.setOnItemChangeListener(new ImageIndicatorView.OnItemChangeListener() {
			@Override
			public void onPosition(int position, int totalCount) {
				if(position==totalCount){
					finish();
				}
			}
		});
	}

	@Override
	public void doBusiness(Context mContext) {

		final Integer[] resArray = new Integer[] { R.mipmap.guide01, R.mipmap.guide02, R.mipmap.guide03 };
		imageIndicatorView.setupLayoutByDrawable(resArray);
		imageIndicatorView.setIndicateStyle(ImageIndicatorView.INDICATE_USERGUIDE_STYLE);
		imageIndicatorView.show();
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}

}
