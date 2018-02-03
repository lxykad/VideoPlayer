package com.lxy.livedata.di.component;

import com.lxy.livedata.MainActivity;
import com.lxy.livedata.di.module.MainModule;
import com.lxy.livedata.di.scope.ActivityScope;
import com.lxy.livedata.repository.RemoteDataSource;

import dagger.Component;

/**
 * @author a
 * @date 2018/1/17
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);

    void injectSkilRep(RemoteDataSource dataSource);
}
