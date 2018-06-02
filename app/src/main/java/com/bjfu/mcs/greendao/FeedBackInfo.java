package com.bjfu.mcs.greendao;

import com.bjfu.mcs.bean.PersonInfo;

import cn.bmob.v3.BmobObject;

/**
 * Created by ly on 2018/6/2.
 */

public class FeedBackInfo extends BmobObject {

    private String type;
    private String question;
    private String activity;
    private String frequence;
    private String othercontent;
    private String content;
    private String mobile;
    private String deviceIMEI;
    private String appversion;
    private String deviceandroid;
    private String deviceCPU;
    public PersonInfo personInfo;
    public String installationId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getFrequence() {
        return frequence;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public String getOthercontent() {
        return othercontent;
    }

    public void setOthercontent(String othercontent) {
        this.othercontent = othercontent;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getDeviceandroid() {
        return deviceandroid;
    }

    public void setDeviceandroid(String deviceandroid) {
        this.deviceandroid = deviceandroid;
    }

    public String getDeviceCPU() {
        return deviceCPU;
    }

    public void setDeviceCPU(String deviceCPU) {
        this.deviceCPU = deviceCPU;
    }


    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public FeedBackInfo() {
    }

    public FeedBackInfo(String mobile, String content) {
        this.mobile = mobile;
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
