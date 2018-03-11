package com.bjfu.mcs.utils;

import com.bjfu.mcs.utils.Rx.RxFileTool;

import java.io.File;

/**
 * Created by dingchao on 17-9-18.
 */

public class Constants {
    public static final boolean DEBUG = false;

    public static final String PACKAGE_NAME = "com.bjfu.mcs";

    //LocationService定位数据上传
    public static final int upLoad = 1;
    //本地数据库加密
    public static final String SQLITE_ENCRTPT = "mashangyingbanmahui";
    public static final String fileDir = RxFileTool.getRootPath().getAbsolutePath() + File.separator + "MCS" + File.separator + "Log";
    public static final String cacheDir = RxFileTool.getRootPath().getAbsolutePath() + File.separator + "MCS";

}
