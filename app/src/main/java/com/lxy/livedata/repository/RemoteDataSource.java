package com.lxy.livedata.repository;

import android.arch.lifecycle.MediatorLiveData;

import com.blankj.utilcode.util.TimeUtils;
import com.lxy.livedata.Resource;
import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.db.ArticleDatabase;
import com.lxy.livedata.db.SkillEntityDao;
import com.lxy.livedata.di.component.DaggerMainComponent;
import com.lxy.livedata.rx.RxHttpResponse;
import com.lxy.livedata.ui.entity.SkilEntity;
import com.lxy.livedata.utils.DateUtil;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author a
 * @date 2018/2/2
 */

public class RemoteDataSource implements SkilDataSource {

    @Inject
    ApiService mApiService;

    private static final RemoteDataSource instance = new RemoteDataSource();

    private RemoteDataSource() {

    }

    public static RemoteDataSource getInstance() {

        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .injectSkilRep(instance);
        return instance;
    }

    /**
     * 查询网络数据
     *
     * @param type
     * @param count
     * @param page
     * @return
     */

    @Override
    public MediatorLiveData<Resource<List<SkilEntity>>> getData(String type, int count, int page) {

        SkillEntityDao skillDao = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                .getSkillDao();

        MediatorLiveData<Resource<List<SkilEntity>>> liveData = new MediatorLiveData<>();

        mApiService.loadList(type, count, page)
                .compose(RxHttpResponse.handResult())
                .subscribe(new Observer<List<SkilEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        liveData.setValue(Resource.loading());
                    }

                    @Override
                    public void onNext(List<SkilEntity> list) {
                        liveData.postValue(Resource.success(list));

                        for (SkilEntity entity : list) {

                            Date date = DateUtil.parseDate(entity.publishedAt);
                            long l = TimeUtils.date2Millis(date);
                            entity.sortDate = l;
                            skillDao.addEntity(entity);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(Resource.error(e));
                        System.out.println("db=====err==");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return liveData;
    }

}
