package com.zxtc.collectiontools.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

/**
 * Created by Administrator on 2017/4/21.
 */

public class DialogUtils {

    private static ProgressDialog progressDialog;
    private static DialogUtils dialogUtils;

    public static DialogUtils buildLoadingProgress(Activity context) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
        }

        if (progressDialog.isShowing()) {
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        dialogUtils = new DialogUtils();
        return dialogUtils;
    }

    public DialogUtils show() {
        progressDialog.show();
        return dialogUtils;
    }

    public DialogUtils setMessage(String string) {
        progressDialog.setMessage(string);
        return dialogUtils;
    }

    public DialogUtils dismiss() {
        progressDialog.dismiss();
        return dialogUtils;
    }
}
