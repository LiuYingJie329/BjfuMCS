package com.bjfu.mcs.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.lang.reflect.Array;

import cn.bmob.v3.BmobUser;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ly on 2018/5/24.
 */
@Entity
public class PersonInfo extends BmobUser {
    @Id
    private Long id;

    private String sqlusername;
    private String sqlpassword;
    private Boolean sqlmobilePhoneNumberVerified;
    private String sqlmobilePhoneNumber;
    private Boolean sqlemailVerified;
    private String sqlemail;

    private String userNickname;
    private String userTag;
    private String loginType;
    private String userActs;
    private String userAddress;
    private String userAvatar;
    private String userBirthday;
    private String userChannels;
    private String userConstellation;
    private String userHome;
    private String userSchool;
    private String userCompany;
    private String usersex;
    private String userSign;
    private String deviceIMEI;
    @Generated(hash = 551911186)
    public PersonInfo(Long id, String sqlusername, String sqlpassword,
            Boolean sqlmobilePhoneNumberVerified, String sqlmobilePhoneNumber,
            Boolean sqlemailVerified, String sqlemail, String userNickname,
            String userTag, String loginType, String userActs, String userAddress,
            String userAvatar, String userBirthday, String userChannels,
            String userConstellation, String userHome, String userSchool,
            String userCompany, String usersex, String userSign,
            String deviceIMEI) {
        this.id = id;
        this.sqlusername = sqlusername;
        this.sqlpassword = sqlpassword;
        this.sqlmobilePhoneNumberVerified = sqlmobilePhoneNumberVerified;
        this.sqlmobilePhoneNumber = sqlmobilePhoneNumber;
        this.sqlemailVerified = sqlemailVerified;
        this.sqlemail = sqlemail;
        this.userNickname = userNickname;
        this.userTag = userTag;
        this.loginType = loginType;
        this.userActs = userActs;
        this.userAddress = userAddress;
        this.userAvatar = userAvatar;
        this.userBirthday = userBirthday;
        this.userChannels = userChannels;
        this.userConstellation = userConstellation;
        this.userHome = userHome;
        this.userSchool = userSchool;
        this.userCompany = userCompany;
        this.usersex = usersex;
        this.userSign = userSign;
        this.deviceIMEI = deviceIMEI;
    }
    @Generated(hash = 1597442618)
    public PersonInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSqlusername() {
        return this.sqlusername;
    }
    public void setSqlusername(String sqlusername) {
        this.sqlusername = sqlusername;
    }
    public String getSqlpassword() {
        return this.sqlpassword;
    }
    public void setSqlpassword(String sqlpassword) {
        this.sqlpassword = sqlpassword;
    }
    public Boolean getSqlmobilePhoneNumberVerified() {
        return this.sqlmobilePhoneNumberVerified;
    }
    public void setSqlmobilePhoneNumberVerified(
            Boolean sqlmobilePhoneNumberVerified) {
        this.sqlmobilePhoneNumberVerified = sqlmobilePhoneNumberVerified;
    }
    public String getSqlmobilePhoneNumber() {
        return this.sqlmobilePhoneNumber;
    }
    public void setSqlmobilePhoneNumber(String sqlmobilePhoneNumber) {
        this.sqlmobilePhoneNumber = sqlmobilePhoneNumber;
    }
    public Boolean getSqlemailVerified() {
        return this.sqlemailVerified;
    }
    public void setSqlemailVerified(Boolean sqlemailVerified) {
        this.sqlemailVerified = sqlemailVerified;
    }
    public String getSqlemail() {
        return this.sqlemail;
    }
    public void setSqlemail(String sqlemail) {
        this.sqlemail = sqlemail;
    }
    public String getUserNickname() {
        return this.userNickname;
    }
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
    public String getUserTag() {
        return this.userTag;
    }
    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }
    public String getLoginType() {
        return this.loginType;
    }
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    public String getUserActs() {
        return this.userActs;
    }
    public void setUserActs(String userActs) {
        this.userActs = userActs;
    }
    public String getUserAddress() {
        return this.userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public String getUserAvatar() {
        return this.userAvatar;
    }
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
    public String getUserBirthday() {
        return this.userBirthday;
    }
    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }
    public String getUserChannels() {
        return this.userChannels;
    }
    public void setUserChannels(String userChannels) {
        this.userChannels = userChannels;
    }
    public String getUserConstellation() {
        return this.userConstellation;
    }
    public void setUserConstellation(String userConstellation) {
        this.userConstellation = userConstellation;
    }
    public String getUserHome() {
        return this.userHome;
    }
    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }
    public String getUserSchool() {
        return this.userSchool;
    }
    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }
    public String getUserCompany() {
        return this.userCompany;
    }
    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }
    public String getUsersex() {
        return this.usersex;
    }
    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }
    public String getUserSign() {
        return this.userSign;
    }
    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }
    public String getDeviceIMEI() {
        return this.deviceIMEI;
    }
    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

   
}
