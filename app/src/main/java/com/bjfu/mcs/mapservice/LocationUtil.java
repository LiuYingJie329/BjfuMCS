package com.bjfu.mcs.mapservice;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.DataBaseHandler;
import com.bjfu.mcs.greendao.LocationInfo;
import com.bjfu.mcs.greendao.LocationInfoDao;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxDeviceTool;
import com.bjfu.mcs.utils.Rx.RxLocationTool;
import com.bjfu.mcs.utils.Rx.RxNetTool;
import com.bjfu.mcs.utils.other.DateUtil;
import com.bjfu.mcs.utils.other.OtherUtil;
import com.bjfu.mcs.utils.other.TimeFormatUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.bjfu.mcs.application.MCSApplication.appcache;
import static com.bjfu.mcs.application.MCSApplication.getApplication;

/**
 * Created by ly on 2018/6/1.
 */

public class LocationUtil {
    private static final String TAG = LocationUtil.class.getSimpleName();
    private BaiduMapLocationService locationService;
    private static LocationUtil mLocationUtil = null;
    private LocationClient mLocationClient = null;//声明LocationClient类
    private LocationInfoDao locationInfoDao = MCSApplication.getApplication().getDaoInstance().getLocationInfoDao();
    public static final String ACTION_LOCATION_CHANGE = "action_location_change";
    private List<String> PoiList = new ArrayList<String>();
    private String phonename = null;
    private LocationClientOption option = null;
    private long firstTime = 0;

    private LocationUtil() {
        if (option == null) {
            option = new LocationClientOption();
            mLocationClient = new LocationClient(MCSApplication.context);
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
            option.setScanSpan(1 * 60 * 1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            option.setOpenGps(true);//可选，默认false,设置是否使用gps
            option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
            option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
            mLocationClient.setLocOption(option);
            BDLocationListener myListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myListener);    //注册监听函数
        }


    }

    public static LocationUtil getInstance() {
        if (null == mLocationUtil) {
            mLocationUtil = new LocationUtil();
        }
        return mLocationUtil;
    }


    public void startGetLocation() {
        mLocationClient.start();
        mLocationClient.requestLocation();
    }

