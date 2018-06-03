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

    public static String KEY_SPLASH_PIC_URL = "key_splash_pic_url";
    public static int SPLASH_PIC_URL_SAVE_TIME =  60 * 60 * 1; //1小时


    public static String KEY_JOKE_TEXT_URL = "key_joke_text_url";
    public static String KEY_JOKE_PIC_URL = "key_joke_pic_url";

    public static int JOKE_TEXT_OR_PIC_URL_SAVE_TIME =  24 * 60 * 60 * 1000; //24小时


    public static String KEY_LAST_UPDATE_CURR_PERSONINFO_TIME = "key_last_update_curr_personinfo_time";
    public static long UPDATE_CURR_PERSONINFO_INTERVAL = 5 * 60 * 1000;// 5分钟


    public static String KEY_LAST_CHECK_UPDATE_TIME = "key_last_check_update_time";
    public static long CHECK_UPDATE_INTERVAL = 10 * 60 * 1000;// 10分钟


    public static String KEY_LAST_SHOW_NOTICE_TIME = "key_last_show_notice_time";
    public static long SHOW_NOTICE_INTERVAL =  24 * 60 * 60 * 1000;// 24小时

    public static String KEY_SIGN_DATE= "key_sign_date";

    public static String KEY_CURR_SERVER_TIME = "key_curr_server_time";

    // 日志过期时间，默认为10天
    public static int LOG_EXPIRED_TIME = 10;


}
