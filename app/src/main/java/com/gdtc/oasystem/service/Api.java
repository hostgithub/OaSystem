package com.gdtc.oasystem.service;

import com.gdtc.oasystem.bean.Detail;
import com.gdtc.oasystem.bean.NewCenter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wangjiawei on 2017-11-14.
 */

public interface Api {

    //http://192.168.0.122:8080//app/corGetById.do?id=103558   获取轮播图详情json
    @GET("app/corGetById.do")
    Call<Detail> getDetailData(@Query("id") int id);

    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007&pages=1   获取新闻中心列表json  固定ID
    @GET("app/corTabloid.do")
    Call<NewCenter> getNewCenterData(@Query("id") String id, @Query("pages") int pages);
}
