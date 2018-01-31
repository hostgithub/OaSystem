package com.gdtc.oasystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.adapter.AdministrativeApprovalAdapter;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.AdministrativeApproval;
import com.gdtc.oasystem.bean.AdministrativeApprovalDetail;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.utils.RecyclerViewSpacesItemDecoration;
import com.gdtc.oasystem.utils.SharePreferenceTools;
import com.gdtc.oasystem.widget.EndLessOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdministrativeApprovalActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_center)
    TextView title_center;

    private ArrayList<AdministrativeApproval.ResultsBean> list;
    private AdministrativeApprovalAdapter administrativeApprovalAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;
    private SharePreferenceTools sp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_handle;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        title_center.setText("行政待批");

        sp = new SharePreferenceTools(MyApplication.getContext());
        refreshLayout.setOnRefreshListener(this);
        list=new ArrayList();
        initData(1);
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

        administrativeApprovalAdapter=new AdministrativeApprovalAdapter(list,this);
        //条目点击事件
        administrativeApprovalAdapter.setOnItemClickLitener(new AdministrativeApprovalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(list.get(position).getFile_source_id(),list.get(position).getSender(),list.get(position).getFlowsort(),list.get(position).getTypeAdvice(),
                        sp.getString(Config.PATHDATA),list.get(position).getSort(),list.get(position).getIsRead(),sp.getString(Config.DEPTUNIT),
                sp.getString(Config.USER_DEPARTMENT_BIG),sp.getString(Config.USER_DEPARTMENT),position);
                //Toast.makeText(AdministrativeApprovalActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(administrativeApprovalAdapter);

        //mRecyclerView.setAdapter(picAdapter);
        administrativeApprovalAdapter.addFooterView(R.layout.view_footer);//添加脚布局
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                if(sp.getInt("apagesize")<15){
                    refreshLayout.setRefreshing(false);
                    administrativeApprovalAdapter.setFooterVisible(View.GONE);
                    Toast.makeText(AdministrativeApprovalActivity.this,"已经是最后一条数据了",Toast.LENGTH_SHORT).show();
                }else{
                    pages++;
                    administrativeApprovalAdapter.setFooterVisible(View.VISIBLE);
                    initData(pages);
                }
            }
            @Override
            public void hide() {
            }

            @Override
            public void show() {
//                relativeLayout.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
//                relativeLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        administrativeApprovalAdapter.setFooterVisible(View.GONE);
        pages = 1;
        list.clear();
        initData(1);
    }

    private void initData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AdministrativeApproval> call=api.getAdministrativeApprovalListData(pages,sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.DEPTUNIT));
        call.enqueue(new Callback<AdministrativeApproval>() {
            @Override
            public void onResponse(Call<AdministrativeApproval> call, Response<AdministrativeApproval> response) {
                if(response.body()!=null){
                    list.addAll(response.body().getResults());
                    Log.e("---------->>>",response.body().getSuccess());
                    sp.putString("count",response.body().getCount());
                    Log.e("---------->>>请求数据集合大小:", String.valueOf(response.body().getResults().size()));
                    sp.putInt("apagesize",response.body().getResults().size());
                    if(sp.getInt("apagesize")<15){
                        administrativeApprovalAdapter.setFooterVisible(View.GONE);
                    }else{
                        administrativeApprovalAdapter.setFooterVisible(View.VISIBLE);
                    }
                    Log.e("---------->>>请求数据发送人:",response.body().getResults().get(0).getSender().toString());
                    administrativeApprovalAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<AdministrativeApproval> call, Throwable t) {
                Toast.makeText(AdministrativeApprovalActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
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
                    Intent intent = new Intent(AdministrativeApprovalActivity.this, AdministrativeApprovalWebviewActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    intent.putExtra("title",list.get(position).getTitle());
                    intent.putExtra("sender",list.get(position).getSender());
                    intent.putExtra("time",list.get(position).getSenderTime());
                    startActivity(intent);
                    Log.e("---------------",resultsBean.getHtmls());
                }else{
                    Toast.makeText(AdministrativeApprovalActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
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
}
