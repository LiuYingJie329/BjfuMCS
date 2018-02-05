package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
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
import com.bjfu.mcs.map.MyOrientationListener;
import com.bjfu.mcs.map.util.LocationManager;
import com.bjfu.mcs.utils.Constants;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends CheckPermissionsActivity implements OnGetRoutePlanResultListener {

//    @BindView(R.id.frame_container)
//    FrameLayout frame_container;
//    @BindView(R.id.id_bmapView)
//    MapView mMapView;
//    @BindView(R.id.bike_layout)
//    LinearLayout bike_layout;
//    @BindView(R.id.bike_distance_layout)
//    LinearLayout bike_distance_layout;
//    @BindView(R.id.bike_info_layout)
//    LinearLayout bike_info_layout;
//    @BindView(R.id.confirm_cancel_layout)
//    LinearLayout confirm_cancel_layout;

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

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(MCSApplication.getApplication());
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.upLoad:
                        RxToast.success("定位成功");
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
        getSupportActionBar().setTitle(R.string.drawer_item_crossfade_drawer_layout_drawer);

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile, profile2
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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("地图展示").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withName("图形展示").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                        new PrimaryDrawerItem().withDescription("经度\n纬度").withName("定位").withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName("设置").withIcon(FontAwesome.Icon.faw_info).withIdentifier(6),
                        new SecondaryDrawerItem().withName("关于").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(7)
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
                            } else if (drawerItem.getIdentifier() == 3) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                            } else if (drawerItem.getIdentifier() == 4) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                            } else if (drawerItem.getIdentifier() == 5) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                            } else if (drawerItem.getIdentifier() == 6) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, SettingActivity.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                RxToast.normal(drawerItem.getIdentifier() + "");
                                RxActivityTool.skipActivity(MainActivity.this, AboutActivity.class);
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

        //initMap();
    }


//    private void initMap() {
//        // 地图初始化
//        mMapView = (MapView) findViewById(R.id.id_bmapView);
//        mBaiduMap = mMapView.getMap();
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        // 定位初始化
//        mlocationClient = new LocationClient(this);
//        mlocationClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(2000);//设置onReceiveLocation()获取位置的频率
//        option.setIsNeedAddress(true);//如想获得具体位置就需要设置为true
//        mlocationClient.setLocOption(option);
//        mlocationClient.start();
//        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//                mCurrentMode, true, null));
//        myOrientationListener = new MyOrientationListener(this);
//        //通过接口回调来实现实时方向的改变
//        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
//            @Override
//            public void onOrientationChanged(float x) {
//                mCurrentX = x;
//            }
//        });
//        myOrientationListener.start();
//        mSearch = RoutePlanSearch.newInstance();
//        mSearch.setOnGetRoutePlanResultListener(this);
//        initMarkerClickEvent();
//    }

//    private void initMarkerClickEvent() {
//        // 对Marker的点击
//        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(final Marker marker) {
//                // 获得marker中的数据
//                if (marker != null && marker.getExtraInfo() != null) {
//                    //数据库
//                    updateBikeInfo();
//                }
//                return true;
//            }
//        });
//    }

//    private void updateBikeInfo() {
//
//        if (!hasPlanRoute) {
//            bike_layout.setVisibility(View.VISIBLE);
//
//        }
//    }

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
//            if (bdLocation == null || mMapView == null) {
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(bdLocation.getRadius())
//                    .direction(mCurrentX)//设定图标方向     // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .latitude(bdLocation.getLatitude())
//                    .longitude(bdLocation.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            currentLatitude = bdLocation.getLatitude();
//            currentLongitude = bdLocation.getLongitude();
//            current_addr.setText(bdLocation.getAddrStr());
//            currentLL = new LatLng(bdLocation.getLatitude(),
//                    bdLocation.getLongitude());
//            LocationManager.getInstance().setCurrentLL(currentLL);
//            LocationManager.getInstance().setAddress(bdLocation.getAddrStr());
//            startNodeStr = PlanNode.withLocation(currentLL);
//            //option.setScanSpan(2000)，每隔2000ms这个方法就会调用一次，而有些我们只想调用一次，所以要判断一下isFirstLoc
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(bdLocation.getLatitude(),
//                        bdLocation.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                //地图缩放比设置为18
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                changeLatitude = bdLocation.getLatitude();
//                changeLongitude = bdLocation.getLongitude();
//            }
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

}
