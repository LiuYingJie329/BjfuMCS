package com.bjfu.mcs.greendao;

import com.bjfu.mcs.bean.PersonInfo;

import cn.bmob.v3.BmobObject;

/**
 * Created by ly on 2018/6/2.
 */

public class PersonPushSet extends BmobObject {
    public PersonInfo personInfo;
    private String personid;
    private boolean isopen_notifi;
    private String openstarttime;
    private String openendtime;

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getOpencool() {
        return opencool;
    }

    public void setOpencool(String opencool) {
        this.opencool = opencool;
    }

    private String opencool;

    public boolean isIsopen_notifi() {
        return isopen_notifi;
    }

    public void setIsopen_notifi(boolean isopen_notifi) {
        this.isopen_notifi = isopen_notifi;
    }

    public String getOpenstarttime() {
        return openstarttime;
    }

    public void setOpenstarttime(String openstarttime) {
        this.openstarttime = openstarttime;
    }

    public String getOpenendtime() {
        return openendtime;
    }

    public void setOpenendtime(String openendtime) {
        this.openendtime = openendtime;
    }


}
