package com.bjfu.mcs.application;

import android.app.Application;
import android.app.Service;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.bjfu.mcs.greendao.DaoMaster;
import com.bjfu.mcs.greendao.DaoSession;
import com.bjfu.mcs.greendao.helper.DaoMasterOpenHelper;
import com.bjfu.mcs.keepalive.daemon.TraceServiceImpl;
import com.bjfu.mcs.keepalive.service.DaemonService;
import com.bjfu.mcs.keepalive.service.PlayerMusicService;
import com.bjfu.mcs.mapservice.BaiduMapLocationService;
import com.bjfu.mcs.utils.Rx.RxTool;
import com.bjfu.mcs.utils.log.Utils;
import com.xdandroid.hellodaemon.DaemonEnv;

/**
 * Created by ly on 2018/1/17.
 * @author ly
 * mcs 实验系统application
 */

public class MCSApplication extends Application {
    private static MCSApplication application = null;
    public BaiduMapLocationService locationService;
    public Vibrator mVibrator;
    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //工具类 初始化
        RxTool.init(application);
        // Log 写入txt文件 工具类 初始化
        Utils.init(application);
        //保活工具类初始化，需要在 Application 的 onCreate() 中调用一次 DaemonEnv.initialize()
        DaemonEnv.initialize(this, TraceServiceImpl.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        TraceServiceImpl.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);
        DaemonEnv.startServiceMayBind(DaemonService.class);
        DaemonEnv.startServiceMayBind(PlayerMusicService.class);
        /*百度初始化定位sdk*/
        locationService = new BaiduMapLocationService(application);
        mVibrator =(Vibrator)application.getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(application);
    }

    private void setupDatabase(String str) {
        //初始化GreenDao
        //创建数据库，参数1：上下文，参数2：库名，参数3：游标工厂
        DaoMaster.OpenHelper openHelper = new DaoMasterOpenHelper(this,str);

        //实例化DaoMaster对象
        //加密
        //DaoMaster daoMaster = new DaoMaster(openHelper.getEncryptedReadableDb(Constants.SQLITE_ENCRTPT));
        //不加密
        SQLiteDatabase db = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        //实例化DaoSession对象
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoInstance() {
        return daoSession;
    }

    public MCSApplication(){
        super();;
    }
    public static MCSApplication getApplication(){
        return application;
    }
}
