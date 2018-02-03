package com.lxy.livedata.base;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.common.HttpHelper;
import com.lxy.livedata.di.component.AppComponent;
import com.lxy.livedata.di.component.DaggerAppComponent;
import com.lxy.livedata.di.module.AppModule;
import com.lxy.livedata.di.module.HttpModule;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author a
 * @date 2018/1/5
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static ApiService mApiService;

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build();
        init();
        Utils.init(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static ApiService getApiService() {
        if (mApiService == null) {

            mApiService = new Retrofit.Builder()
                    .client(getOkhttpClient().build())
                    .baseUrl(HttpHelper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(ApiService.class);
        }

        return mApiService;
    }

    public static OkHttpClient.Builder getOkhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("http_response========", message);
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return builder;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    private void init(){
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

}
