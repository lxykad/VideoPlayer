package com.lxy.livedata.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lxy.livedata.base.BaseApplication;

/**
 * @author a
 * @date 2018/1/25
 */

public class LoadingUtil {

    private static KProgressHUD mProgressHUD;

    private LoadingUtil() {
    }

    public static void showLoading(Context context) {
        getInstance(context).show();
    }

    public static void dismiss(Context context) {

        getInstance(context).dismiss();
        mProgressHUD = null;
    }

    public static KProgressHUD getInstance(Context context) {

        if (mProgressHUD == null) {

            mProgressHUD = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Downloading data")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
        }

        return mProgressHUD;
    }

}
