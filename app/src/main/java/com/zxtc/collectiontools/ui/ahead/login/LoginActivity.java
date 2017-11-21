package com.zxtc.collectiontools.ui.ahead.login;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.utils.Regex;

/**
 * 登录界面
 */

public class LoginActivity extends BaseActivity {

    private EditText et_name,et_password;
    private Button login_btn;
    private ImageView iv_head;

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected boolean isShowMenuImage() {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View mContextView) {
        //toolbar显示内容设置
        getToolbarTitle().setText("登录界面");
        getSubTitle().setText(null);
        getToolbar().setNavigationIcon(null);

        iv_head = (ImageView) findViewById(R.id.login_iv_head);
        et_name = (EditText) findViewById(R.id.login_et_name);
        et_password = (EditText) findViewById(R.id.login_et_password);
        login_btn = (Button) findViewById(R.id.login_btn);
    }

    @Override
    public void doBusiness(Context mContext) {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    validateEdit(et_name,"用户名不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    validateEdit(et_password,"密码不能为空！");
                    return;
                }

                //校验数字密码
                if (!Regex.isSTR_NUM(password)) {
                    validateEdit(et_password,"密码错误！");
                    return;
                }
                /*startActivity(new Intent(LoginActivity.this, MainActivity.class));*/
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }


}
