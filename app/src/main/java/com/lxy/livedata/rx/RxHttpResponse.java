package com.lxy.livedata.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author a
 * @date 2018/1/29
 * http 请求结果统一预处理
 */

public class RxHttpResponse {

    public static <T> ObservableTransformer<BaseBean<T>, T> handResult() {

        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {

                return upstream.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseBean<T> tBaseBean) throws Exception {

                        if (tBaseBean.error) {
                            // 请求失败
                            System.out.println("list=====请求失败");
                            return Observable.error(new Exception());
                        } else {
                            // 请求成功
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> e) throws Exception {
                                    System.out.println("list=====请求成功==");
                                    e.onNext(tBaseBean.results);
                                    e.onComplete();
                                }
                            });
                        }

                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());// 数据库操作放在子线程，所以这里在io线程执行
            }
        };
    }
}