    public void stopGetLocation() {
        mLocationClient.stop();
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            long secondTime = System.currentTimeMillis();
            try {
                phonename = RxDeviceTool.getUniqueSerialNumber() + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Math.abs(secondTime - firstTime) >= 1 * 60 * 1000) {
                if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                    Log.i("定位信息获取成功", "定位信息获取成功");
                    if (!RxDataTool.isNullString(location.getCity())) {
                        appcache.put("location_city", location.getCity());
                    }

                    if (location.getLatitude() == 0 || location.getLongitude() == 0)
                        return;

                    if (!TextUtils.isEmpty(appcache.getAsString("lat")) && !TextUtils.isEmpty(appcache.getAsString("lon"))) {
                        if (OtherUtil.equal(location.getLatitude(), Double.parseDouble(appcache.getAsString("lat"))) &&
                                OtherUtil.equal(location.getLongitude(), Double.parseDouble(appcache.getAsString("lon"))))//避免上传相同的位置到云端
                            return;

                        double lastlat = Double.parseDouble(appcache.getAsString("lat"));
                        double lastlon = Double.parseDouble(appcache.getAsString("lon"));

                        if (lastlat > 0 && lastlon > 0) {
                            LatLng lastLatLng = new LatLng(lastlat, lastlon);
                            LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            //判断点newPt是否在，以lastPt为中心点，50为半径的圆内
                            boolean isInner = SpatialRelationUtil.isCircleContainsPoint(lastLatLng, 50, newLatLng);
                            if (isInner) {
                                return;
                            }
                        }
                    }

                    LocationInfo locationInfo = new LocationInfo();
                    firstTime = System.currentTimeMillis();
                    locationInfo.setSuccess("Success");   //请求成功
                    PersonInfo user = BmobUser.getCurrentUser(PersonInfo.class);
                    if (user != null) {
                        locationInfo.setUserId(user.getObjectId());
                        locationInfo.setDevicephone(user.getUsername());
                    }

                    locationInfo.setDeviceName(phonename);  //设备名称
                    locationInfo.setClientTime(TimeFormatUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")); //手机端请求时间
                    locationInfo.setServerTime(location.getTime()); //服务端返回的时间,如果位置不发生变化，则时间不变
                    locationInfo.setLocType(location.getLocType() + ""); //定位类型
                    locationInfo.setLongTitude(location.getLongitude() + "");//经度
                    locationInfo.setLatitude(location.getLatitude() + ""); //纬度
                    locationInfo.setRadius(location.getRadius() + ""); //定位精度
                    locationInfo.setAddressStr(location.getAddrStr());// 地址信息
                    locationInfo.setLocationDescribe(location.getLocationDescribe());// 位置语义化信息

                    Log.i("语义化信息", location.getLocationDescribe() + "");

                    if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                        for (int i = 0; i < location.getPoiList().size(); i++) {
                            Poi poi = (Poi) location.getPoiList().get(i);
                            PoiList.add(poi.getName());
                            Log.i("兴趣点语义化信息", PoiList.toString());
                        }
                    }

                    locationInfo.setPoiList(PoiList.toString());

                    Log.i("兴趣点语义化信息", PoiList.toString());
                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
                        locationInfo.setDescribe("gps定位成功");
                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        // 网络定位结果

                        if (location.getOperators() == 0) {
                            locationInfo.setOperators("未知运营商");// 运营商信息
                        } else if (location.getOperators() == 1) {
                            locationInfo.setOperators("中国移动");// 运营商信息
                        } else if (location.getOperators() == 2) {
                            locationInfo.setOperators("中国联通");// 运营商信息
                        } else if (location.getOperators() == 3) {
                            locationInfo.setOperators("中国电信");// 运营商信息
                        }
                        //在网络定位结果的情况下，获取网络定位结果是通过基站定位得到的还是通过wifi定位得到的还是GPS得结果
                        locationInfo.setNetWorktype(location.getNetworkLocationType());
                        locationInfo.setDescribe("网络定位成功");
                    } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                        // 离线定位结果
                        locationInfo.setDescribe("离线定位成功，离线定位结果也是有效的");
                    } else if (location.getLocType() == BDLocation.TypeServerError) {
                        locationInfo.setDescribe("百度服务器网络定位失败");
                    } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                        locationInfo.setDescribe("网络不同导致定位失败，请检查网络是否通畅");
                    } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                        locationInfo.setDescribe("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                    }
                    locationInfo.setIsNetAble((RxNetTool.isConnected(MCSApplication.getApplication()) + "").contains("true") ? "Net true" : "Net false");//判断网络是否连接
                    locationInfo.setIsWifiAble((RxNetTool.isWifi(MCSApplication.getApplication()) + "").contains("true") ? "Wifi true" : "Wifi false");//判断网络连接方式是否为WIFI
                    locationInfo.setGpsStatus((RxLocationTool.isGpsEnabled(MCSApplication.getApplication()) + "").contains("true") ? "GPS true" : "GPS false");//GPS是否打开
                    locationInfoDao.insert(locationInfo);
                    Log.i("插入数据库成功", "插入数据库成功");
                    submitLocationInfo(locationInfo);

                    MCSApplication.getApplication().sendBroadcast(new Intent(ACTION_LOCATION_CHANGE).putExtra("curr_location", location));
                }
            }


        }

    }

    private void submitLocationInfo(final LocationInfo locationInfo) {
        if (null == locationInfo)
            return;
        if (!OtherUtil.hasLogin())
            return;
        PersonInfo user = BmobUser.getCurrentUser(PersonInfo.class);
        //添加一对一关联
        //locationInfo.piInfo = user.getObjectId();
        locationInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    DataBaseHandler.insertLocationInfo(locationInfo);
                    appcache.put("lat", locationInfo.getLatitude());
                    appcache.put("lon", locationInfo.getLongTitude());

                    appcache.put("isUploadLocation", "yes");
                    //speak("您当前位置是"+ locationInfo.getAddress() + locationInfo.getLocationdescribe());
                    System.out.println("—— 位置同步成功 ——");
                } else {
                    Log.i("—— 位置同步失败 ——", e.getMessage());
                    System.out.println("—— 位置同步失败 ——");
                }
            }
        });
    }


}
