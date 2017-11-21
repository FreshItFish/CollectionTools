package com.zxtc.collectiontools.widget.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 仿雅虎旋转动画
 */
public class Yahooloadingview extends View {

    private Paint paint;

    private RectF rectF;

    private int width = 360;

    private int progress = 0;

    private int status = 1;


    private int startOne = 0;
    private int startTwo = 180;

    private int endOne = progress;
    private int endTwo = progress;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (progress >= 180) {
                status = -1;
            }
            if (progress <= 0) {
                status = 3;
            }
            progress += status;

            endOne = progress;
            endTwo = progress;


            if (status == -1) {
                startOne = 180 - progress;
            }
            if (startOne >= 180) {
                startOne = 0;
            }

            if (status == -1) {
                startTwo = 360 - progress;
            }
            if (startTwo >= 360) {
                startTwo = 180;
            }

            invalidate();

            return true;
        }
    });


    public Yahooloadingview(Context context) {
        this(context, null);
    }

    public Yahooloadingview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Yahooloadingview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initRectF();
        initThread();
        initAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintLoadview(canvas);

    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(30);   //圆环的宽度
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
    }


    private void initRectF() {
        rectF = new RectF(30, 30, width - 30, width - 30);
    }

    private void paintLoadview(Canvas canvas) {

        canvas.drawArc(rectF, startOne, endOne, false, paint);

        canvas.drawArc(rectF, startTwo, endTwo, false, paint);

    }


    private void initThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessageDelayed(0, 1000);
                    SystemClock.sleep(8);
                }
            }
        }).start();
    }

    private void initAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(12000);   //设置动画播放时长
        rotateAnimation.setRepeatCount(Animation.INFINITE); //设置动画循环播放
        rotateAnimation.setInterpolator(new LinearInterpolator());  //设置动画以均匀的速率在改变
        this.startAnimation(rotateAnimation);

    }
}
