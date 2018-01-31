package com.bjfu.mcs.utils.other;

/**
 * Created by ly on 2018/1/30.
 */
import android.os.Build;

public class SdkUtil {

    public static boolean sdkVersionGe(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    public static boolean sdkVersionEq(int version) {
        return Build.VERSION.SDK_INT == version;
    }

    public static boolean sdkVersionLt(int version) {
        return Build.VERSION.SDK_INT < version;
    }

    public static boolean sdkVersionGe19() {
        return sdkVersionGe(19);
    }

    public static boolean sdkVersionGe21() {
        return sdkVersionGe(21);
    }
}
