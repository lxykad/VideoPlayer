package com.lxy.livedata.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 *
 * @author a
 * @date 2018/1/19
 *
 *  自定义scope实现全局单例
 *  singleTon 只在某一个类里是单例
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {


}
