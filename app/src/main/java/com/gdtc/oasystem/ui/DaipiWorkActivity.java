package com.gdtc.oasystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.adapter.DaipiWorkListAdapter;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.AdministrativeApprovalDetail;
import com.gdtc.oasystem.bean.DaipiWork;
import com.gdtc.oasystem.bean.DetailDispatchdb;
import com.gdtc.oasystem.bean.MeetingDetail;
import com.gdtc.oasystem.bean.ShouWenDbDetail;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.utils.RecyclerViewSpacesItemDecoration;
import com.gdtc.oasystem.utils.SharePreferenceTools;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaipiWorkActivity extends BaseActivity{

    @BindView(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.title_center)
    TextView title_center;

    private ArrayList<DaipiWork.ResultsBean> list;
    private DaipiWorkListAdapter daipiWorkListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;
    private SharePreferenceTools sp;

    @Override
    protected int getLayoutId() {
        return R.layout.xrecyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        title_center.setText("待批工作");

        sp = new SharePreferenceTools(MyApplication.getContext());
        list=new ArrayList();
        startProgressDialog();
        initData();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,0);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,0);//底部间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,10);//左间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,10);//右间距
        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        daipiWorkListAdapter=new DaipiWorkListAdapter(list,this);
        //条目点击事件
        daipiWorkListAdapter.setOnItemClickLitener(new DaipiWorkListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                getData(position);
                //showToast("点击位置"+position);
            }
        });
        mRecyclerView.setAdapter(daipiWorkListAdapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                pages = 1;
//                list.clear();
//                initData(1);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
//                pages++;
//                initData(pages);
                mRecyclerView.loadMoreComplete();
            }
        });
