package com.bjfu.mcs.application;

import android.app.Application;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.bjfu.mcs.R;
import com.bjfu.mcs.greendao.DaoMaster;
import com.bjfu.mcs.greendao.DaoSession;
import com.bjfu.mcs.greendao.helper.DaoMasterOpenHelper;
import com.bjfu.mcs.keepalive.daemon.TraceServiceImpl;
import com.bjfu.mcs.keepalive.service.DaemonService;
import com.bjfu.mcs.keepalive.service.PlayerMusicService;
import com.bjfu.mcs.keepalive.service.UploadLocationService;
import com.bjfu.mcs.mapservice.BaiduMapLocationService;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.Rx.RxTool;
import com.bjfu.mcs.utils.log.Utils;
import com.bjfu.mcs.utils.network.NetChangeObserver;
import com.bjfu.mcs.utils.network.NetStateReceiver;
import com.bjfu.mcs.utils.network.NetworkUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.xdandroid.hellodaemon.DaemonEnv;

//import cn.bmob.push.BmobPush;
import java.io.File;
import java.lang.reflect.Field;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import common.Logger;

/**
 * Created by ly on 2018/1/17.
 * @author ly
 * mcs 实验系统application
 */

public class MCSApplication extends Application {

    private static final String TAG = MCSApplication.class.getName();

    public static Context context;

    private static MCSApplication application = null;
    public BaiduMapLocationService locationService;
    public Vibrator mVibrator;
    public static ApplicationCache appcache;

    public static DaoMaster daoMaster;
    public static DaoSession daoSession;

    public NetworkUtils.NetworkType mNetType;
    private NetStateReceiver netStateReceiver;

    public static String mSDCardPath;
    public static final String APP_FOLDER_NAME = "BjfuMCS";

    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        appcache = ApplicationCache.get(this);
        //工具类 初始化
        RxTool.init(application);
        // Log 写入txt文件 工具类 初始化
        Utils.init(application);
        //保活工具类初始化，需要在 Application 的 onCreate() 中调用一次 DaemonEnv.initialize()
        DaemonEnv.initialize(this, TraceServiceImpl.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
//        TraceServiceImpl.sShouldStopService = false;
//        UploadLocationService.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(UploadLocationService.class);
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);
        DaemonEnv.startServiceMayBind(DaemonService.class);
        DaemonEnv.startServiceMayBind(PlayerMusicService.class);
        /*百度初始化定位sdk*/
        locationService = new BaiduMapLocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        //SDKInitializer.setCoordType(OverlayItem.CoordType.CoordType_BD09LL);
        //腾讯bugly
        CrashReport.initCrashReport(getApplicationContext(), "5f9ff1ce3c", false);
        setupDatabase("locationdetails");
        //Bmob后端云
        //第一：默认初始化
        Bmob.initialize(this, "410847461a2086a65b522fb4da7c3967");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
        // 使用推送服务时的初始化操作,保存设备信息，用于推送功能
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {

                    Log.i("MCSApplication",bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Log.e("MCSApplication",e.getMessage());
                }
            }
        });
        // 启动推送服务
        //BmobPush.startWork(application);
        //Bmob短信初始化
        BmobSMS.initialize(this,"410847461a2086a65b522fb4da7c3967");

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        try {
            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
            Field[] fs = aClass.getDeclaredFields();
            for (Field f:fs){
                Log.e("xxxxxx","ff="+f.getName()+"   "+f.getType().getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
//        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
//            "669c30a9584623e70e8cd01b0381dcb4");

        UMConfigure.init(this, "5b0faa348f4a9d2fa2000036", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "bb7f2101bffada52d2fa623a88f10537");
        //PushSDK初始化(如使用推送SDK，必须调用此方法)
        initUpush();

        initNetChangeReceiver();
        initDirs();
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
        daoMaster = new DaoMaster(db);
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

    /**
     * 应用全局的网络变化处理
     */
    private void initNetChangeReceiver() {
        //获取当前网络类型
        mNetType = NetworkUtils.getNetworkType(this);
        //定义网络状态的广播接受者
        netStateReceiver = NetStateReceiver.getReceiver();
        //给广播接受者注册一个观察者
        netStateReceiver.registerObserver(netChangeObserver);
        //注册网络变化广播
        NetworkUtils.registerNetStateReceiver(this, netStateReceiver);
    }

    private NetChangeObserver netChangeObserver = new NetChangeObserver() {
        @Override
        public void onConnect(NetworkUtils.NetworkType type) {
            MCSApplication.this.onNetConnect(type);
        }
        @Override
        public void onDisConnect() {
            MCSApplication.this.onNetDisConnect();
        }
    };

    protected void onNetDisConnect() {
        RxToast.info("网络已断开,请检查网络设置");
        mNetType = NetworkUtils.NetworkType.NETWORK_NONE;
    }

    protected void onNetConnect(NetworkUtils.NetworkType type) {
        if (type == mNetType) return; //net not change
        switch (type) {
            case NETWORK_WIFI:
                RxToast.info("已切换到 WIFI 网络");
                break;
            case NETWORK_MOBILE:
                RxToast.info("已切换到 2G/3G/4G 网络");
                break;
        }
        mNetType = type;
    }

    //释放广播接受者(建议在 最后一个 Activity 退出前调用)
    private void destroyReceiver() {
        //移除里面的观察者
        netStateReceiver.removeObserver(netChangeObserver);
        //解注册广播接受者,
        try {
            NetworkUtils.unRegisterNetStateReceiver(this, netStateReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
        destroyReceiver();
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initUpush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        handler = new Handler(getMainLooper());

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

        // mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            /**
             * 通知的回调方法（通知送达时会回调）
             */
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                //调用super，会展示通知，不调用super，则不展示通知。
                super.dealWithNotificationMessage(context, msg);
            }

            /**
             * 自定义消息的回调方法
             */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
                                getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.i(TAG, "device token: " + deviceToken);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i(TAG, "register failed: " + s + " " + s1);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });

        //使用完全自定义处理
        //mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);

        //小米通道
        //MiPushRegistar.register(this, XIAOMI_ID, XIAOMI_KEY);
        //华为通道
        //HuaWeiRegister.register(this);
        //魅族通道
        //MeizuRegister.register(this, MEIZU_APPID, MEIZU_APPKEY);
    }
}
