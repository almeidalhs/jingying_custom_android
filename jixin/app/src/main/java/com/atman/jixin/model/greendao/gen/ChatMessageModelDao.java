package com.atman.jixin.model.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.atman.jixin.model.bean.ChatMessageModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHAT_MESSAGE_MODEL".
*/
public class ChatMessageModelDao extends AbstractDao<ChatMessageModel, Long> {

    public static final String TABLENAME = "CHAT_MESSAGE_MODEL";

    /**
     * Properties of entity ChatMessageModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ChatId = new Property(1, long.class, "chatId", false, "CHAT_ID");
        public final static Property LoginId = new Property(2, long.class, "loginId", false, "LOGIN_ID");
        public final static Property Type = new Property(3, int.class, "type", false, "TYPE");
        public final static Property TargetType = new Property(4, int.class, "targetType", false, "TARGET_TYPE");
        public final static Property TargetId = new Property(5, long.class, "targetId", false, "TARGET_ID");
        public final static Property TargetName = new Property(6, String.class, "targetName", false, "TARGET_NAME");
        public final static Property TargetAvatar = new Property(7, String.class, "targetAvatar", false, "TARGET_AVATAR");
        public final static Property SendTime = new Property(8, long.class, "sendTime", false, "SEND_TIME");
        public final static Property Content = new Property(9, String.class, "content", false, "CONTENT");
        public final static Property Audio_duration = new Property(10, int.class, "audio_duration", false, "AUDIO_DURATION");
        public final static Property AudioLocationUrl = new Property(11, String.class, "audioLocationUrl", false, "AUDIO_LOCATION_URL");
        public final static Property Video_image_url = new Property(12, String.class, "video_image_url", false, "VIDEO_IMAGE_URL");
        public final static Property ImageT_icon = new Property(13, String.class, "imageT_icon", false, "IMAGE_T_ICON");
        public final static Property ImageT_title = new Property(14, String.class, "imageT_title", false, "IMAGE_T_TITLE");
        public final static Property ImageT_back = new Property(15, String.class, "imageT_back", false, "IMAGE_T_BACK");
        public final static Property IdentifyStr = new Property(16, String.class, "identifyStr", false, "IDENTIFY_STR");
        public final static Property OperaterId = new Property(17, long.class, "operaterId", false, "OPERATER_ID");
        public final static Property OperaterName = new Property(18, String.class, "operaterName", false, "OPERATER_NAME");
        public final static Property OperaterType = new Property(19, int.class, "operaterType", false, "OPERATER_TYPE");
        public final static Property ActionType = new Property(20, int.class, "actionType", false, "ACTION_TYPE");
        public final static Property CouponId = new Property(21, long.class, "couponId", false, "COUPON_ID");
        public final static Property EnterpriseId = new Property(22, int.class, "enterpriseId", false, "ENTERPRISE_ID");
        public final static Property GoodId = new Property(23, long.class, "goodId", false, "GOOD_ID");
        public final static Property StoreId = new Property(24, long.class, "storeId", false, "STORE_ID");
        public final static Property SelfSend = new Property(25, boolean.class, "selfSend", false, "SELF_SEND");
        public final static Property Readed = new Property(26, int.class, "readed", false, "READED");
        public final static Property SendStatus = new Property(27, int.class, "sendStatus", false, "SEND_STATUS");
    }


    public ChatMessageModelDao(DaoConfig config) {
        super(config);
    }
    
    public ChatMessageModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHAT_MESSAGE_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CHAT_ID\" INTEGER NOT NULL ," + // 1: chatId
                "\"LOGIN_ID\" INTEGER NOT NULL ," + // 2: loginId
                "\"TYPE\" INTEGER NOT NULL ," + // 3: type
                "\"TARGET_TYPE\" INTEGER NOT NULL ," + // 4: targetType
                "\"TARGET_ID\" INTEGER NOT NULL ," + // 5: targetId
                "\"TARGET_NAME\" TEXT," + // 6: targetName
                "\"TARGET_AVATAR\" TEXT," + // 7: targetAvatar
                "\"SEND_TIME\" INTEGER NOT NULL ," + // 8: sendTime
                "\"CONTENT\" TEXT," + // 9: content
                "\"AUDIO_DURATION\" INTEGER NOT NULL ," + // 10: audio_duration
                "\"AUDIO_LOCATION_URL\" TEXT," + // 11: audioLocationUrl
                "\"VIDEO_IMAGE_URL\" TEXT," + // 12: video_image_url
                "\"IMAGE_T_ICON\" TEXT," + // 13: imageT_icon
                "\"IMAGE_T_TITLE\" TEXT," + // 14: imageT_title
                "\"IMAGE_T_BACK\" TEXT," + // 15: imageT_back
                "\"IDENTIFY_STR\" TEXT," + // 16: identifyStr
                "\"OPERATER_ID\" INTEGER NOT NULL ," + // 17: operaterId
                "\"OPERATER_NAME\" TEXT," + // 18: operaterName
                "\"OPERATER_TYPE\" INTEGER NOT NULL ," + // 19: operaterType
                "\"ACTION_TYPE\" INTEGER NOT NULL ," + // 20: actionType
                "\"COUPON_ID\" INTEGER NOT NULL ," + // 21: couponId
                "\"ENTERPRISE_ID\" INTEGER NOT NULL ," + // 22: enterpriseId
                "\"GOOD_ID\" INTEGER NOT NULL ," + // 23: goodId
                "\"STORE_ID\" INTEGER NOT NULL ," + // 24: storeId
                "\"SELF_SEND\" INTEGER NOT NULL ," + // 25: selfSend
                "\"READED\" INTEGER NOT NULL ," + // 26: readed
                "\"SEND_STATUS\" INTEGER NOT NULL );"); // 27: sendStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHAT_MESSAGE_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChatMessageModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getChatId());
        stmt.bindLong(3, entity.getLoginId());
        stmt.bindLong(4, entity.getType());
        stmt.bindLong(5, entity.getTargetType());
        stmt.bindLong(6, entity.getTargetId());
 
        String targetName = entity.getTargetName();
        if (targetName != null) {
            stmt.bindString(7, targetName);
        }
 
        String targetAvatar = entity.getTargetAvatar();
        if (targetAvatar != null) {
            stmt.bindString(8, targetAvatar);
        }
        stmt.bindLong(9, entity.getSendTime());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(10, content);
        }
        stmt.bindLong(11, entity.getAudio_duration());
 
        String audioLocationUrl = entity.getAudioLocationUrl();
        if (audioLocationUrl != null) {
            stmt.bindString(12, audioLocationUrl);
        }
 
        String video_image_url = entity.getVideo_image_url();
        if (video_image_url != null) {
            stmt.bindString(13, video_image_url);
        }
 
        String imageT_icon = entity.getImageT_icon();
        if (imageT_icon != null) {
            stmt.bindString(14, imageT_icon);
        }
 
        String imageT_title = entity.getImageT_title();
        if (imageT_title != null) {
            stmt.bindString(15, imageT_title);
        }
 
        String imageT_back = entity.getImageT_back();
        if (imageT_back != null) {
            stmt.bindString(16, imageT_back);
        }
 
        String identifyStr = entity.getIdentifyStr();
        if (identifyStr != null) {
            stmt.bindString(17, identifyStr);
        }
        stmt.bindLong(18, entity.getOperaterId());
 
        String operaterName = entity.getOperaterName();
        if (operaterName != null) {
            stmt.bindString(19, operaterName);
        }
        stmt.bindLong(20, entity.getOperaterType());
        stmt.bindLong(21, entity.getActionType());
        stmt.bindLong(22, entity.getCouponId());
        stmt.bindLong(23, entity.getEnterpriseId());
        stmt.bindLong(24, entity.getGoodId());
        stmt.bindLong(25, entity.getStoreId());
        stmt.bindLong(26, entity.getSelfSend() ? 1L: 0L);
        stmt.bindLong(27, entity.getReaded());
        stmt.bindLong(28, entity.getSendStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChatMessageModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getChatId());
        stmt.bindLong(3, entity.getLoginId());
        stmt.bindLong(4, entity.getType());
        stmt.bindLong(5, entity.getTargetType());
        stmt.bindLong(6, entity.getTargetId());
 
        String targetName = entity.getTargetName();
        if (targetName != null) {
            stmt.bindString(7, targetName);
        }
 
        String targetAvatar = entity.getTargetAvatar();
        if (targetAvatar != null) {
            stmt.bindString(8, targetAvatar);
        }
        stmt.bindLong(9, entity.getSendTime());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(10, content);
        }
        stmt.bindLong(11, entity.getAudio_duration());
 
        String audioLocationUrl = entity.getAudioLocationUrl();
        if (audioLocationUrl != null) {
            stmt.bindString(12, audioLocationUrl);
        }
 
        String video_image_url = entity.getVideo_image_url();
        if (video_image_url != null) {
            stmt.bindString(13, video_image_url);
        }
 
        String imageT_icon = entity.getImageT_icon();
        if (imageT_icon != null) {
            stmt.bindString(14, imageT_icon);
        }
 
        String imageT_title = entity.getImageT_title();
        if (imageT_title != null) {
            stmt.bindString(15, imageT_title);
        }
 
        String imageT_back = entity.getImageT_back();
        if (imageT_back != null) {
            stmt.bindString(16, imageT_back);
        }
 
        String identifyStr = entity.getIdentifyStr();
        if (identifyStr != null) {
            stmt.bindString(17, identifyStr);
        }
        stmt.bindLong(18, entity.getOperaterId());
 
        String operaterName = entity.getOperaterName();
        if (operaterName != null) {
            stmt.bindString(19, operaterName);
        }
        stmt.bindLong(20, entity.getOperaterType());
        stmt.bindLong(21, entity.getActionType());
        stmt.bindLong(22, entity.getCouponId());
        stmt.bindLong(23, entity.getEnterpriseId());
        stmt.bindLong(24, entity.getGoodId());
        stmt.bindLong(25, entity.getStoreId());
        stmt.bindLong(26, entity.getSelfSend() ? 1L: 0L);
        stmt.bindLong(27, entity.getReaded());
        stmt.bindLong(28, entity.getSendStatus());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ChatMessageModel readEntity(Cursor cursor, int offset) {
        ChatMessageModel entity = new ChatMessageModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // chatId
            cursor.getLong(offset + 2), // loginId
            cursor.getInt(offset + 3), // type
            cursor.getInt(offset + 4), // targetType
            cursor.getLong(offset + 5), // targetId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // targetName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // targetAvatar
            cursor.getLong(offset + 8), // sendTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // content
            cursor.getInt(offset + 10), // audio_duration
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // audioLocationUrl
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // video_image_url
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // imageT_icon
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // imageT_title
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // imageT_back
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // identifyStr
            cursor.getLong(offset + 17), // operaterId
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // operaterName
            cursor.getInt(offset + 19), // operaterType
            cursor.getInt(offset + 20), // actionType
            cursor.getLong(offset + 21), // couponId
            cursor.getInt(offset + 22), // enterpriseId
            cursor.getLong(offset + 23), // goodId
            cursor.getLong(offset + 24), // storeId
            cursor.getShort(offset + 25) != 0, // selfSend
            cursor.getInt(offset + 26), // readed
            cursor.getInt(offset + 27) // sendStatus
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChatMessageModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setChatId(cursor.getLong(offset + 1));
        entity.setLoginId(cursor.getLong(offset + 2));
        entity.setType(cursor.getInt(offset + 3));
        entity.setTargetType(cursor.getInt(offset + 4));
        entity.setTargetId(cursor.getLong(offset + 5));
        entity.setTargetName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTargetAvatar(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSendTime(cursor.getLong(offset + 8));
        entity.setContent(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setAudio_duration(cursor.getInt(offset + 10));
        entity.setAudioLocationUrl(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setVideo_image_url(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setImageT_icon(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setImageT_title(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setImageT_back(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setIdentifyStr(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setOperaterId(cursor.getLong(offset + 17));
        entity.setOperaterName(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setOperaterType(cursor.getInt(offset + 19));
        entity.setActionType(cursor.getInt(offset + 20));
        entity.setCouponId(cursor.getLong(offset + 21));
        entity.setEnterpriseId(cursor.getInt(offset + 22));
        entity.setGoodId(cursor.getLong(offset + 23));
        entity.setStoreId(cursor.getLong(offset + 24));
        entity.setSelfSend(cursor.getShort(offset + 25) != 0);
        entity.setReaded(cursor.getInt(offset + 26));
        entity.setSendStatus(cursor.getInt(offset + 27));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ChatMessageModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ChatMessageModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChatMessageModel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
