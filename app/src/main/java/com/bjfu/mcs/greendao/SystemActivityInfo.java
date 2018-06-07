package com.bjfu.mcs.greendao;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by ly on 2018/6/7.
 */

public class SystemActivityInfo extends BmobObject {
    public String userdone;
    public String usertodo;
    public String userdoing;
    public String sysid;
    public String syscontent;
    public String starttime;
    public String endtime;

    public String getUserdone() {
        return userdone;
    }

    public void setUserdone(String userdone) {
        this.userdone = userdone;
    }

    public String getUsertodo() {
        return usertodo;
    }

    public void setUsertodo(String usertodo) {
        this.usertodo = usertodo;
    }

    public String getUserdoing() {
        return userdoing;
    }

    public void setUserdoing(String userdoing) {
        this.userdoing = userdoing;
    }




    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getSyscontent() {
        return syscontent;
    }

    public void setSyscontent(String syscontent) {
        this.syscontent = syscontent;
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



}
