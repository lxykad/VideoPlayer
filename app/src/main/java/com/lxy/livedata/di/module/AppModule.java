package com.lxy.livedata.di.module;

import com.lxy.livedata.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 * @author a
 * @date 2018/1/17
 */

@Singleton
@Module
public class AppModule {

    private BaseApplication mApplication;

    public AppModule(BaseApplication application){
        mApplication = application;
        System.out.println("1111===app====appmodule");

    }

    @Provides
    public BaseApplication provideApplication(){
        return mApplication;
    }

}
