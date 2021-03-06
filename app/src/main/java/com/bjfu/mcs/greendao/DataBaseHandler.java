package com.bjfu.mcs.greendao;

import android.text.TextUtils;

import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.utils.ConfigKey;

import java.util.List;

import static com.bjfu.mcs.application.MCSApplication.appcache;


public class DataBaseHandler {

    private static PersonInfoDao personInfoDao = MCSApplication.daoSession.getPersonInfoDao();
    private static LocationInfoDao locationInfoDao = MCSApplication.daoSession.getLocationInfoDao();


    public static void insertPesonInfo(PersonInfo personInfo) {
        personInfoDao.insertOrReplace(personInfo);
    }


    public static PersonInfo getCurrPesonInfo() {
        String mobile = appcache.getAsString(ConfigKey.KEY_CURR_LOGIN_MOBILE);
        if(TextUtils.isEmpty(mobile))
            return null;
        List<PersonInfo> personInfos  = personInfoDao.queryBuilder().where(PersonInfoDao.Properties.SqlmobilePhoneNumber.eq(mobile)).list();
        if (personInfos != null && personInfos.size() > 0){
            return  personInfos.get(0);
        }
        return null;
    }


    public static void updatePesonInfo(PersonInfo personInfo) {
        personInfoDao.insertOrReplace(personInfo);
    }

    public static void insertLocationInfo(LocationInfo locationInfo) {
        locationInfoDao.insertOrReplace(locationInfo);
    }
    public static List<LocationInfo> getAllLocationInfo() {
        return  locationInfoDao.loadAll();
    }

}
