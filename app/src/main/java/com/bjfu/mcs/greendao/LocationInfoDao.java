package com.bjfu.mcs.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCATION_INFO".
*/
public class LocationInfoDao extends AbstractDao<LocationInfo, Long> {

    public static final String TABLENAME = "LOCATION_INFO";

    /**
     * Properties of entity LocationInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AddressStr = new Property(1, String.class, "addressStr", false, "ADDRESS_STR");
        public final static Property LocType = new Property(2, String.class, "locType", false, "LOC_TYPE");
        public final static Property LongTitude = new Property(3, String.class, "longTitude", false, "LONG_TITUDE");
        public final static Property NetWorktype = new Property(4, String.class, "netWorktype", false, "NET_WORKTYPE");
        public final static Property PoiList = new Property(5, String.class, "poiList", false, "POI_LIST");
        public final static Property Radius = new Property(6, String.class, "radius", false, "RADIUS");
        public final static Property ServerTime = new Property(7, String.class, "serverTime", false, "SERVER_TIME");
        public final static Property Success = new Property(8, String.class, "Success", false, "SUCCESS");
        public final static Property UserId = new Property(9, String.class, "userId", false, "USER_ID");
        public final static Property LocationDescribe = new Property(10, String.class, "locationDescribe", false, "LOCATION_DESCRIBE");
        public final static Property Latitude = new Property(11, String.class, "latitude", false, "LATITUDE");
        public final static Property ClientTime = new Property(12, String.class, "clientTime", false, "CLIENT_TIME");
        public final static Property Describe = new Property(13, String.class, "describe", false, "DESCRIBE");
        public final static Property IsNetAble = new Property(14, String.class, "isNetAble", false, "IS_NET_ABLE");
        public final static Property IsWifiAble = new Property(15, String.class, "isWifiAble", false, "IS_WIFI_ABLE");
        public final static Property GpsStatus = new Property(16, String.class, "gpsStatus", false, "GPS_STATUS");
        public final static Property DeviceName = new Property(17, String.class, "deviceName", false, "DEVICE_NAME");
        public final static Property Devicephone = new Property(18, String.class, "devicephone", false, "DEVICEPHONE");
        public final static Property Operators = new Property(19, String.class, "operators", false, "OPERATORS");
    }


    public LocationInfoDao(DaoConfig config) {
        super(config);
    }
    
    public LocationInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCATION_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ADDRESS_STR\" TEXT," + // 1: addressStr
                "\"LOC_TYPE\" TEXT," + // 2: locType
                "\"LONG_TITUDE\" TEXT," + // 3: longTitude
                "\"NET_WORKTYPE\" TEXT," + // 4: netWorktype
                "\"POI_LIST\" TEXT," + // 5: poiList
                "\"RADIUS\" TEXT," + // 6: radius
                "\"SERVER_TIME\" TEXT," + // 7: serverTime
                "\"SUCCESS\" TEXT," + // 8: Success
                "\"USER_ID\" TEXT," + // 9: userId
                "\"LOCATION_DESCRIBE\" TEXT," + // 10: locationDescribe
                "\"LATITUDE\" TEXT," + // 11: latitude
                "\"CLIENT_TIME\" TEXT," + // 12: clientTime
                "\"DESCRIBE\" TEXT," + // 13: describe
                "\"IS_NET_ABLE\" TEXT," + // 14: isNetAble
                "\"IS_WIFI_ABLE\" TEXT," + // 15: isWifiAble
                "\"GPS_STATUS\" TEXT," + // 16: gpsStatus
                "\"DEVICE_NAME\" TEXT," + // 17: deviceName
                "\"DEVICEPHONE\" TEXT," + // 18: devicephone
                "\"OPERATORS\" TEXT);"); // 19: operators
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCATION_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocationInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String addressStr = entity.getAddressStr();
        if (addressStr != null) {
            stmt.bindString(2, addressStr);
        }
 
        String locType = entity.getLocType();
        if (locType != null) {
            stmt.bindString(3, locType);
        }
 
        String longTitude = entity.getLongTitude();
        if (longTitude != null) {
            stmt.bindString(4, longTitude);
        }
 
        String netWorktype = entity.getNetWorktype();
        if (netWorktype != null) {
            stmt.bindString(5, netWorktype);
        }
 
        String poiList = entity.getPoiList();
        if (poiList != null) {
            stmt.bindString(6, poiList);
        }
 
        String radius = entity.getRadius();
        if (radius != null) {
            stmt.bindString(7, radius);
        }
 
        String serverTime = entity.getServerTime();
        if (serverTime != null) {
            stmt.bindString(8, serverTime);
        }
 
        String Success = entity.getSuccess();
        if (Success != null) {
            stmt.bindString(9, Success);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(10, userId);
        }
 
        String locationDescribe = entity.getLocationDescribe();
        if (locationDescribe != null) {
            stmt.bindString(11, locationDescribe);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(12, latitude);
        }
 
        String clientTime = entity.getClientTime();
        if (clientTime != null) {
            stmt.bindString(13, clientTime);
        }
 
        String describe = entity.getDescribe();
        if (describe != null) {
            stmt.bindString(14, describe);
        }
 
        String isNetAble = entity.getIsNetAble();
        if (isNetAble != null) {
            stmt.bindString(15, isNetAble);
        }
 
        String isWifiAble = entity.getIsWifiAble();
        if (isWifiAble != null) {
            stmt.bindString(16, isWifiAble);
        }
 
        String gpsStatus = entity.getGpsStatus();
        if (gpsStatus != null) {
            stmt.bindString(17, gpsStatus);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(18, deviceName);
        }
 
        String devicephone = entity.getDevicephone();
        if (devicephone != null) {
            stmt.bindString(19, devicephone);
        }
 
        String operators = entity.getOperators();
        if (operators != null) {
            stmt.bindString(20, operators);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocationInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String addressStr = entity.getAddressStr();
        if (addressStr != null) {
            stmt.bindString(2, addressStr);
        }
 
        String locType = entity.getLocType();
        if (locType != null) {
            stmt.bindString(3, locType);
        }
 
        String longTitude = entity.getLongTitude();
        if (longTitude != null) {
            stmt.bindString(4, longTitude);
        }
 
        String netWorktype = entity.getNetWorktype();
        if (netWorktype != null) {
            stmt.bindString(5, netWorktype);
        }
 
        String poiList = entity.getPoiList();
        if (poiList != null) {
            stmt.bindString(6, poiList);
        }
 
        String radius = entity.getRadius();
        if (radius != null) {
            stmt.bindString(7, radius);
        }
 
        String serverTime = entity.getServerTime();
        if (serverTime != null) {
            stmt.bindString(8, serverTime);
        }
 
        String Success = entity.getSuccess();
        if (Success != null) {
            stmt.bindString(9, Success);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(10, userId);
        }
 
        String locationDescribe = entity.getLocationDescribe();
        if (locationDescribe != null) {
            stmt.bindString(11, locationDescribe);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(12, latitude);
        }
 
        String clientTime = entity.getClientTime();
        if (clientTime != null) {
            stmt.bindString(13, clientTime);
        }
 
        String describe = entity.getDescribe();
        if (describe != null) {
            stmt.bindString(14, describe);
        }
 
        String isNetAble = entity.getIsNetAble();
        if (isNetAble != null) {
            stmt.bindString(15, isNetAble);
        }
 
        String isWifiAble = entity.getIsWifiAble();
        if (isWifiAble != null) {
            stmt.bindString(16, isWifiAble);
        }
 
        String gpsStatus = entity.getGpsStatus();
        if (gpsStatus != null) {
            stmt.bindString(17, gpsStatus);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(18, deviceName);
        }
 
        String devicephone = entity.getDevicephone();
        if (devicephone != null) {
            stmt.bindString(19, devicephone);
        }
 
        String operators = entity.getOperators();
        if (operators != null) {
            stmt.bindString(20, operators);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocationInfo readEntity(Cursor cursor, int offset) {
        LocationInfo entity = new LocationInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // addressStr
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // locType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // longTitude
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // netWorktype
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // poiList
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // radius
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // serverTime
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // Success
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // userId
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // locationDescribe
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // latitude
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // clientTime
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // describe
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // isNetAble
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // isWifiAble
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // gpsStatus
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // deviceName
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // devicephone
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19) // operators
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocationInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAddressStr(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLocType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLongTitude(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNetWorktype(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPoiList(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRadius(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setServerTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSuccess(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUserId(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setLocationDescribe(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setLatitude(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setClientTime(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setDescribe(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsNetAble(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setIsWifiAble(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setGpsStatus(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setDeviceName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setDevicephone(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setOperators(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocationInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocationInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocationInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}