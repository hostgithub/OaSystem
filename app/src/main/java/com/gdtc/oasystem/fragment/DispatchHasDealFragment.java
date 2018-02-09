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
import com.gdtc.oasystem.adapter.SendFileHasDealAdapter;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.bean.DispatchHasDeal;
import com.gdtc.oasystem.bean.DispatchHasDealDetail;
import com.gdtc.oasystem.bean.EventUtil;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.ui.DispatchHasWebviewActivity;
import com.gdtc.oasystem.utils.RecyclerViewSpacesItemDecoration;
import com.gdtc.oasystem.utils.SharePreferenceTools;
import com.gdtc.oasystem.widget.EndLessOnScrollListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
 * 发文已办
 */

public class DispatchHasDealFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private Unbinder mUnbinder;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    //private ArrayList<DataInfo.Info> arrayList;
    private ArrayList<DispatchHasDeal.ResultsBean> list;
    private SendFileHasDealAdapter picAdapter;
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

        picAdapter=new SendFileHasDealAdapter(list,getActivity());
        //条目点击事件
        picAdapter.setOnItemClickLitener(new SendFileHasDealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(list.get(position).getFile_source_id(),sp.getString(Config.DEPTUNIT),"OutfileDetailYiBan",sp.getString(Config.PATHDATA),position); //跳进详情页/
            }
        });
        mRecyclerView.setAdapter(picAdapter);

        picAdapter.addFooterView(R.layout.view_footer);//添加脚布局
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                if(sp.getInt("apagesize")<15){
                    refreshLayout.setRefreshing(false);
                    picAdapter.setFooterVisible(View.GONE);
                    Toast.makeText(getActivity(),"已经是最后一条数据了",Toast.LENGTH_SHORT).show();
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
        Call<DispatchHasDeal> call=api.getDispatchHasDealData(sp.getString(Config.USER_ID),pages);
        call.enqueue(new Callback<DispatchHasDeal>() {
            @Override
            public void onResponse(Call<DispatchHasDeal> call, Response<DispatchHasDeal> response) {
                if(response.body()!=null){
                    list.addAll(response.body().getResults());
                    Log.e("---------->>>",response.body().getSuccess());
                    sp.putString("count",response.body().getCount());
                    Log.e("---------->>>请求数据集合大小:", String.valueOf(response.body().getResults().size()));
                    sp.putInt("apagesize",response.body().getResults().size());
                    Log.e("---------->>>请求数据发送人:",response.body().getResults().get(0).getSender().toString());
                    Log.e("---------->>>请求数据id:",response.body().getResults().get(0).get_id().toString());
                    Log.e("---------->>>请求数据标题:",response.body().getResults().get(0).getTitle().toString());
                    picAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<DispatchHasDeal> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }


    private void getData(String file_source_id,String deptunit,String type,String pathdata,final int position){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<DispatchHasDealDetail> call=api.getDispatchHasDetailData(file_source_id,deptunit,type,pathdata);
        call.enqueue(new Callback<DispatchHasDealDetail>() {
            @Override
            public void onResponse(Call<DispatchHasDealDetail> call, Response<DispatchHasDealDetail> response) {
                if(response!=null){
                    DispatchHasDealDetail detail=response.body();
                    DispatchHasDealDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(getActivity(), DispatchHasWebviewActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    intent.putExtra("title",list.get(position).getTitle());
                    intent.putExtra("sender",list.get(position).getSender());
                    intent.putExtra("time",list.get(position).getSenderTime());
                    startActivity(intent);
                    Log.e("---------->>pdfPath",resultsBean.getHtmls());
                }else{
                    Toast.makeText(getActivity(),"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DispatchHasDealDetail> call, Throwable t) {
                Log.e("-------------解析失败",t.getMessage().toString());
            }
        });
    }

    // 接收函数二
    @Subscribe
    public void onEventBackgroundThread(EventUtil event){
        String msglog = "----onEventBackground收到了消息："+event.getMsg();
        Log.d("EventBus",msglog);
        initData(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initData(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
