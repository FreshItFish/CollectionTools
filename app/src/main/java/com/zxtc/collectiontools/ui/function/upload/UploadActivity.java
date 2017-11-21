package com.zxtc.collectiontools.ui.function.upload;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.listener.UploadListener;
import com.lzy.okserver.upload.UploadInfo;
import com.lzy.okserver.upload.UploadManager;
import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyConstant;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.entity.PostImageBean;
import com.zxtc.collectiontools.entity.ResponseResult;
import com.zxtc.collectiontools.utils.DialogUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/13.
 */

public class UploadActivity extends BaseActivity {
    //拍照回传标志
    public final static int CAMERA_RESULT = 100;
    private transient String mCurrentPhotoPath;
    private EditText et_name,et_password;
    private Button login_btn;
    private ImageView iv_head;

    /*private MaterialDialog materialDialog;*/
    private UploadManager uploadManager;
    private DialogUtils dialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View mContextView) {
        getToolbarTitle().setText("图片编辑上传测试");
        getSubTitle().setText("");

        uploadManager = UploadManager.getInstance();

        iv_head = (ImageView) findViewById(R.id.login_iv_head);
        et_name = (EditText) findViewById(R.id.login_et_name);
        et_password = (EditText) findViewById(R.id.login_et_password);
        login_btn = (Button) findViewById(R.id.login_btn);
    }

    @Override
    public void doBusiness(Context mContext) {
        iv_head.setImageResource(R.mipmap.addimage);
        iv_head.setClickable(true);
        et_name.setHint("请输入图片描述");
        et_password.setHint("请输入上传者姓名");

        login_btn.setText("上传");

        //初始化dialog
//        ProgressBar pb = new ProgressBar(UploadActivity.this, null, android.R.attr.progressBarStyleHorizontal);    //横向进度条
//        ProgressBar pb = new ProgressBar(UploadActivity.this, null, android.R.attr.progressBarStyle);    //圆环进度条
//
//        materialDialog = new MaterialDialog(UploadActivity.this)
//                .setContentView(pb)
//                .setMessage("上传中，请稍等。。。");
        /*.setTitle("上传中，请稍等。。。")*/

        dialog = DialogUtils.buildLoadingProgress(this);

        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromCamera();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    validateEdit(et_name,"数据不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    validateEdit(et_password,"数据不能为空！");
                    return;
                }

                if (mCurrentPhotoPath == null || mCurrentPhotoPath.isEmpty()) {
                    Toast.makeText(UploadActivity.this, "图片路径不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //显示dialog
                dialog.setMessage("请求网络中...").show();
                /*materialDialog.show();*/
//                String url = "http://192.168.51.55:8080/updateInfo?name="+password+"&description="+name;
                String url = MyConstant.net_address + "updateInfo";

                PostRequest request = OkGo.post(url)
                        .tag(this)
                        .isMultipart(true)      // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                        .connTimeOut(5000)      // 设置当前请求的连接超时时间
                        .readTimeOut(3000)      // 设置当前请求的读取超时时间
                        .writeTimeOut(3000)     // 设置当前请求的写入超时时间
                        .params("description", name) //描述
                        .params("name", password)    //上传者
                        .params("image", new File(mCurrentPhotoPath));  //可以添加文件上传
                uploadManager.addTask("upp", request, new UploadListener<ResponseResult<List<PostImageBean>>>() {
                    @Override
                    public void onProgress(UploadInfo uploadInfo) {
                        if (uploadInfo.getState() == UploadManager.ERROR) {
                            /*materialDialog.setTitle("上传出错");
                            materialDialog.dismiss();*/
                            dialog.setMessage("上传出错").dismiss();
                        } else if (uploadInfo.getState() == UploadManager.WAITING) {
                            /*materialDialog.setTitle("等待上传");*/
                            dialog.setMessage("等待上传");
                        } else if (uploadInfo.getState() == UploadManager.FINISH) {
                            /*materialDialog.setTitle("上传成功");*/
                            dialog.setMessage("上传成功");
                            Toast.makeText(UploadActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                        } else if (uploadInfo.getState() == UploadManager.UPLOADING) {
                            /*materialDialog.setTitle("上传中 "+ (Math.round(uploadInfo.getProgress() * 10000) * 1.0f / 100) + "%");*/
                            dialog.setMessage("上传中 "+ (Math.round(uploadInfo.getProgress() * 10000) * 1.0f / 100) + "%");
                        }
                    }

                    @Override
                    public void onFinish(ResponseResult<List<PostImageBean>> listResponseResult) {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(UploadInfo uploadInfo, String errorMsg, Exception e) {
                        /*materialDialog.dismiss();*/
                        dialog.dismiss();
                    }

                    @Override
                    public ResponseResult<List<PostImageBean>> parseNetworkResponse(Response response) throws Exception {
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        Type type = params[0];
                        JsonReader jsonReader = new JsonReader(response.body().charStream());
                        ResponseResult<List<PostImageBean>> beanList = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss a").create().fromJson(jsonReader, type);
                        response.close();
                        return beanList;
                    }
                });
            }
        });

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);

        //取消所有请求
        OkGo.getInstance().cancelAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, null);
            if (bitmap == null) {
                iv_head.setImageResource(R.mipmap.addimage);
            }else {
                iv_head.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * Edittext 编辑框错误提示
     * @param view
     * @param string
     */
    private void validateEdit(EditText view, String string) {
        //获取焦点
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        //设置edittext右侧提示图片
        Drawable drawable = getResources().getDrawable(R.drawable.bg_red_radio);
        drawable.setBounds(0, 0, 20, 20);   //图片大小
        view.setError(string,drawable);     //错误提示
    }

    /**
     * 拍照获取图片
     */
    private void getImageFromCamera() {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".png";
    }
}
