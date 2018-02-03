package com.lxy.livedata.di.Qualifier;

import javax.inject.Qualifier;

/**
 * @author a
 * @date 2018/1/19
 */

@Qualifier
public @interface MainFier {
    String value() default "default";
}
