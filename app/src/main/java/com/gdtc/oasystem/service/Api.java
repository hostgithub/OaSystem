package com.gdtc.oasystem.service;

import com.gdtc.oasystem.bean.Detail;
import com.gdtc.oasystem.bean.DispatchWaitDeal;
import com.gdtc.oasystem.bean.NewCenter;
import com.gdtc.oasystem.bean.ResponseBean;

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

    //http://192.168.0.135:8080/app_phone/appLogin.do?userid=wangtianpeng&user_password=123&dept_properties=topoffice       login
    @GET("app_phone/appLogin.do")
    Call<ResponseBean> getLoginData(@Query("userid") String userid, @Query("user_password") String user_password,@Query("dept_properties") String dept_properties);

    //http://192.168.0.135:8080/app_phone/getUntreatedList.do?page=1   发文待办
    @GET("app_phone/getUntreatedList.do")
    Call<DispatchWaitDeal> getDispatchWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/getProcessedHandleList.do?page=1   发文已办
    @GET("app_phone/getProcessedHandleList.do")
    Call<DispatchWaitDeal> getDispatchHasDealData(@Query("sign") String sign, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/getProcessedList.do?page=1   收文待办
    @GET("app_phone/getProcessedList.do")
    Call<DispatchWaitDeal> getIncomingWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);


}
