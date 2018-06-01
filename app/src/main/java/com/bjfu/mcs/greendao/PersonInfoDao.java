package com.bjfu.mcs.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bjfu.mcs.bean.PersonInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PERSON_INFO".
*/
public class PersonInfoDao extends AbstractDao<PersonInfo, Long> {

    public static final String TABLENAME = "PERSON_INFO";

    /**
     * Properties of entity PersonInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Sqlusername = new Property(1, String.class, "sqlusername", false, "SQLUSERNAME");
        public final static Property Sqlpassword = new Property(2, String.class, "sqlpassword", false, "SQLPASSWORD");
        public final static Property SqlmobilePhoneNumberVerified = new Property(3, Boolean.class, "sqlmobilePhoneNumberVerified", false, "SQLMOBILE_PHONE_NUMBER_VERIFIED");
        public final static Property SqlmobilePhoneNumber = new Property(4, String.class, "sqlmobilePhoneNumber", false, "SQLMOBILE_PHONE_NUMBER");
        public final static Property SqlemailVerified = new Property(5, Boolean.class, "sqlemailVerified", false, "SQLEMAIL_VERIFIED");
        public final static Property Sqlemail = new Property(6, String.class, "sqlemail", false, "SQLEMAIL");
        public final static Property UserNickname = new Property(7, String.class, "userNickname", false, "USER_NICKNAME");
        public final static Property UserTag = new Property(8, String.class, "userTag", false, "USER_TAG");
        public final static Property LoginType = new Property(9, String.class, "loginType", false, "LOGIN_TYPE");
        public final static Property UserActs = new Property(10, String.class, "userActs", false, "USER_ACTS");
        public final static Property UserAddress = new Property(11, String.class, "userAddress", false, "USER_ADDRESS");
        public final static Property UserAvatar = new Property(12, String.class, "userAvatar", false, "USER_AVATAR");
        public final static Property UserBirthday = new Property(13, String.class, "userBirthday", false, "USER_BIRTHDAY");
        public final static Property UserChannels = new Property(14, String.class, "userChannels", false, "USER_CHANNELS");
        public final static Property UserConstellation = new Property(15, String.class, "userConstellation", false, "USER_CONSTELLATION");
        public final static Property UserHome = new Property(16, String.class, "userHome", false, "USER_HOME");
        public final static Property UserSchool = new Property(17, String.class, "userSchool", false, "USER_SCHOOL");
        public final static Property UserCompany = new Property(18, String.class, "userCompany", false, "USER_COMPANY");
        public final static Property Usersex = new Property(19, String.class, "usersex", false, "USERSEX");
        public final static Property UserSign = new Property(20, String.class, "userSign", false, "USER_SIGN");
        public final static Property DeviceIMEI = new Property(21, String.class, "deviceIMEI", false, "DEVICE_IMEI");
        public final static Property UserId = new Property(22, String.class, "userId", false, "USER_ID");
    }


    public PersonInfoDao(DaoConfig config) {
        super(config);
    }
    
    public PersonInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PERSON_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SQLUSERNAME\" TEXT," + // 1: sqlusername
                "\"SQLPASSWORD\" TEXT," + // 2: sqlpassword
                "\"SQLMOBILE_PHONE_NUMBER_VERIFIED\" INTEGER," + // 3: sqlmobilePhoneNumberVerified
                "\"SQLMOBILE_PHONE_NUMBER\" TEXT," + // 4: sqlmobilePhoneNumber
                "\"SQLEMAIL_VERIFIED\" INTEGER," + // 5: sqlemailVerified
                "\"SQLEMAIL\" TEXT," + // 6: sqlemail
                "\"USER_NICKNAME\" TEXT," + // 7: userNickname
                "\"USER_TAG\" TEXT," + // 8: userTag
                "\"LOGIN_TYPE\" TEXT," + // 9: loginType
                "\"USER_ACTS\" TEXT," + // 10: userActs
                "\"USER_ADDRESS\" TEXT," + // 11: userAddress
                "\"USER_AVATAR\" TEXT," + // 12: userAvatar
                "\"USER_BIRTHDAY\" TEXT," + // 13: userBirthday
                "\"USER_CHANNELS\" TEXT," + // 14: userChannels
                "\"USER_CONSTELLATION\" TEXT," + // 15: userConstellation
                "\"USER_HOME\" TEXT," + // 16: userHome
                "\"USER_SCHOOL\" TEXT," + // 17: userSchool
                "\"USER_COMPANY\" TEXT," + // 18: userCompany
                "\"USERSEX\" TEXT," + // 19: usersex
                "\"USER_SIGN\" TEXT," + // 20: userSign
                "\"DEVICE_IMEI\" TEXT," + // 21: deviceIMEI
                "\"USER_ID\" TEXT);"); // 22: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PERSON_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PersonInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sqlusername = entity.getSqlusername();
        if (sqlusername != null) {
            stmt.bindString(2, sqlusername);
        }
 
        String sqlpassword = entity.getSqlpassword();
        if (sqlpassword != null) {
            stmt.bindString(3, sqlpassword);
        }
 
        Boolean sqlmobilePhoneNumberVerified = entity.getSqlmobilePhoneNumberVerified();
        if (sqlmobilePhoneNumberVerified != null) {
            stmt.bindLong(4, sqlmobilePhoneNumberVerified ? 1L: 0L);
        }
 
        String sqlmobilePhoneNumber = entity.getSqlmobilePhoneNumber();
        if (sqlmobilePhoneNumber != null) {
            stmt.bindString(5, sqlmobilePhoneNumber);
        }
 
        Boolean sqlemailVerified = entity.getSqlemailVerified();
        if (sqlemailVerified != null) {
            stmt.bindLong(6, sqlemailVerified ? 1L: 0L);
        }
 
        String sqlemail = entity.getSqlemail();
        if (sqlemail != null) {
            stmt.bindString(7, sqlemail);
        }
 
        String userNickname = entity.getUserNickname();
        if (userNickname != null) {
            stmt.bindString(8, userNickname);
        }
 
        String userTag = entity.getUserTag();
        if (userTag != null) {
            stmt.bindString(9, userTag);
        }
 
        String loginType = entity.getLoginType();
        if (loginType != null) {
            stmt.bindString(10, loginType);
        }
 
        String userActs = entity.getUserActs();
        if (userActs != null) {
            stmt.bindString(11, userActs);
        }
 
        String userAddress = entity.getUserAddress();
        if (userAddress != null) {
            stmt.bindString(12, userAddress);
        }
 
        String userAvatar = entity.getUserAvatar();
        if (userAvatar != null) {
            stmt.bindString(13, userAvatar);
        }
 
        String userBirthday = entity.getUserBirthday();
        if (userBirthday != null) {
            stmt.bindString(14, userBirthday);
        }
 
        String userChannels = entity.getUserChannels();
        if (userChannels != null) {
            stmt.bindString(15, userChannels);
        }
 
        String userConstellation = entity.getUserConstellation();
        if (userConstellation != null) {
            stmt.bindString(16, userConstellation);
        }
 
        String userHome = entity.getUserHome();
        if (userHome != null) {
            stmt.bindString(17, userHome);
        }
 
        String userSchool = entity.getUserSchool();
        if (userSchool != null) {
            stmt.bindString(18, userSchool);
        }
 
        String userCompany = entity.getUserCompany();
        if (userCompany != null) {
            stmt.bindString(19, userCompany);
        }
 
        String usersex = entity.getUsersex();
        if (usersex != null) {
            stmt.bindString(20, usersex);
        }
 
        String userSign = entity.getUserSign();
        if (userSign != null) {
            stmt.bindString(21, userSign);
        }
 
        String deviceIMEI = entity.getDeviceIMEI();
        if (deviceIMEI != null) {
            stmt.bindString(22, deviceIMEI);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(23, userId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PersonInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sqlusername = entity.getSqlusername();
        if (sqlusername != null) {
            stmt.bindString(2, sqlusername);
        }
 
        String sqlpassword = entity.getSqlpassword();
        if (sqlpassword != null) {
            stmt.bindString(3, sqlpassword);
        }
 
        Boolean sqlmobilePhoneNumberVerified = entity.getSqlmobilePhoneNumberVerified();
        if (sqlmobilePhoneNumberVerified != null) {
            stmt.bindLong(4, sqlmobilePhoneNumberVerified ? 1L: 0L);
        }
 
        String sqlmobilePhoneNumber = entity.getSqlmobilePhoneNumber();
        if (sqlmobilePhoneNumber != null) {
            stmt.bindString(5, sqlmobilePhoneNumber);
        }
 
        Boolean sqlemailVerified = entity.getSqlemailVerified();
        if (sqlemailVerified != null) {
            stmt.bindLong(6, sqlemailVerified ? 1L: 0L);
        }
 
        String sqlemail = entity.getSqlemail();
        if (sqlemail != null) {
            stmt.bindString(7, sqlemail);
        }
 
        String userNickname = entity.getUserNickname();
        if (userNickname != null) {
            stmt.bindString(8, userNickname);
        }
 
        String userTag = entity.getUserTag();
        if (userTag != null) {
            stmt.bindString(9, userTag);
        }
 
        String loginType = entity.getLoginType();
        if (loginType != null) {
            stmt.bindString(10, loginType);
        }
 
        String userActs = entity.getUserActs();
        if (userActs != null) {
            stmt.bindString(11, userActs);
        }
 
        String userAddress = entity.getUserAddress();
        if (userAddress != null) {
            stmt.bindString(12, userAddress);
        }
 
        String userAvatar = entity.getUserAvatar();
        if (userAvatar != null) {
            stmt.bindString(13, userAvatar);
        }
 
        String userBirthday = entity.getUserBirthday();
        if (userBirthday != null) {
            stmt.bindString(14, userBirthday);
        }
 
        String userChannels = entity.getUserChannels();
        if (userChannels != null) {
            stmt.bindString(15, userChannels);
        }
 
        String userConstellation = entity.getUserConstellation();
        if (userConstellation != null) {
            stmt.bindString(16, userConstellation);
        }
 
        String userHome = entity.getUserHome();
        if (userHome != null) {
            stmt.bindString(17, userHome);
        }
 
        String userSchool = entity.getUserSchool();
        if (userSchool != null) {
            stmt.bindString(18, userSchool);
        }
 
        String userCompany = entity.getUserCompany();
        if (userCompany != null) {
            stmt.bindString(19, userCompany);
        }
 
        String usersex = entity.getUsersex();
        if (usersex != null) {
            stmt.bindString(20, usersex);
        }
 
        String userSign = entity.getUserSign();
        if (userSign != null) {
            stmt.bindString(21, userSign);
        }
 
        String deviceIMEI = entity.getDeviceIMEI();
        if (deviceIMEI != null) {
            stmt.bindString(22, deviceIMEI);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(23, userId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PersonInfo readEntity(Cursor cursor, int offset) {
        PersonInfo entity = new PersonInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sqlusername
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sqlpassword
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0, // sqlmobilePhoneNumberVerified
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sqlmobilePhoneNumber
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // sqlemailVerified
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sqlemail
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // userNickname
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // userTag
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // loginType
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // userActs
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // userAddress
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // userAvatar
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // userBirthday
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // userChannels
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // userConstellation
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // userHome
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // userSchool
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // userCompany
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // usersex
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // userSign
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // deviceIMEI
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22) // userId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PersonInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSqlusername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSqlpassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSqlmobilePhoneNumberVerified(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
        entity.setSqlmobilePhoneNumber(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSqlemailVerified(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setSqlemail(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUserNickname(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUserTag(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLoginType(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUserActs(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUserAddress(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUserAvatar(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUserBirthday(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUserChannels(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setUserConstellation(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setUserHome(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setUserSchool(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setUserCompany(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setUsersex(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setUserSign(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setDeviceIMEI(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setUserId(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PersonInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PersonInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PersonInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
