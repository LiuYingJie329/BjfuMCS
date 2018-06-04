package com.bjfu.mcs.greendao;

import com.bjfu.mcs.bean.PersonInfo;

import cn.bmob.v3.BmobObject;

/**
 * Created by ly on 2018/6/5.
 */

public class UserPlaceTimeInfo extends BmobObject {
    public PersonInfo personInfo;
    private String address;
    private String name;
    private String search;
    private String longitude;
    private String latitude;
    private String starttime;
    private String endtime;
    private String alltime;
    private String often;
    private String personid;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getStarttime() {

        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAlltime() {
        return alltime;
    }

    public void setAlltime(String alltime) {
        this.alltime = alltime;
    }

    public String getOften() {
        return often;
    }

    public void setOften(String often) {
        this.often = often;
    }
}
