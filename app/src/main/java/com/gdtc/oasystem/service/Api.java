package com.gdtc.oasystem.service;

import com.gdtc.oasystem.bean.AdministrativeApproval;
import com.gdtc.oasystem.bean.Detail;
import com.gdtc.oasystem.bean.DispatchHasDeal;
import com.gdtc.oasystem.bean.DispatchHasDealDetail;
import com.gdtc.oasystem.bean.DispatchWaitDeal;
import com.gdtc.oasystem.bean.IncomingHasDeal;
import com.gdtc.oasystem.bean.IncomingHasDealDetail;
import com.gdtc.oasystem.bean.MeetingDetail;
import com.gdtc.oasystem.bean.MeetingHandle;
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

    //http://192.168.0.135:8080/app_phone/appLogin.do?userid=wangtianpeng&user_password=123&dept_properties=topoffice       login返回 personnelId=56736 depetUnit=10021
    @GET("app_phone/appLogin.do")
    Call<ResponseBean> getLoginData(@Query("userid") String userid, @Query("user_password") String user_password,@Query("dept_properties") String dept_properties);

    //http://192.168.0.135:8080/app_phone/getUntreatedList.do?page=1   发文待办
    @GET("app_phone/getUntreatedList.do")
    Call<DispatchWaitDeal> getDispatchWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/getProcessedHandleList.do?sign=10021&page=1   发文已办
    @GET("app_phone/getProcessedHandleList.do")
    Call<DispatchHasDeal> getDispatchHasDealData(@Query("sign") String sign, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/dispatchInfoyb.do?flowsort=120520170817026PM56104&file_source_id=120520170817026PM56104 发文已办详情 好像有可能要加载pdf
    @GET("app_phone/dispatchInfoyb.do")
    Call<DispatchHasDealDetail> getDispatchHasDetailData(@Query("flowsort") String flowsort, @Query("file_source_id") String file_source_id);

    //http://192.168.0.135:8080/app_phone/getInHandleList.do?sign=&page=1   收文已办
    @GET("app_phone/getInHandleList.do")
    Call<IncomingHasDeal> getIncomingHasDealData(@Query("sign") String sign, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/getInfileFlowSingle.do?file_source_id=091520170219028PM56736 收文已办详情
    @GET("app_phone/getInfileFlowSingle.do")
    Call<IncomingHasDealDetail> getIncomingHasDetailData(@Query("file_source_id") String file_source_id);


    //http://192.168.0.135:8080/app_phone/getProcessedList.do?page=1   收文待办
    @GET("app_phone/getProcessedList.do")
    Call<DispatchWaitDeal> getIncomingWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/getMeetingHandleList.do?type=0&page=1&sign=56736&deptunit=10021   会议通知列表
    @GET("app_phone/getMeetingHandleList.do")
    Call<MeetingHandle> getMeetingHandleListData(@Query("type") String type, @Query("page") int page,@Query("sign") String sign, @Query("deptunit") String deptunit);

    //http://192.168.0.135:8080/app_phone/getMeetingHandle.do?flowsort=011220181001006AM56104   会议通知列表详情信息
    @GET("app_phone/getMeetingHandle.do")
    Call<MeetingDetail> getMeetingDetailData(@Query("flowsort") String flowsort);

    //http://192.168.0.135:8080/app_phone/getAdministrationHandleList.do?page=1&sign=56736   行政审批待办
    @GET("app_phone/getAdministrationHandleList.do")
    Call<AdministrativeApproval> getAdministrativeApprovalListData(@Query("page") int page, @Query("sign") String sign);

    // 保留接口 http://192.168.0.135:8080/app_phone/getLeaveApplication.do?deptunit=10021   政务申请表单的数量 每个人员登陆可能申请的表单数不同 参数传 deptunit
    @GET("app_phone/getLeaveApplication.do") //json有问题
    Call<AdministrativeApproval> getLeaveApplicationListData( @Query("deptunit") String deptunit);
}
