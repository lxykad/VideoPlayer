package com.lxy.livedata.repository;

import android.arch.lifecycle.MediatorLiveData;

import com.lxy.livedata.Resource;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

/**
 *
 * @author a
 * @date 2018/2/2
 */

public interface SkilDataSource {

    MediatorLiveData<Resource<List<SkilEntity>>> getData(String type, int count, int page);
}
