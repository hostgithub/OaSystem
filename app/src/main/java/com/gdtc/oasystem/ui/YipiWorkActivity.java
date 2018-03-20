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

public class YipiWorkActivity extends BaseActivity{

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

        title_center.setText("已批工作");

        sp = new SharePreferenceTools(MyApplication.getContext());
        list=new ArrayList();
        initData();
        startProgressDialog();
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
                switch (list.get(position).getType()){
                    case "发文":
                        break;
                    case "收文":
                        break;
                    case "办会":
                        break;
                    case "行政审批":
                        break;
                    default:
                        break;
                }
                showToast("点击位置"+position);
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
        Call<DaipiWork> call=api.getYipiWorkList(sp.getString(Config.PATHDATA),sp.getString(Config.DEPTUNIT),
                sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.DEPTUNIT));
        call.enqueue(new Callback<DaipiWork>() {
            @Override
            public void onResponse(Call<DaipiWork> call, Response<DaipiWork> response) {
                if(response.body()!=null){
                    stopProgressDialog();
                    if(response.body().getResults().size()==0){
//                        meetingHandleAdapter.setFooterVisible(View.GONE);
                        Toast.makeText(YipiWorkActivity.this,"暂无更多数据",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(YipiWorkActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(String file_source_id,String usersend,String flowsort,String typeAdvice,String pathdata,
                         String sort,String isRead,String deptunit,String user_department_big,String user_department,final int position){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AdministrativeApprovalDetail> call=api.getAdministrativeApprovalDetailData(file_source_id,usersend,flowsort,typeAdvice,pathdata,
                sort,isRead,deptunit,user_department_big,user_department);
        call.enqueue(new Callback<AdministrativeApprovalDetail>() {
            @Override
            public void onResponse(Call<AdministrativeApprovalDetail> call, Response<AdministrativeApprovalDetail> response) {
                if(response!=null){
                    AdministrativeApprovalDetail detail=response.body();
                    AdministrativeApprovalDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(YipiWorkActivity.this, AdministrativeApprovalWebviewActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
//                    intent.putExtra("title",list.get(position).getTitle());
//                    intent.putExtra("sender",list.get(position).getSender());
//                    intent.putExtra("time",list.get(position).getSenderTime());
                    startActivity(intent);
                    Log.e("---------------",resultsBean.getHtmls());
                }else{
                    Toast.makeText(YipiWorkActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdministrativeApprovalDetail> call, Throwable t) {
                Log.e("-------------",t.getMessage().toString());
            }
        });
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
