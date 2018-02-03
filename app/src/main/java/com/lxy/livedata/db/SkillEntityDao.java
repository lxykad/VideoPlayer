package com.lxy.livedata.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

/**
 * @author a
 * @date 2018/1/8
 */

@Dao
public interface SkillEntityDao {

    @Query("select*from android2")
    LiveData<List<SkilEntity>> getSkillList();

    @Query("select*from android2 order by sortDate desc limit 15 offset :startCount")
    LiveData<List<SkilEntity>> getPageList(int startCount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEntity(SkilEntity entity);

    @Insert
    void addEntityList(List<SkilEntity> list);

    @Delete()
    void delEntity(SkilEntity entity);

    @Delete()
    void deleteAllEntity(List<SkilEntity> list);

    @Update
    void updateEntity(SkilEntity entity);
}
