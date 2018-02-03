package com.lxy.livedata.di.component;

import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.di.module.AppModule;
import com.lxy.livedata.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author a
 * @date 2018/1/17
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    BaseApplication getApplication();
    ApiService getApiService();
}
