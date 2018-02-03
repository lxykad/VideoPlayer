package com.lxy.livedata.api;

import com.lxy.livedata.SkilBean;
import com.lxy.livedata.rx.BaseBean;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author a
 * @date 2018/1/5
 */

public interface ApiService {

    @GET("{type}/{count}/{page}")
    Call<SkilBean> loadSkilData(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    @GET("{type}/{count}/{page}")
    Observable<SkilBean> loadData(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    /**
     * @param type
     * @param count
     * @param page
     * @return
     */
    @GET("{type}/{count}/{page}")
    Observable<BaseBean<List<SkilEntity>>> loadList(@Path("type") String type, @Path("count") int count, @Path("page") int page);
}
