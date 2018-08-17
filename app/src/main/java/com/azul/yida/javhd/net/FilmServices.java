package com.azul.yida.javhd.net;

import com.azul.yida.javhd.model.javModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface FilmServices {
    //https://c211f409.ngrok.io/ film/page/10/
    public static final String BASE_URL="https://c211f409.ngrok.io/";

    @GET("film/page/{page}")
    Observable<List<javModel>> getData(@Path("page") int page);
}
