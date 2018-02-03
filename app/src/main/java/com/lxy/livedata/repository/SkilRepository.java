package com.lxy.livedata.repository;

import android.arch.lifecycle.MediatorLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.lxy.livedata.Resource;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

/**
 * @author a
 * @date 2018/1/5
 * Repository模块负责处理数据操作
 */

public class SkilRepository {

    private DataSourceContext mDataSourceContext;

    /**
     * 获取数据
     */
    public MediatorLiveData<Resource<List<SkilEntity>>> getDataList(String type, int count, int page) {

        if (NetworkUtils.isConnected()) {
            mDataSourceContext = new DataSourceContext(RemoteDataSource.getInstance());
        } else {
            mDataSourceContext = new DataSourceContext(LocalDataSource.getInstance());
        }

        return mDataSourceContext.getData(type, count, page);
    }
}
