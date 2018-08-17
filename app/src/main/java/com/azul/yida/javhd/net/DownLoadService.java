package com.azul.yida.javhd.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by xuyimin on 2018/8/17.
 * E-mail codingyida@qq.com
 */

public interface DownLoadService {

    @Streaming
    @GET
        public Observable<ResponseBody> downloadFilm(@Url String url);

}
