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
import com.gdtc.oasystem.adapter.SendFileAdapter;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.DetailDispatchdb;
import com.gdtc.oasystem.bean.DispatchWaitDeal;
import com.gdtc.oasystem.bean.EventUtil;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.utils.RecyclerViewSpacesItemDecoration;
import com.gdtc.oasystem.utils.RetrofitUtils;
import com.gdtc.oasystem.utils.SharePreferenceTools;
import com.gdtc.oasystem.widget.EndLessOnScrollListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaWenDaiPiActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_center)
    TextView title_center;

    private ArrayList<DispatchWaitDeal.ResultsBean> list;
    private SendFileAdapter meetingHandleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;
    private SharePreferenceTools sp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fa_wen_dai_pi;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        title_center.setText("发文待批");
        EventBus.getDefault().register(this);
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

        meetingHandleAdapter=new SendFileAdapter(list,this);
        //条目点击事件
        meetingHandleAdapter.setOnItemClickLitener(new SendFileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(sp.getString(Config.PATHDATA),sp.getString(Config.DEPTUNIT),sp.getString(Config.USER_ID),sp.getString(Config.USERNAME)
                        ,list.get(position).getFileSourceId(),list.get(position).getFlowsort(),position);
            }
        });
        mRecyclerView.setAdapter(meetingHandleAdapter);

        //mRecyclerView.setAdapter(picAdapter);
        meetingHandleAdapter.addFooterView(R.layout.view_footer);//添加脚布局
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                if(sp.getInt("apagesize")<15){
                    refreshLayout.setRefreshing(false);
                    meetingHandleAdapter.setFooterVisible(View.GONE);
                    Toast.makeText(FaWenDaiPiActivity.this,"已经是最后一条数据了",Toast.LENGTH_SHORT).show();
                }else{
                    pages++;
                    meetingHandleAdapter.setFooterVisible(View.VISIBLE);
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
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onRefresh() {
        meetingHandleAdapter.setFooterVisible(View.GONE);
        pages = 1;
        list.clear();
        initData(1);
    }

    private void initData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<DispatchWaitDeal> call=api.getDispatchWaitDealData(sp.getString(Config.USER_ID),sp.getString(Config.DEPTUNIT),pages);
        Log.e("---------->>>",sp.getString(Config.USER_ID));
        Log.e("---------->>>",sp.getString(Config.DEPTUNIT));
        call.enqueue(new Callback<DispatchWaitDeal>() {
            @Override
            public void onResponse(Call<DispatchWaitDeal> call, Response<DispatchWaitDeal> response) {
                if(response.body()!=null){
                    if(response.body().getResults().size()==0){
                        refreshLayout.setRefreshing(false);
                        meetingHandleAdapter.setFooterVisible(View.GONE);
                        meetingHandleAdapter.notifyDataSetChanged();
                        Toast.makeText(FaWenDaiPiActivity.this,"暂无更多数据",Toast.LENGTH_SHORT).show();
                    }else{
                        if(list!=null){
                            list.clear();//用于更新列表数据，必须移除之前的数据
                        }
                        list.addAll(response.body().getResults());
                        Log.e("---------->>>",response.body().getSuccess());
                        sp.putString("count",response.body().getCount());
                        Log.e("---------->>>请求数据集合大小:", String.valueOf(response.body().getResults().size()));
                        sp.putInt("apagesize",response.body().getResults().size());
                        if(sp.getInt("apagesize")<15){
                            meetingHandleAdapter.setFooterVisible(View.GONE);
                        }else{
                            meetingHandleAdapter.setFooterVisible(View.VISIBLE);
                        }
                        Log.e("---------->>>请求数据发送人:",response.body().getResults().get(0).getSender().toString());
                        Log.e("---------->>>请求数据id:",response.body().getResults().get(0).get_id().toString());
                        meetingHandleAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<DispatchWaitDeal> call, Throwable t) {
                Toast.makeText(FaWenDaiPiActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }


    private void getData(String pathdata, String deptunit, String sign, String userid, String file_source_id,String flowsort,final int position){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<DetailDispatchdb> call=api.getDispatchdbDetailData(pathdata,deptunit,sign,userid,file_source_id,flowsort);
        call.enqueue(new Callback<DetailDispatchdb>() {
            @Override
            public void onResponse(Call<DetailDispatchdb> call, Response<DetailDispatchdb> response) {
                if(response!=null){
                    DetailDispatchdb.ResultsBean resultsBean=response.body().getResults().get(0);
                    Intent intent = new Intent(FaWenDaiPiActivity.this, WebviewDispatchdbActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
//                    intent.putExtra("title",list.get(position).getTitle());
//                    intent.putExtra("sender",list.get(position).getSender());
//                    intent.putExtra("time",list.get(position).getSenderTime());
                    startActivity(intent);
                    meetingHandleAdapter.notifyDataSetChanged();
                    Log.e("xxxxxxx",resultsBean.getHtmls());
                }else{
                    Toast.makeText(FaWenDaiPiActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailDispatchdb> call, Throwable t) {
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

    @Subscribe(threadMode = ThreadMode.BACKGROUND )
    public void onMoonEvent(EventUtil messageEvent){
        if(list.size()==1){
            list.clear();
        }
        initData(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(linearLayoutManager!=null){
            linearLayoutManager=null;
        }
        EventBus.getDefault().unregister(this);
        if(mRecyclerView != null){
            mRecyclerView.destroyDrawingCache(); // this will totally release XR's memory
            mRecyclerView = null;
        }
    }
}
