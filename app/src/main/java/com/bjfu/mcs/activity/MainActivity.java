package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.bjfu.mcs.R;
import com.bjfu.mcs.R2;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.base.CheckPermissionsActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.DataBaseHandler;
import com.bjfu.mcs.loginSign.LoginActivity;
import com.bjfu.mcs.map.DynamicDemo;
import com.bjfu.mcs.map.MyOrientationListener;
import com.bjfu.mcs.map.NavigationActivity;
import com.bjfu.mcs.map.RoutePlanDemo;
import com.bjfu.mcs.map.util.LocationManager;
import com.bjfu.mcs.upush.SplashTestActivity;
import com.bjfu.mcs.upush.UpushActivity;
import com.bjfu.mcs.utils.Constants;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxDeviceTool;
import com.bjfu.mcs.utils.Rx.RxSPTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.umeng.message.inapp.InAppMessageManager;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.bjfu.mcs.application.MCSApplication.appcache;
import static com.bjfu.mcs.utils.Rx.RxToast.showToast;


public class MainActivity extends CheckPermissionsActivity implements OnGetRoutePlanResultListener {

    @BindView(R.id.frame_container)
    FrameLayout frame_container;
    @BindView(R.id.id_bmapView)
    MapView mMapView;
    @BindView(R.id.bike_layout)
    LinearLayout bike_layout;
    @BindView(R.id.bike_distance_layout)
    LinearLayout bike_distance_layout;
    @BindView(R.id.bike_info_layout)
    LinearLayout bike_info_layout;
    @BindView(R.id.confirm_cancel_layout)
    LinearLayout confirm_cancel_layout;

    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private double currentLatitude, currentLongitude, changeLatitude, changeLongitude;
    public static TextView current_addr;
    private boolean isFirstLoc = true; // 是否首次定位
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    public static Handler mHandler = null;
    //定位相关
    private BaiduMap mBaiduMap;
    LocationClient mlocationClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private MyOrientationListener myOrientationListener;
    RoutePlanSearch mSearch;    // 搜索模块，也可去掉地图模块独立使用
    private float mCurrentX;
    private boolean hasPlanRoute = false;
    LatLng currentLL;
    PlanNode startNodeStr, endNodeStr;
    IProfile profile,profile2;
    //当前用户信息
    private String userName = null;
    private String userEmail = null;
    private String userImage = null;
    private String userImageUrl = null;
    Bitmap bitmap = null;
    private static final int updateIMEI =2;
    private static final int updateImageUrl = 3;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.upLoad:
                        RxToast.success("定位成功");
                        Toast.makeText(mContext,"定位成功",Toast.LENGTH_LONG).show();
                        break;
                    case updateIMEI:
                        String Imei = (String) msg.obj;
                        PersonInfo personInfoIMEI = BmobUser.getCurrentUser(PersonInfo.class);
                        personInfoIMEI.setDeviceIMEI(Imei);
                        personInfoIMEI.update(personInfoIMEI.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                    if(null != piInfo){
                                        piInfo.setDeviceIMEI(Imei);
                                    }
                                    DataBaseHandler.updatePesonInfo(piInfo);
                                    Log.i(TAG,"IMEI信息更新成功");
                                    RxToast.success("IMEI信息更新成功");
                                }else {
                                    if(e.getErrorCode() == 206){
                                        RxToast.error("您的账号在其他地方登录，请重新登录");
                                        appcache.put("has_login", "no");
                                        PersonInfo.logOut();
                                        RxActivityTool.skipActivityAndFinish(MainActivity.this, LoginActivity.class);
                                    }else{
                                        Log.i(TAG,"IMEI信息更新失败");
                                        RxToast.error("IMEI信息更新失败");
                                    }
                                }
                            }
                        });
                        break;
                    case updateImageUrl:
                        String content= (String)msg.obj;
                        try {
                            bitmap = Glide.with(mContext).load(content).asBitmap().into(200, 200).get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }

            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("首页");

        inituserdata();
        // Create a few sample profile
        if(!RxDataTool.isNullString(userImage)){

            profile = new ProfileDrawerItem().withName(userName).withEmail(userEmail).withIcon(Uri.parse(userImage));

        }else{

            profile = new ProfileDrawerItem().withName(userName).withEmail(userEmail).withIcon(R.drawable.profile);
        }

        profile2 = new ProfileDrawerItem().withName("MCS").withEmail("2236746458@qq.com").withIcon(R.drawable.profile2);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile, profile2,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("添加用户").withDescription("添加新用户邮箱").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(100000),
                        new ProfileSettingDrawerItem().withName("管理用户组").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("地图展示").withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIdentifier(1),
                        new PrimaryDrawerItem().withName("excel展示").withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("图表展示").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withName("推送服务").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                        new PrimaryDrawerItem().withDescription("经度\n纬度").withName("定位").withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                        new PrimaryDrawerItem().withName("我的主页").withIcon(FontAwesome.Icon.faw_user).withIdentifier(6),
                        new SectionDrawerItem().withName("其他"),
                        new SecondaryDrawerItem().withName("设置").withIcon(FontAwesome.Icon.faw_info).withIdentifier(7),
                        new SecondaryDrawerItem().withName("关于").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(8),
                        new SecondaryDrawerItem().withName("系统设置").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(9),
                        new SecondaryDrawerItem().withName("反馈").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(10),
                        new SecondaryDrawerItem().withName("活动添加").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(11),
                        new SecondaryDrawerItem().withName("位置详情").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(12),
                        new SecondaryDrawerItem().withName("轨迹路线").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(13),
                        new SecondaryDrawerItem().withName("路径导航").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(14),
                        new SecondaryDrawerItem().withName("路线规划").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(15),
                        new SecondaryDrawerItem().withName("推送系统").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(16),
                        new SecondaryDrawerItem().withName("足迹记录").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(17)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                RxToast.normal(drawerItem.getIdentifier() + "");

                            } else if (drawerItem.getIdentifier() == 2) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, ExcelActivity.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, ChartMainActivity.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, SetPushActivity.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, LocationActivity.class);
                            } else if(drawerItem.getIdentifier() == 6){
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, MeActivity.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, SettingActivity.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, NewAboutActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 9) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, NewSettingActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 10) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, FeedBackActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 11) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, Selectctivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 12) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, SetPlaceActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 13) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, DynamicDemo.class);
                            }
                            else if (drawerItem.getIdentifier() == 14) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, NavigationActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 15) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, RoutePlanDemo.class);
                            }
                            else if (drawerItem.getIdentifier() == 16) {
                                InAppMessageManager mInAppMessageManager = InAppMessageManager.getInstance(mContext);
                                mInAppMessageManager.setInAppMsgDebugMode(true);
                                mInAppMessageManager.setMainActivityPath("com.bjfu.mcs.upush.UpushActivity");
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, UpushActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 17) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, ZuJiActivity.class);
                            }

                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        final MiniDrawer miniResult = result.getMiniDrawer();
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

        initMap();

        initDialogs();
    }

    private void inituserdata() {
        PersonInfo personInfo = DataBaseHandler.getCurrPesonInfo();
        if(null != personInfo){
            if(!RxDataTool.isNullString(personInfo.getSqlemail())){
                userEmail = personInfo.getSqlemail();
            }
            if(!RxDataTool.isNullString(personInfo.getUserNickname())){
                userName = personInfo.getUserNickname();
            }
//            if(!RxDataTool.isNullString(personInfo.getUserAvatar())){
//                updatePersonInfoMsg(updateImageUrl,personInfo.getUserAvatar());
//            }
            if(!RxDataTool.isNullString(RxSPTool.getContent(mContext,"AVATAR"))){
                userImage = RxSPTool.getContent(mContext,"AVATAR");
            }

            String imei = RxDeviceTool.getUniqueSerialNumber();
            if(!RxDataTool.isNullString(imei)){
                updatePersonInfoMsg(updateIMEI,imei);
            }
        }

    }


    private void updatePersonInfoMsg(int type,String content){
        Message msg = new Message();
        msg.what = type;
        msg.obj =  content;
        mHandler.sendMessage(msg);
    }

    private void initDialogs() {

        /*升级对话框*/
        /*new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title("发现新版本")
                .content(R.string.update_content, true)
                .positiveText("同意升级")
                .negativeText("暂不升级")
                .show();*/

        /*退出原因对话框*/
       /* new MaterialDialog.Builder(this)
                .title(R.string.quickresons)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(
                        new Integer[] {1, 3},
                        (dialog, which, text) -> {
                            StringBuilder str = new StringBuilder();
                            for (int i = 0; i < which.length; i++) {
                                if (i > 0) {
                                    str.append('\n');
                                }
                                str.append(which[i]);
                                str.append(": ");
                                str.append(text[i]);
                            }
                            showToast(str.toString());
                            return true; // allow selection
                        })
                .onNeutral((dialog, which) -> dialog.clearSelectedIndices())
                .onPositive((dialog, which) -> dialog.dismiss())
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.sure)
                .autoDismiss(false)
                .neutralText(R.string.clear_selection)
                .show();*/


    }


    @OnClick({R.id.btn_locale})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_locale:
                getMyLocation();
                addOverLayout(currentLatitude, currentLongitude);
                break;
            default:
                break;
        }
    }
    public void getMyLocation() {
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }
    private void addOverLayout(double _latitude, double _longitude) {
        //先清除图层
        mBaiduMap.clear();
        mlocationClient.requestLocation();
        // 定义Maker坐标点
        LatLng point = new LatLng(_latitude, _longitude);
        // 构建MarkerOption，用于在地图上添加Marker
        MarkerOptions options = new MarkerOptions().position(point)
                .icon( BitmapDescriptorFactory.fromResource(R.drawable.drag_location));
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(options);
    }
    private void initMap() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        // 隐藏百度的LOGO
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mlocationClient = new LocationClient(this);
        mlocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(2000);//设置onReceiveLocation()获取位置的频率
        option.setIsNeedAddress(true);//如想获得具体位置就需要设置为true
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        mlocationClient.setLocOption(option);
        mlocationClient.start();
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        myOrientationListener = new MyOrientationListener(this);
        //通过接口回调来实现实时方向的改变
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
        myOrientationListener.start();
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        initMarkerClickEvent();
    }

    private void initMarkerClickEvent() {
        // 对Marker的点击
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                // 获得marker中的数据
                if (marker != null && marker.getExtraInfo() != null) {
                    //数据库
                    updateBikeInfo();
                }
                return true;
            }
        });
    }

    private void updateBikeInfo() {

        if (!hasPlanRoute) {
            bike_layout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // map view 销毁后不在处理新接收的位置
            if (bdLocation == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(mCurrentX)//设定图标方向     // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            currentLatitude = bdLocation.getLatitude();
            currentLongitude = bdLocation.getLongitude();
            //current_addr.setText(bdLocation.getAddrStr());
            currentLL = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            LocationManager.getInstance().setCurrentLL(currentLL);
            LocationManager.getInstance().setAddress(bdLocation.getAddrStr());
            startNodeStr = PlanNode.withLocation(currentLL);
            //option.setScanSpan(2000)，每隔2000ms这个方法就会调用一次，而有些我们只想调用一次，所以要判断一下isFirstLoc
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                //地图缩放比设置为18
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                changeLatitude = bdLocation.getLatitude();
                changeLongitude = bdLocation.getLongitude();

                Log.i("MAINACTIVITY--->","语义化信息-->"+bdLocation.getLocationDescribe()+"--->兴趣点语义化信息-->"+bdLocation.getPoiList());
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mlocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
