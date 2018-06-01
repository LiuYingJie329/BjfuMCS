package com.bjfu.mcs.greendao;

import com.bjfu.mcs.bean.PersonInfo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import cn.bmob.v3.BmobObject;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Comparator;

/**
 * Created by ly on 2018/6/1.
 */

@Entity
public class LocationInfo extends BmobObject {
    @Id
    private Long id;

    //定位地址
    private String addressStr;
    //定位类型
    private String locType;
    //定位经度
    private String longTitude;
    //在网络定位结果的情况下，获取网络定位结果是通过基站定位得到的还是通过wifi定位得到的还是GPS得结果
    private String netWorktype;
    // POI信息
    private String poiList;
    // 半径
    private String radius;
    //服务端出本次结果的时间:
    private String serverTime;
    //定位是否成功+用户是否想主动退出。Success Fail Quit
    private String Success;
    //用户id
    private String userId;
    // 位置语义化信息
    private String locationDescribe;
    // 纬度
    private String latitude;
    //手机端请求服务器定位的时间
    private String clientTime;
    //定位描述
    private String describe;
    //是否有网络
    private String isNetAble;
    //wifi是否打开
    private String isWifiAble;
    //GPS是否打开
    private String gpsStatus;
    //设备名称
    private String deviceName;
    //手机号码
    private String devicephone;
    // 运营商信息
    private String operators;
    @Generated(hash = 1397830954)
    public LocationInfo(Long id, String addressStr, String locType,
            String longTitude, String netWorktype, String poiList, String radius,
            String serverTime, String Success, String userId,
            String locationDescribe, String latitude, String clientTime,
            String describe, String isNetAble, String isWifiAble, String gpsStatus,
            String deviceName, String devicephone, String operators) {
        this.id = id;
        this.addressStr = addressStr;
        this.locType = locType;
        this.longTitude = longTitude;
        this.netWorktype = netWorktype;
        this.poiList = poiList;
        this.radius = radius;
        this.serverTime = serverTime;
        this.Success = Success;
        this.userId = userId;
        this.locationDescribe = locationDescribe;
        this.latitude = latitude;
        this.clientTime = clientTime;
        this.describe = describe;
        this.isNetAble = isNetAble;
        this.isWifiAble = isWifiAble;
        this.gpsStatus = gpsStatus;
        this.deviceName = deviceName;
        this.devicephone = devicephone;
        this.operators = operators;
    }
    @Generated(hash = 1054559726)
    public LocationInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddressStr() {
        return this.addressStr;
    }
    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }
    public String getLocType() {
        return this.locType;
    }
    public void setLocType(String locType) {
        this.locType = locType;
    }
    public String getLongTitude() {
        return this.longTitude;
    }
    public void setLongTitude(String longTitude) {
        this.longTitude = longTitude;
    }
    public String getNetWorktype() {
        return this.netWorktype;
    }
    public void setNetWorktype(String netWorktype) {
        this.netWorktype = netWorktype;
    }
    public String getPoiList() {
        return this.poiList;
    }
    public void setPoiList(String poiList) {
        this.poiList = poiList;
    }
    public String getRadius() {
        return this.radius;
    }
    public void setRadius(String radius) {
        this.radius = radius;
    }
    public String getServerTime() {
        return this.serverTime;
    }
    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
    public String getSuccess() {
        return this.Success;
    }
    public void setSuccess(String Success) {
        this.Success = Success;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getLocationDescribe() {
        return this.locationDescribe;
    }
    public void setLocationDescribe(String locationDescribe) {
        this.locationDescribe = locationDescribe;
    }
    public String getLatitude() {
        return this.latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getClientTime() {
        return this.clientTime;
    }
    public void setClientTime(String clientTime) {
        this.clientTime = clientTime;
    }
    public String getDescribe() {
        return this.describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    public String getIsNetAble() {
        return this.isNetAble;
    }
    public void setIsNetAble(String isNetAble) {
        this.isNetAble = isNetAble;
    }
    public String getIsWifiAble() {
        return this.isWifiAble;
    }
    public void setIsWifiAble(String isWifiAble) {
        this.isWifiAble = isWifiAble;
    }
    public String getGpsStatus() {
        return this.gpsStatus;
    }
    public void setGpsStatus(String gpsStatus) {
        this.gpsStatus = gpsStatus;
    }
    public String getDeviceName() {
        return this.deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getDevicephone() {
        return this.devicephone;
    }
    public void setDevicephone(String devicephone) {
        this.devicephone = devicephone;
    }
    public String getOperators() {
        return this.operators;
    }
    public void setOperators(String operators) {
        this.operators = operators;
    }

}
