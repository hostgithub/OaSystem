package com.gdtc.oasystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.adapter.MeetingHandleAdapter;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.MeetingDetail;
import com.gdtc.oasystem.bean.MeetingHandle;
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

public class XRecyclerViewActivity extends BaseActivity{

    @BindView(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;

    private ArrayList<MeetingHandle.ResultsBean> list;
    private MeetingHandleAdapter meetingHandleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;
    private SharePreferenceTools sp;

    @Override
    protected int getLayoutId() {
        return R.layout.xrecyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        sp = new SharePreferenceTools(MyApplication.getContext());
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
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        meetingHandleAdapter=new MeetingHandleAdapter(list,this);
        //条目点击事件
        meetingHandleAdapter.setOnItemClickLitener(new MeetingHandleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(list.get(position).getFlowsort(),list.get(position).get_id());
            }
        });
        mRecyclerView.setAdapter(meetingHandleAdapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pages = 1;
                list.clear();
                initData(1);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                pages++;
                initData(pages);
                mRecyclerView.loadMoreComplete();
            }
        });
//        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
//            //EndLessOnScrollListener 是自定义的监听器
//            @Override
//            public void onLoadMore() {
//                if(sp.getInt("apagesize")<15){
//                    meetingHandleAdapter.setFooterVisible(View.GONE);
//                    Toast.makeText(XRecyclerViewActivity.this,"已经是最后一条数据了",Toast.LENGTH_SHORT).show();
//                }else{
//                    pages++;
//                    meetingHandleAdapter.setFooterVisible(View.VISIBLE);
//                    initData(pages);
//                }
//            }
//            @Override
//            public void hide() {
//            }
//
//            @Override
//            public void show() {
////                relativeLayout.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
////                relativeLayout.setVisibility(View.VISIBLE);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public void onRefresh() {
//        meetingHandleAdapter.setFooterVisible(View.GONE);
//        pages = 1;
//        list.clear();
//        initData(1);
//    }

    private void initData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<MeetingHandle> call=api.getMeetingHandleListData("0",pages,sp.getString(Config.USER_ID),sp.getString(Config.DEPTUNIT));
        Log.e("---------->>>",sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.DEPTUNIT));
        call.enqueue(new Callback<MeetingHandle>() {
            @Override
            public void onResponse(Call<MeetingHandle> call, Response<MeetingHandle> response) {
                if(response.body()!=null){
                    if(response.body().getResults().size()==0){
//                        meetingHandleAdapter.setFooterVisible(View.GONE);
                        Toast.makeText(XRecyclerViewActivity.this,"暂无更多数据",Toast.LENGTH_SHORT).show();
                    }else{
                        list.addAll(response.body().getResults());
                        Log.e("---------->>>",response.body().getSuccess());
                        sp.putString("count",response.body().getCount());
                        Log.e("---------->>>请求数据集合大小:", String.valueOf(response.body().getResults().size()));
                        sp.putInt("apagesize",response.body().getResults().size());
//                        if(sp.getInt("apagesize")<15){
//                            meetingHandleAdapter.setFooterVisible(View.GONE);
//                        }else{
//                            meetingHandleAdapter.setFooterVisible(View.VISIBLE);
//                        }
                        Log.e("---------->>>请求数据发送人:",response.body().getResults().get(0).getSender().toString());
                        Log.e("---------->>>请求数据id:",response.body().getResults().get(0).get_id().toString());
                        meetingHandleAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<MeetingHandle> call, Throwable t) {
                Toast.makeText(XRecyclerViewActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(String flowsort,String flowid){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<MeetingDetail> call=api.getMeetingDetailData(flowsort,flowid);
        call.enqueue(new Callback<MeetingDetail>() {
            @Override
            public void onResponse(Call<MeetingDetail> call, Response<MeetingDetail> response) {
                if(response!=null){
                    MeetingDetail detail=response.body();
                    MeetingDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(XRecyclerViewActivity.this, MeetingHandleDetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
//                    finish();
                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.getContent());
                }else{
                    Toast.makeText(XRecyclerViewActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MeetingDetail> call, Throwable t) {
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
