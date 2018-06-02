package com.bjfu.mcs.greendao;

import com.bjfu.mcs.bean.PersonInfo;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by ly on 2018/6/2.
 */

public class Installation extends BmobInstallation {

    public PersonInfo personInfo;
    private String deviceIMEI;
    private String pushToken;


    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }



    public Installation(){

    }
}