//        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
//            //EndLessOnScrollListener 是自定义的监听器
//            @Override
//            public void onLoadMore() {
//                if(sp.getInt("apagesize")<15){
//                    refreshLayout.setRefreshing(false);
//                    administrativeApprovalAdapter.setFooterVisible(View.GONE);
//                    Toast.makeText(AdministrativeApprovalActivity.this,"已经是最后一条数据了",Toast.LENGTH_SHORT).show();
//                }else{
//                    pages++;
//                    administrativeApprovalAdapter.setFooterVisible(View.VISIBLE);
//                    initData(pages);
//                }
//            }
//            @Override
//            public void hide() {
//            }
//            @Override
//            public void show() {
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public void onRefresh() {
//        administrativeApprovalAdapter.setFooterVisible(View.GONE);
//        pages = 1;
//        list.clear();
//        initData(1);
//    }

    private void initData() {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<DaipiWork> call=api.getDaipiWorkList(sp.getString(Config.PATHDATA),sp.getString(Config.DEPTUNIT),
                sp.getString(Config.USER_ID),sp.getString(Config.USERNAME));
        Log.e("---------->>>",sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.DEPTUNIT));
        call.enqueue(new Callback<DaipiWork>() {
            @Override
            public void onResponse(Call<DaipiWork> call, Response<DaipiWork> response) {
                if(response.body()!=null){
                    stopProgressDialog();
                    if(response.body().getResults().size()==0){
//                        meetingHandleAdapter.setFooterVisible(View.GONE);
                        Toast.makeText(DaipiWorkActivity.this,"暂无更多数据",Toast.LENGTH_SHORT).show();
                    }else {
                        list.addAll(response.body().getResults());
                        Log.e("---------->>>请求数据发送人:",response.body().getResults().get(0).getTitle().toString());
                        daipiWorkListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DaipiWork> call, Throwable t) {
                stopProgressDialog();
                Toast.makeText(DaipiWorkActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(final int position){

        switch (list.get(position).getType()){
            case "发文":
                //showToast("点击位置"+position);
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(Config.BANNER_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api =retrofit.create(Api.class);
                Call<DetailDispatchdb> call=api.getDetailDispatchdb(list.get(position).getAddress());
                call.enqueue(new Callback<DetailDispatchdb>() {
                    @Override
                    public void onResponse(Call<DetailDispatchdb> call, Response<DetailDispatchdb> response) {
                        if(response!=null){
                            DetailDispatchdb.ResultsBean resultsBean=response.body().getResults().get(0);
                            Intent intent = new Intent(DaipiWorkActivity.this, WebviewDispatchdbActivity.class);
                            intent.putExtra(Config.NEWS,resultsBean);
//                    intent.putExtra("title",list.get(position).getTitle());
//                    intent.putExtra("sender",list.get(position).getSender());
//                    intent.putExtra("time",list.get(position).getSenderTime());
                            startActivity(intent);
                            Log.e("xxxxxxx",resultsBean.getHtmls());
                        }else{
                            Toast.makeText(DaipiWorkActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailDispatchdb> call, Throwable t) {
                        Log.e("-------------",t.getMessage().toString());
                    }
                });
                break;
            case "收文":
                //showToast("点击位置"+position);
                Retrofit retrofit2=new Retrofit.Builder()
                        .baseUrl(Config.BANNER_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api2 =retrofit2.create(Api.class);
                Call<ShouWenDbDetail> call2=api2.getShouWenDbDetail(list.get(position).getAddress());
                call2.enqueue(new Callback<ShouWenDbDetail>() {
                    @Override
                    public void onResponse(Call<ShouWenDbDetail> call, Response<ShouWenDbDetail> response) {
                        if(response!=null){
                            ShouWenDbDetail detail=response.body();
                            ShouWenDbDetail.ResultsBean resultsBean=detail.getResults().get(0);
                            Intent intent = new Intent(DaipiWorkActivity.this, WebviewIncomingdbActivity.class);
                            intent.putExtra(Config.NEWS,resultsBean);
//                    intent.putExtra("title",list.get(position).getTitle());
//                    intent.putExtra("sender",list.get(position).getSender());
//                    intent.putExtra("time",list.get(position).getSenderTime());
                            startActivity(intent);
                            Log.e("----------->>",resultsBean.getUserQc());
                            Log.e("----------->>",resultsBean.getHtmls());
                        }else{
                            Toast.makeText(DaipiWorkActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShouWenDbDetail> call, Throwable t) {
                        Log.e("------------->>",t.getMessage().toString());
                    }
                });
                break;
            case "办会":
                //showToast("点击位置"+position);
                //使用retrofit配置api
                Retrofit retrofit3=new Retrofit.Builder()
                        .baseUrl(Config.BANNER_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api3 =retrofit3.create(Api.class);
                Call<MeetingDetail> call3=api3.getMeetingDetail(list.get(position).getAddress());
                call3.enqueue(new Callback<MeetingDetail>() {
                    @Override
                    public void onResponse(Call<MeetingDetail> call, Response<MeetingDetail> response) {
                        if(response!=null){
                            MeetingDetail detail=response.body();
                            MeetingDetail.ResultsBean resultsBean=detail.getResults().get(0);
                            Intent intent = new Intent(DaipiWorkActivity.this, MeetingHandleDetailActivity.class);
                            intent.putExtra(Config.NEWS,resultsBean);
                            //finish();
                            startActivity(intent);
                            Log.e("xxxxxxx",resultsBean.getContent());
                        }else{
                            Toast.makeText(DaipiWorkActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MeetingDetail> call, Throwable t) {
                        Log.e("-------------",t.getMessage().toString());
                    }
                });
                break;
            case "行政审批":
                //使用retrofit配置api
                Retrofit retrofit4=new Retrofit.Builder()
                        .baseUrl(Config.BANNER_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api4 =retrofit4.create(Api.class);
                Call<AdministrativeApprovalDetail> call4=api4.getAdministrativeApprovalDetail(list.get(position).getAddress());
                call4.enqueue(new Callback<AdministrativeApprovalDetail>() {
                    @Override
                    public void onResponse(Call<AdministrativeApprovalDetail> call, Response<AdministrativeApprovalDetail> response) {
                        if(response!=null){
                            AdministrativeApprovalDetail detail=response.body();
                            Log.e("---------------",detail.getResults().toString());
                            AdministrativeApprovalDetail.ResultsBean resultsBean=detail.getResults().get(0);
                            Intent intent = new Intent(DaipiWorkActivity.this, AdministrativeApprovalWebviewActivity.class);
                            intent.putExtra(Config.NEWS,resultsBean);
                            startActivity(intent);
                            Log.e("---------------",resultsBean.getHtmls());
                        }else{
                            Toast.makeText(DaipiWorkActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdministrativeApprovalDetail> call, Throwable t) {
                        Log.e("-------------",t.getMessage().toString());
                    }
                });
                break;
            default:
                break;
        }
    }

    @OnClick({ R.id.title_left})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRecyclerView != null){
            mRecyclerView.destroy(); // this will totally release XR's memory
            mRecyclerView = null;
        }
    }
}
