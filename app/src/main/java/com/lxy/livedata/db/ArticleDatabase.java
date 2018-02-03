package com.lxy.livedata.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lxy.livedata.ui.entity.SkilEntity;

/**
 * @author a
 * @date 2018/1/25
 */

@Database(entities = {SkilEntity.class}, version = 9, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    private static ArticleDatabase sInstance;

    public static ArticleDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), ArticleDatabase.class, "android2.db")
                    .addMigrations(MIGRATION, MIGRATION_UPDATE)
                    .build();
        }

        return sInstance;
    }

    // 升级数据库表
    static final Migration MIGRATION = new Migration(7, 8) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //添加一个字段
            database.execSQL("alter table 'android2' "
                    + " add column 'sortDate' INTEGER");
        }
    };

    /**
     * room 只支持重命名表以及添加新的字段，修改字段的步骤如下
     * <p>
     * 创建一个新的临时表，
     * <p>
     * 把users表中的数据拷贝到临时表中，
     * <p>
     * 丢弃users表
     * <p>
     * 把临时表重命名为users
     */
    static final Migration MIGRATION_UPDATE = new Migration(8, 9) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL(
                    "CREATE TABLE android2_new (userid TEXT, username TEXT, last_update INTEGER, PRIMARY KEY(userid))");
            // Copy the data
            database.execSQL(
                    "INSERT INTO android2_new (userid, username, last_update) SELECT userid, username, last_update FROM users");
            // Remove the old table
            database.execSQL("DROP TABLE android2");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE users_new RENAME TO android2_new");
        }
    };

    public static void onDestory() {
        sInstance = null;
    }

    public abstract SkillEntityDao getSkillDao();
}
