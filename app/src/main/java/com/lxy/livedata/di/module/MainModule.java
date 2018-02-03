package com.lxy.livedata.di.module;

import com.lxy.livedata.di.Qualifier.MainFier;
import com.lxy.livedata.di.User;
import com.lxy.livedata.di.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 * @author a
 * @date 2018/1/17
 *  module 的优先级高于 构造函数inject的优先级
 */

@Module
public class MainModule {


    @MainFier("no_params")
    @Provides
    @ActivityScope
    public User provideUser(){
        return new User();
    }

    /*
    * dagger2中  参数也需要注入
    * */

    @MainFier("params")
    @Provides
    @ActivityScope
    public User provideUserParams(String name){
        return new User(name);
    }

    @Provides
    @ActivityScope
    public String provideName(){
        return "带参构造";
    }

}
