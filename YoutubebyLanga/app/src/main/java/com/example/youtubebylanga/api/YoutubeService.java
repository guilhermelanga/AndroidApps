package com.example.youtubebylanga.api;

import com.example.youtubebylanga.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    /*
    https://www.googleapis.com/youtube/v3/
    search
    ?part=snippet
    &order=date
    &maxresults=10
    &key=AIzaSyCuZXWmhbrcHamAYtwCOghRbw_0d_9WwVE
    &channelID=UCVHFbqXqoYvEWM1Ddxl0QDg
    &q=pesquisa

     */

    //FAZ A PESQUISA NO SERVIDOR UTILIZA QUERY PARA NÃO PRECISAR MONTAR A URL ATRAVÉS DO PATH
    @GET("search")
    Call<Resultado> recuperarVideos(@Query("part") String part,
                                    @Query("order") String order,
                                    @Query("maxResults") String maxResults,
                                    @Query("key") String key,
                                    @Query("channelId") String channelId,
                                    @Query("q") String q);
}
