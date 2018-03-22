package com.gdtc.oasystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.adapter.SendFileAdapter;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.bean.DispatchWaitDeal;
import com.gdtc.oasystem.bean.ShouWenDbDetail;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.ui.WebviewIncomingdbActivity;
import com.gdtc.oasystem.utils.RecyclerViewSpacesItemDecoration;
import com.gdtc.oasystem.utils.SharePreferenceTools;
import com.gdtc.oasystem.widget.EndLessOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangjiawei on 2017-11-13.
 * 发文待办
 */

public class IncomingWaitDealFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private Unbinder mUnbinder;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    //private ArrayList<DataInfo.Info> arrayList;
    private ArrayList<DispatchWaitDeal.ResultsBean> list;
    private SendFileAdapter picAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;
    private SharePreferenceTools sp;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        sp = new SharePreferenceTools(MyApplication.getContext());
        refreshLayout.setOnRefreshListener(this);
        list=new ArrayList();
        initData(1);
        linearLayoutManager = new LinearLayoutManager(getActivity());
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

        picAdapter=new SendFileAdapter(list,getActivity());
        //条目点击事件
        picAdapter.setOnItemClickLitener(new SendFileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(sp.getString(Config.PATHDATA),list.get(position).getFlowsort(),
                        sp.getString(Config.DEPTUNIT),list.get(position).getFileSourceId(),sp.getString(Config.USER_ID),position);
//                Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(picAdapter);

        //mRecyclerView.setAdapter(picAdapter);
        picAdapter.addFooterView(R.layout.view_footer);//添加脚布局
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                if(sp.getInt("apagesize")<15){
                    refreshLayout.setRefreshing(false);
                    picAdapter.setFooterVisible(View.GONE);
                    Toast.makeText(getActivity(),"暂无更多数据",Toast.LENGTH_SHORT).show();
                }else{
                    pages++;
                    picAdapter.setFooterVisible(View.VISIBLE);
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
    public void initLoadData() {

    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onRefresh() {
        picAdapter.setFooterVisible(View.GONE);
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
        Call<DispatchWaitDeal> call=api.getIncomingWaitDealData(sp.getString(Config.USER_ID),sp.getString(Config.DEPTUNIT),pages);
        Log.e("---------->>>",sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.DEPTUNIT));
        call.enqueue(new Callback<DispatchWaitDeal>() {
            @Override
            public void onResponse(Call<DispatchWaitDeal> call, Response<DispatchWaitDeal> response) {
                if(response.body()!=null){
                    if(response.body().getResults().size()==0){
                        refreshLayout.setRefreshing(false);
                        picAdapter.setFooterVisible(View.GONE);
                        Toast.makeText(getActivity(),"暂无更多数据",Toast.LENGTH_SHORT).show();
                    }else {
                        if(response.body().getResults().size()<15){
                            picAdapter.setFooterVisible(View.GONE);
                        }
                        list.addAll(response.body().getResults());
                        Log.e("---------->>>",response.body().getSuccess());
                        sp.putString("count",response.body().getCount());
                        Log.e("---------->>>请求数据集合大小:", String.valueOf(response.body().getResults().size()));
                        sp.putInt("apagesize",response.body().getResults().size());
                        Log.e("---------->>>请求数据发送人:",response.body().getResults().get(0).getSender().toString());
                        Log.e("---------->>>请求数据id:",response.body().getResults().get(0).get_id().toString());
                        picAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<DispatchWaitDeal> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }


    private void getData(String pathdata,String flowsort,String deptunit,String file_source_id,String sign,final int position){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<ShouWenDbDetail> call=api.getIncomingDbData(pathdata,flowsort,deptunit,file_source_id,sign);
        call.enqueue(new Callback<ShouWenDbDetail>() {
            @Override
            public void onResponse(Call<ShouWenDbDetail> call, Response<ShouWenDbDetail> response) {
                if(response!=null){
                    ShouWenDbDetail detail=response.body();
                    ShouWenDbDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(getActivity(), WebviewIncomingdbActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
//                    intent.putExtra("title",list.get(position).getTitle());
//                    intent.putExtra("sender",list.get(position).getSender());
//                    intent.putExtra("time",list.get(position).getSenderTime());
                    startActivity(intent);
                    Log.e("----------->>",resultsBean.getUserQc());
                    Log.e("----------->>",resultsBean.getHtmls());
                }else{
                    Toast.makeText(getActivity(),"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShouWenDbDetail> call, Throwable t) {
                Log.e("------------->>",t.getMessage().toString());
            }
        });
    }
}
