package com.gdtc.oasystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.bean.AllWaitDealSize;
import com.gdtc.oasystem.bean.EventUtil;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.ui.AdministrativeApprovalActivity;
import com.gdtc.oasystem.ui.FaWenDaiPiActivity;
import com.gdtc.oasystem.ui.MeetingHandleActivity;
import com.gdtc.oasystem.ui.ShouWenDaiPiActivity;
import com.gdtc.oasystem.utils.DataString;
import com.gdtc.oasystem.utils.NetWorkUtil;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangjiawei on 2017-11-13.
 */

public class HomeFragmentTest extends BaseFragment {

    private Unbinder mUnbinder;

    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_username)
    TextView tv_username;

    @BindView(R.id.tv_size1)
    TextView tv_size1;
    @BindView(R.id.tv_size2)
    TextView tv_size2;
    @BindView(R.id.tv_size3)
    TextView tv_size3;
    @BindView(R.id.tv_size4)
    TextView tv_size4;
    private SharePreferenceTools sp;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        //获得实例对象
        sp = new SharePreferenceTools(MyApplication.getContext());

        tv_username.setText("欢迎,"+sp.getString(Config.USERNAME));

        if(NetWorkUtil.isNetworkConnected(getContext())){
            initData(sp.getString(Config.USER_ID));
        }else {
            showErrorHint("请检查您的网络");
        }
    }

    @Override
    public void initLoadData() {
        tv_date.setText(DataString.StringData());
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(NetWorkUtil.isNetworkConnected(getContext())){
            initData(sp.getString(Config.USER_ID));
        }else {
            showErrorHint("请检查您的网络");
        }
    }

    @OnClick({ R.id.rl1,R.id.rl2,R.id.rl3,R.id.rl4})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl1:
                if(NetWorkUtil.isNetworkConnected(getContext())){
                    startActivity(new Intent(getActivity(),MeetingHandleActivity.class));
                    Toast.makeText(getActivity(),"会议通知",Toast.LENGTH_SHORT).show();
                }else {
                    showErrorHint("请检查您的网络");
                }
                break;
            case R.id.rl2:
                startActivity(new Intent(getActivity(),AdministrativeApprovalActivity.class));
                Toast.makeText(getActivity(),"行政待批",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl3:
                startActivity(new Intent(getActivity(),FaWenDaiPiActivity.class));
                Toast.makeText(getActivity(),"发文待批",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl4:
                startActivity(new Intent(getActivity(),ShouWenDaiPiActivity.class));
                Toast.makeText(getActivity(),"收文待批",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private void initData(String sign) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AllWaitDealSize> call=api.getAllWaitDealNumberData(sign);
        call.enqueue(new Callback<AllWaitDealSize>() {
            @Override
            public void onResponse(Call<AllWaitDealSize> call, Response<AllWaitDealSize> response) {
                if(response.body()!=null){
                    stopProgressDialog();
                    tv_size1.setText(response.body().getMeetingCount());
                    tv_size2.setText(response.body().getAdministrationCount());
                    tv_size3.setText(response.body().getDispatchCount());
                    tv_size4.setText(response.body().getInCount());
                }
            }

            @Override
            public void onFailure(Call<AllWaitDealSize> call, Throwable t) {
                Toast.makeText(getActivity(),"请求异常",Toast.LENGTH_SHORT).show();
            }
        });
    }
    // 接收函数二
    @Subscribe
    public void onEventBackgroundThread(EventUtil event){
        String msglog = "----onEventBackground收到了消息："+event.getMsg();
        Log.d("EventBus",msglog);
        initData(sp.getString(Config.USER_ID));
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
