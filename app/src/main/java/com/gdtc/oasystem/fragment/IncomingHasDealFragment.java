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
import com.gdtc.oasystem.adapter.IncomingHasDealAdapter;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.bean.DispatchHasDealDetail;
import com.gdtc.oasystem.bean.IncomingHasDeal;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.ui.DispatchHasWebviewActivity;
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
 * 收文已办
 */

public class IncomingHasDealFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private Unbinder mUnbinder;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    //private ArrayList<DataInfo.Info> arrayList;
    private ArrayList<IncomingHasDeal.ResultsBean> list;
    private IncomingHasDealAdapter incomingHasDealAdapter;
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

        incomingHasDealAdapter=new IncomingHasDealAdapter(list,getActivity());
        //条目点击事件
        incomingHasDealAdapter.setOnItemClickLitener(new IncomingHasDealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(sp.getString(Config.PATHDATA ),list.get(position).getFile_source_id(),sp.getString(Config.DEPTUNIT),position);   //跳进详情页
//                Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(incomingHasDealAdapter);

        //mRecyclerView.setAdapter(picAdapter);
        incomingHasDealAdapter.addFooterView(R.layout.view_footer);//添加脚布局
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                if(sp.getInt("apagesize")<15){
                    refreshLayout.setRefreshing(false);
                    incomingHasDealAdapter.setFooterVisible(View.GONE);
                    Toast.makeText(getActivity(),"已经是最后一条数据了",Toast.LENGTH_SHORT).show();
                }else{
                    pages++;
                    incomingHasDealAdapter.setFooterVisible(View.VISIBLE);
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
        incomingHasDealAdapter.setFooterVisible(View.GONE);
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
        Call<IncomingHasDeal> call=api.getIncomingHasDealData(sp.getString(Config.USER_ID),pages);
        call.enqueue(new Callback<IncomingHasDeal>() {
            @Override
            public void onResponse(Call<IncomingHasDeal> call, Response<IncomingHasDeal> response) {
                if(response.body()!=null){
                    if(response.body().getResults().size()==0){
                        refreshLayout.setRefreshing(false);
                        incomingHasDealAdapter.setFooterVisible(View.GONE);
                        Toast.makeText(getActivity(),"暂无更多数据",Toast.LENGTH_SHORT).show();
                    }else{
                        list.addAll(response.body().getResults());
                        Log.e("---------->>>",response.body().getSuccess());
                        Log.e("---------->>>请求数据集合大小:", String.valueOf(response.body().getResults().size()));
                        sp.putInt("apagesize",response.body().getResults().size());
                        Log.e("---------->>>请求数据发送人:",response.body().getResults().get(0).getUserSend().toString());
                        Log.e("---------->>>请求数据id:",response.body().getResults().get(0).getTitle().toString());
                        incomingHasDealAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<IncomingHasDeal> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }


    private void getData(String pathdata,String file_source_id,String deptunit,final int position){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<DispatchHasDealDetail> call=api.getIncomingHasDetailData(pathdata,file_source_id,deptunit);
        call.enqueue(new Callback<DispatchHasDealDetail>() {
            @Override
            public void onResponse(Call<DispatchHasDealDetail> call, Response<DispatchHasDealDetail> response) {
                if(response!=null){
                    DispatchHasDealDetail detail=response.body();
                    DispatchHasDealDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(getActivity(), DispatchHasWebviewActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    intent.putExtra("title",list.get(position).getTitle());
                    intent.putExtra("sender",list.get(position).getUserSend());
                    intent.putExtra("time",list.get(position).getSendTime());
                    startActivity(intent);
                    Log.e("---------->>",resultsBean.getHtmls());
                }else{
                    Toast.makeText(getActivity(),"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DispatchHasDealDetail> call, Throwable t) {
                Log.e("------------->>",t.getMessage().toString());
            }
        });
    }
}
