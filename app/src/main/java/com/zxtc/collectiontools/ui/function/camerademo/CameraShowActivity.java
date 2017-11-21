package com.zxtc.collectiontools.ui.function.camerademo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.widget.ZoomImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拍照显示并上传
 */

public class CameraShowActivity extends BaseActivity implements View.OnClickListener {
    public final static int CAMERA_RESULT = 1;
    private ImageView mImageView;//用于显示照片
    private ZoomImageView mZoomImageView;
    private transient String mCurrentPhotoPath;
    private Bitmap timeBitmap;
    private TextView tv;

    @Override
    public int bindLayout() {
        return R.layout.activity_camerashow;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("拍照加水印显示");
        getSubTitle().setText(null);

        Button mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        Button mButton02 = (Button) findViewById(R.id.button_02);
        mButton02.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.camerashow_tv);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mZoomImageView = (ZoomImageView) findViewById(R.id.zoomimageView);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 拍照并显示
             */
            case R.id.button:
                showMyPhoto();
                if (null != timeBitmap) {
                    tv.setText("图片位置：" + mCurrentPhotoPath);
                }
                break;
            /**
             * 上传
             */
            case R.id.button_02:
                if (null == timeBitmap){
                    Toast.makeText(this, "请处理带时间的bitmap", Toast.LENGTH_SHORT).show();
                    return;
                }
                mZoomImageView.setImageBitmap(timeBitmap);
                break;
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        recycleBitmap(timeBitmap);
    }

    /**
     * 拍照并显示出来
     */
    private void showMyPhoto() {
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
            File mPhotoCacheDir = getSDPath();
            File mPicture = new File(mPhotoCacheDir, getPhotoFileName());
            try {
                if (mPicture.exists()) {
                    mPicture.delete();
                }
                mPicture.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mCurrentPhotoPath = mPicture.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                    Uri.fromFile(mPicture));
            startActivityForResult(intent, CAMERA_RESULT);//跳转界面传回拍照所得数据
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  加水印 也可以加文字
     */
    public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark, String title) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        //需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newb= Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        Paint paint=new Paint();
        //加入图片
        if (watermark != null) {
            int ww = watermark.getWidth();
            int wh = watermark.getHeight();
            paint.setAlpha(50);
            cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印
        }
        //加入文字
        if(title!=null) {
            String familyName ="宋体";
            Typeface font = Typeface.create(familyName, Typeface.BOLD);
            TextPaint textPaint=new TextPaint();
            textPaint.setColor(Color.RED);
            textPaint.setTypeface(font);
            textPaint.setTextSize(200);
            //这里是自动换行的
            StaticLayout layout = new StaticLayout(title,textPaint,w, Layout.Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
            layout.draw(cv);
            //文字就加左上角
            cv.drawText(title,10,40,paint);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        return newb;
    }

    /**
     * bitmap内存释放
     */
    public static Bitmap recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.getConfig() != null) {
            return bitmap;
        }
        Bitmap newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        bitmap.recycle();
        return newBitmap;
    }

    /**
     * 获取图片所在文件夹路径
     */
    public File getSDPath() {
        File mPhotoDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            File sdDir = Environment.getExternalStorageDirectory();//获取根目录

            String mPhotoPath = sdDir.toString() + "/zxsc/picCache";//设置图片文件路径，getSDPath()/zxsc/picCache
            mPhotoDir = new File(mPhotoPath);
            if (!mPhotoDir.exists() && !mPhotoDir.isDirectory()) {
                mPhotoDir.mkdirs();
            }
        }
        return mPhotoDir;
    }

    /**
     * 获取图片名称
     */
    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".png";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, null);

            String phoneName = getPhotoFileName();
            String time = phoneName.substring(0, phoneName.indexOf(".")).split("_")[1];
            timeBitmap = watermarkBitmap(bitmap, null, time);

            mImageView.setImageBitmap(timeBitmap);
        }
    }
}
