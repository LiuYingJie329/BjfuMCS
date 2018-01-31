package com.bjfu.mcs.utils.other;

import android.content.Context;
import android.util.TypedValue;

import com.bjfu.mcs.application.MCSApplication;

/**
 * Created by ly on 2018/1/30.
 */

public class EnvUtil {

    private static int sStatusBarHeight;

    public static int getActionBarSize(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return DensityUtil.dp2px(44);
    }

    public static int getStatusBarHeight() {
        if (sStatusBarHeight == 0) {
            int resourceId =
                    MCSApplication.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                sStatusBarHeight = MCSApplication.getApplication().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return sStatusBarHeight;
    }
}
