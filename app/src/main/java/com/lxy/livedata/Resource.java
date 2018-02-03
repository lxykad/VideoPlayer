package com.lxy.livedata;

import javax.annotation.Nullable;

import static com.lxy.livedata.NetworkState.FAILED;
import static com.lxy.livedata.NetworkState.LOADING;
import static com.lxy.livedata.NetworkState.SUCCESS;

/**
 * @author a
 * @date 2018/1/23
 */

public class Resource<T> {

    @Nullable
    public final NetworkState status;
    @Nullable
    public final T data;

    public final Throwable error;

    private Resource(@Nullable NetworkState status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Throwable error) {
        return new Resource<>(FAILED,null, error);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null);
    }
}
