package com.gdtc.oasystem.service;

import com.gdtc.oasystem.bean.AdministrativeApproval;
import com.gdtc.oasystem.bean.AdministrativeApprovalDetail;
import com.gdtc.oasystem.bean.AllWaitDealSize;
import com.gdtc.oasystem.bean.DaipiWork;
import com.gdtc.oasystem.bean.Detail;
import com.gdtc.oasystem.bean.DetailDispatchdb;
import com.gdtc.oasystem.bean.DispatchHasDeal;
import com.gdtc.oasystem.bean.DispatchHasDealDetail;
import com.gdtc.oasystem.bean.DispatchWaitDeal;
import com.gdtc.oasystem.bean.HuiZhiBean;
import com.gdtc.oasystem.bean.IncomingHasDeal;
import com.gdtc.oasystem.bean.MeetingDetail;
import com.gdtc.oasystem.bean.MeetingHandle;
import com.gdtc.oasystem.bean.NewCenter;
import com.gdtc.oasystem.bean.ResponseBean;
import com.gdtc.oasystem.bean.ShouWenDbDetail;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    //http://192.168.0.135:8080/app_phone/getUntreatedList.do?sign=56736&deptunit10021&=page=1   发文待办
    @GET("app_phone/getUntreatedList.do")
    Call<DispatchWaitDeal> getDispatchWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=&userid=&file_source_id=&flowsort=               发文待办详情2版本
    @GET("app_phone/dispatchInfodb.do")
    Call<DetailDispatchdb> getDispatchdbDetailData(@Query("pathdata") String pathdata, @Query("deptunit") String deptunit,
                                                   @Query("sign") String sign, @Query("userid") String userid,
                                                   @Query("file_source_id") String file_source_id, @Query("flowsort") String flowsort);

    //http://192.168.0.135:8080/app_phone/getProcessedHandleList.do?sign=56736&page=1   发文已办
    @GET("app_phone/getProcessedHandleList.do")
    Call<DispatchHasDeal> getDispatchHasDealData(@Query("sign") String sign, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/dispatchInfoyb.do?file_source_id=120620170853057AM56736&deptunit=10021&type=OutfileDetailYiBan&pathdata=sgy 发文已办详情 好像有可能要加载word
    @GET("app_phone/dispatchInfoyb.do")
    Call<DispatchHasDealDetail> getDispatchHasDetailData(@Query("file_source_id") String file_source_id,@Query("deptunit") String deptunit
    ,@Query("type") String type,@Query("pathdata") String pathdata);

    //http://192.168.0.135:8080/app_phone/newGetInHandleList.do?sign=56736=&page=1   收文已办
    @GET("app_phone/newGetInHandleList.do")
    Call<IncomingHasDeal> getIncomingHasDealData(@Query("sign") String sign, @Query("page") int page);

    //http://192.168.0.135:8080/app_phone/getInfileFlowSingle.do?pathdata=sgy&file_source_id=091520170219028PM56736&deptunit=10021 收文已办详情
    @GET("app_phone/getInfileFlowSingle.do")
    Call<DispatchHasDealDetail> getIncomingHasDetailData(@Query("pathdata") String pathdata,
            @Query("file_source_id") String file_source_id,@Query("deptunit") String deptunit);


    //http://192.168.0.135:8080/app_phone/getProcessedList.do?sign=56736&deptunit=10021&page=1   收文待办
    @GET("app_phone/getProcessedList.do")
    Call<DispatchWaitDeal> getIncomingWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);


    //http://192.168.0.135:8080/app_phone/processedInfo.do?pathdata=sgy&flowsort=120420170424026PM56104&deptunit=10021&file_source_id=120420170423007PM56736&sign=56736    收文待办详情
    @GET("app_phone/processedInfo.do")
    Call<ShouWenDbDetail> getIncomingDbData(@Query("pathdata") String pathdata, @Query("flowsort") String flowsort,
                                            @Query("deptunit") String deptunit,
                                            @Query("file_source_id") String file_source_id, @Query("sign") String sign);

    //http://192.168.0.135:8080/app_phone/getMeetingHandleList.do?type=0&page=1&sign=56736&deptunit=10021   会议通知列表
    @GET("app_phone/getMeetingHandleList.do")
    Call<MeetingHandle> getMeetingHandleListData(@Query("type") String type, @Query("page") int page,@Query("sign") String sign, @Query("deptunit") String deptunit);

    //http://192.168.0.135:8080/app_phone/getMeetingHandle.do?flowsort=011220181001006AM56104   会议通知列表详情信息
    @GET("app_phone/getMeetingHandle.do")
    Call<MeetingDetail> getMeetingDetailData(@Query("flowsort") String flowsort,@Query("flowid") String flowid);

    //http://192.168.0.135:8080/app_phone/hyblRebacks.do?sign=56736&advice=111&flowsort=011620181014045AM56104   会议回执
    @GET("app_phone/hyblRebacks.do")
    Call<HuiZhiBean> getHuiZhiData(@Query("sign") String sign, @Query("advice") String advice, @Query("flowsort") String flowsort);

    //http://192.168.0.135:8080/app_phone/getAdministrationHandleList.do?page=1&sign=56736   行政审批待办
    @GET("app_phone/getAdministrationHandleList.do")
    Call<AdministrativeApproval> getAdministrativeApprovalListData(@Query("page") int page, @Query("sign") String sign);


    //http://192.168.0.135:8080/app_phone/administrationInfo.do?file_source_id=010520180932006PM56736&usersend=邢纪文&flowsort=011020180203013PM56104&typeAdvice=办理&pathdata=sgy&sort=custom&isRead=1&deptunit=10021&user_department_big=信息中心运维部&user_department=信息中心运维部   行政审批待办详情
    @GET("app_phone/administrationInfo.do")
    Call<AdministrativeApprovalDetail> getAdministrativeApprovalDetailData(@Query("file_source_id") String file_source_id, @Query("usersend") String usersend,
                                                                           @Query("userSendId") String userSendId,
                                                                           @Query("flowsort") String flowsort, @Query("typeAdvice") String typeAdvice,
                                                                           @Query("pathdata") String pathdata, @Query("sort") String sort,
                                                                           @Query("isRead") String isRead, @Query("deptunit") String deptunit,
                                                                           @Query("user_department_big") String user_department_big,
                                                                           @Query("user_department") String user_department);



    // 保留接口 http://192.168.0.135:8080/app_phone/getLeaveApplication.do?deptunit=10021   政务申请表单的数量 每个人员登陆可能申请的表单数不同 参数传 deptunit
    @GET("app_phone/getLeaveApplication.do") //json有问题
    Call<AdministrativeApproval> getLeaveApplicationListData( @Query("deptunit") String deptunit);


    //    http://192.168.0.135:8080/app_phone/getListCount.do?sign=56736
    @GET("app_phone/getListCount.do") //获取所有待办列表数
    Call<AllWaitDealSize> getAllWaitDealNumberData(@Query("sign") String sign);


    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("app/receiveData.do")
    Call<ResponseBean> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody

    ///app_phone/writeBack.do?user_department=&user_department_big=&sign=&deptunit=&title=  &jijian=  &userid=  &file_source_id=&flowsort=&advice=  &ip=  &type_advice_sa=  &yffs=  &column1= &column2=  &column3=  &column6=  &column75=  &column76=  &column77=  &column78=  &column79=

    /** 表单提交要加 @FormUrlEncoded
     * 登录
     * @param user_department 用户名
     * @param user_department_big 密码
     * @param sign 设置ID
     * @param deptunit 平台这里是Android
     * @param title 版本号
     * @param jijian 版本号
     * @param userid 版本号
     * @param file_source_id 版本号
     * @param flowsort 版本号
     * @param advice 版本号
     * @param ip 版本号
     * @param type_advice_sa 版本号
     * @param yffs 版本号
     * @param column1 版本号
     * @return
     */
    @FormUrlEncoded
    @POST("app_phone/writeBack.do")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Call<HuiZhiBean> dispatchBack(@Field("user_department") String user_department,
                       @Field("user_department_big") String user_department_big,
                       @Field("sign") String sign,
                       @Field("deptunit") String deptunit,
                       @Field("title") String title,
                       @Field("jijian") String jijian,
                       @Field("userid") String userid,
                       @Field("file_source_id") String file_source_id,
                       @Field("flowsort") String flowsort,
                       @Field("advice") String advice,
                       @Field("ip") String ip,
                       @Field("type_advice_sa") String type_advice_sa,
                       @Field("yffs") String yffs,
                       @Field("column1") String column1,
                       @Field("column2") String column2,
                       @Field("column3") String column3,
                       @Field("column6") String column6,
                       @Field("column75") String column75,
                       @Field("column76") String column76,
                       @Field("column77") String column77,
                       @Field("column78") String column78,
                       @Field("column79") String column79);


    /** 表单提交要加 @FormUrlEncoded
     * @return
     */
    @FormUrlEncoded
    @POST("app_phone/whenReceipt.do")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Call<HuiZhiBean> IncomingBack(@Field("sign") String sign,
                                  @Field("deptunit") String deptunit,
                                  @Field("userid") String userid,
                                  @Field("file_source_id") String file_source_id,
                                  @Field("flowsort") String flowsort,
                                  @Field("type_advice") String type_advice,
                                  @Field("userQc") String userQc,
                                  @Field("fanhui_man_sw") String fanhui_man_sw,
                                  @Field("advicetype") String advicetype,
                                  @Field("advice") String advice,
                                  @Field("column75") String column75,
                                  @Field("column76") String column76,
                                  @Field("column77") String column77,
                                  @Field("column78") String column78,
                                  @Field("column79") String column79);

    @POST("upload")
    @Multipart
    Call<ResponseBody> upload(@Part("description") RequestBody description, @Part("") MultipartBody.Part file);


    //http://192.168.0.135:8080/app_phone/getMessageInfoListDaiban.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏
    @GET("app_phone/getMessageInfoListDaiban.do")                                         //待批工作
    Call<DaipiWork> getDaipiWorkList(@Query("pathdata") String pathdata, @Query("deptunit") String deptunit,
                                      @Query("sign") String sign, @Query("userid") String userid);

    //http://192.168.0.135:8080/app_phone/getMessageInfoList.do?sign=56736&deptunit=10021&pathdata=sgy
    @GET("app_phone/getMessageInfoList.do")                                                  //已批工作
    Call<DaipiWork> getYipiWorkList(@Query("pathdata") String pathdata, @Query("deptunit") String deptunit,
                                     @Query("sign") String sign);

    @GET
    Call<AdministrativeApprovalDetail> getAdministrativeApprovalDetail(@Url String url);//动态拼接地址 行政审批

    @GET
    Call<MeetingDetail> getMeetingDetail(@Url String url);//动态拼接地址  会议通知


    @GET
    Call<DetailDispatchdb> getDetailDispatchdb(@Url String url);//动态拼接地址  发文待批

    @GET
    Call<DispatchHasDealDetail> getDispatchHasDealDetail(@Url String url);//动态拼接地址  发文已批

    @GET
    Call<ShouWenDbDetail> getShouWenDbDetail(@Url String url);//动态拼接地址  收文待批

    @GET
    Call<DispatchHasDealDetail> getShouWenYBDetail(@Url String url);//动态拼接地址  收文已批



    /** 表单提交要加 @FormUrlEncoded
     * @return
     */
    @FormUrlEncoded
    @POST("app_phone/cunstomReceipt.do")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加header
    Call<HuiZhiBean> AdministrativeBack(@Field("xjtype") String xjtype,
                                        @Field("xiaojiatype") String xiaojiatype,
                                        @Field("user_department") String user_department,
                                  @Field("user_department_big") String user_department_big,
                                  @Field("sign") String sign,
                                  @Field("deptunit") String deptunit,
                                  @Field("userid") String userid,
                                  @Field("user_send") String user_send,
                                  @Field("user_send_id") String user_send_id,
                                  @Field("file_source_id") String file_source_id,
                                  @Field("flowsort") String flowsort,
                                  @Field("model_sf") String model_sf,
                                  @Field("sort") String sort,
                                  @Field("type") String type,
                                  @Field("column1") String column1,
                                  @Field("column2") String column2,
                                  @Field("column3") String column3,
                                  @Field("column4") String column4,
                                  @Field("column5") String column5,
                                  @Field("column6") String column6,
                                  @Field("column7") String column7,
                                  @Field("column8") String column8,
                                  @Field("column9") String column9,
                                  @Field("column10") String column10,
                                  @Field("column11") String column11,
                                  @Field("column12") String column12,
                                  @Field("column13") String column13,
                                  @Field("column14") String column14,
                                  @Field("column15") String column15,
                                  @Field("column16") String column16,
                                  @Field("column17") String column17,
                                  @Field("column18") String column18,
                                  @Field("column19") String column19,
                                  @Field("column20") String column20,
                                  @Field("column21") String column21,
                                  @Field("column22") String column22,
                                  @Field("column23") String column23,
                                  @Field("column24") String column24,
                                  @Field("column25") String column25,
                                  @Field("column26") String column26,
                                  @Field("column27") String column27,
                                  @Field("column28") String column28,
                                  @Field("column29") String column29,
                                  @Field("column30") String column30,
                                  @Field("column31") String column31,
                                  @Field("column32") String column32,
                                  @Field("column33") String column33,
                                  @Field("column34") String column34,
                                  @Field("column35") String column35,
                                  @Field("column36") String column36,
                                  @Field("column37") String column37,
                                  @Field("column38") String column38,
                                  @Field("column39") String column39,
                                  @Field("column40") String column40,
                                  @Field("column41") String column41,
                                  @Field("column42") String column42,
                                  @Field("column43") String column43,
                                  @Field("column44") String column44,
                                  @Field("column45") String column45,
                                  @Field("column46") String column46,
                                  @Field("column47") String column47,
                                  @Field("column48") String column48,
                                  @Field("column49") String column49,
                                  @Field("column50") String column50);

    /** 表单提交要加 @FormUrlEncoded
     * @return
     */
    @FormUrlEncoded
    @POST("app_phone/cunstomReceipt.do")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加header
    Call<HuiZhiBean> AdministrativeReBack(@Field("xjtype") String xjtype,
                                        @Field("user_department") String user_department,
                                        @Field("user_department_big") String user_department_big,
                                        @Field("sign") String sign,
                                        @Field("userid") String userid,
                                        @Field("user_send") String user_send,
                                        @Field("user_send_id") String user_send_id,
                                        @Field("file_source_id") String file_source_id,
                                        @Field("flowsort") String flowsort,
                                        @Field("is_TY") String is_TY,
                                        @Field("type") String type,@Field("advice") String advice,@Field("advice1") String advice1,
                                          @Field("column22") String column22,
                                          @Field("column23") String column23,
                                          @Field("column24") String column24,
                                          @Field("column25") String column25,
                                          @Field("column26") String column26,
                                          @Field("column27") String column27,
                                          @Field("column28") String column28,
                                          @Field("column29") String column29,
                                          @Field("column30") String column30,
                                          @Field("column31") String column31,
                                          @Field("column32") String column32,
                                          @Field("column33") String column33);
}
