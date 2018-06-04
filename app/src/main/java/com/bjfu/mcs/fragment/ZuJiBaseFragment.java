package com.bjfu.mcs.fragment;


import android.app.Activity;

import com.bjfu.mcs.utils.widget.BaseProgressDialog;


public abstract class ZuJiBaseFragment extends android.support.v4.app.Fragment {

    private BaseProgressDialog mProgressDialog = null;
    /**
     * @author wulee
     */

    public void showProgressDialog(Activity activity, BaseProgressDialog.OnCancelListener cancelListener, boolean cancelable) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new BaseProgressDialog(activity);
        if (cancelListener != null) {
            mProgressDialog.setOnCancelListener(cancelListener);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public void showProgressDialog(Activity activity, boolean cancelable) {
        showProgressDialog(activity,null, cancelable);
    }

    public void stopProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.stop();
        }
        mProgressDialog = null;
    }
}
