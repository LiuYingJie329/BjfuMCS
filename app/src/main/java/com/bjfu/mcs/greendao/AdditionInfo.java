package com.bjfu.mcs.greendao;

import com.bjfu.mcs.bean.PersonInfo;

import cn.bmob.v3.BmobObject;

/**
 * Created by ly on 2018/6/3.
 */

public class AdditionInfo extends BmobObject {
    public PersonInfo personInfo;
    private String personid;
    private String addcause;
    private String addtype;
    private String addtypeother;
    private String addaddress;
    private String addaddressother;
    private String addstarttime;
    private String addendtime;
    private String addalltime;
    private String addother;

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getAddcause() {
        return addcause;
    }

    public void setAddcause(String addcause) {
        this.addcause = addcause;
    }

    public String getAddtype() {
        return addtype;
    }

    public void setAddtype(String addtype) {
        this.addtype = addtype;
    }

    public String getAddtypeother() {
        return addtypeother;
    }

    public void setAddtypeother(String addtypeother) {
        this.addtypeother = addtypeother;
    }

    public String getAddaddress() {
        return addaddress;
    }

    public void setAddaddress(String addaddress) {
        this.addaddress = addaddress;
    }

    public String getAddaddressother() {
        return addaddressother;
    }

    public void setAddaddressother(String addaddressother) {
        this.addaddressother = addaddressother;
    }

    public String getAddstarttime() {
        return addstarttime;
    }

    public void setAddstarttime(String addstarttime) {
        this.addstarttime = addstarttime;
    }

    public String getAddendtime() {
        return addendtime;
    }

    public void setAddendtime(String addendtime) {
        this.addendtime = addendtime;
    }

    public String getAddalltime() {
        return addalltime;
    }

    public void setAddalltime(String addalltime) {
        this.addalltime = addalltime;
    }

    public String getAddother() {
        return addother;
    }

    public void setAddother(String addother) {
        this.addother = addother;
    }


}
