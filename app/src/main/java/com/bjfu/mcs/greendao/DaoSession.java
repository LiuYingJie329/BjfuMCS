package com.bjfu.mcs.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.LocationInfo;
import com.bjfu.mcs.greendao.MapDetails;

import com.bjfu.mcs.greendao.PersonInfoDao;
import com.bjfu.mcs.greendao.LocationInfoDao;
import com.bjfu.mcs.greendao.MapDetailsDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig personInfoDaoConfig;
    private final DaoConfig locationInfoDaoConfig;
    private final DaoConfig mapDetailsDaoConfig;

    private final PersonInfoDao personInfoDao;
    private final LocationInfoDao locationInfoDao;
    private final MapDetailsDao mapDetailsDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        personInfoDaoConfig = daoConfigMap.get(PersonInfoDao.class).clone();
        personInfoDaoConfig.initIdentityScope(type);

        locationInfoDaoConfig = daoConfigMap.get(LocationInfoDao.class).clone();
        locationInfoDaoConfig.initIdentityScope(type);

        mapDetailsDaoConfig = daoConfigMap.get(MapDetailsDao.class).clone();
        mapDetailsDaoConfig.initIdentityScope(type);

        personInfoDao = new PersonInfoDao(personInfoDaoConfig, this);
        locationInfoDao = new LocationInfoDao(locationInfoDaoConfig, this);
        mapDetailsDao = new MapDetailsDao(mapDetailsDaoConfig, this);

        registerDao(PersonInfo.class, personInfoDao);
        registerDao(LocationInfo.class, locationInfoDao);
        registerDao(MapDetails.class, mapDetailsDao);
    }
    
    public void clear() {
        personInfoDaoConfig.clearIdentityScope();
        locationInfoDaoConfig.clearIdentityScope();
        mapDetailsDaoConfig.clearIdentityScope();
    }

    public PersonInfoDao getPersonInfoDao() {
        return personInfoDao;
    }

    public LocationInfoDao getLocationInfoDao() {
        return locationInfoDao;
    }

    public MapDetailsDao getMapDetailsDao() {
        return mapDetailsDao;
    }

}
