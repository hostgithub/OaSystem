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
import com.gdtc.oasystem.ui.IncomingFilesDealActivity;
import com.gdtc.oasystem.ui.MeetingHandleActivity;
import com.gdtc.oasystem.ui.SendFilesDealActivity;
import com.gdtc.oasystem.ui.ShouWenDaiPiActivity;
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

public class JobFragmentTest extends BaseFragment {

    private Unbinder mUnbinder;
    @BindView(R.id.bumber1)
    TextView bumber1;
    @BindView(R.id.bumber2)
    TextView bumber2;
    @BindView(R.id.bumber3)
    TextView bumber3;
    @BindView(R.id.bumber4)
    TextView bumber4;
    private SharePreferenceTools sp;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_job;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        //获得实例对象
        sp = new SharePreferenceTools(MyApplication.getContext());

        initData(sp.getString(Config.USER_ID));
    }

    @Override
    public void initLoadData() {

    }

    @Override
    protected void lazyFetchData() {

    }

    @OnClick({ R.id.rl1,R.id.rl2,R.id.rl3,R.id.rl4,R.id.rl5,R.id.rl6,R.id.table1,
            R.id.table2,R.id.table3,R.id.table4,R.id.table5,R.id.table6,R.id.table7,R.id.table8,R.id.table9})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl1:
                Toast.makeText(getActivity(),"会议通知",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),MeetingHandleActivity.class));
                break;
            case R.id.rl2:
                Toast.makeText(getActivity(),"行政待批",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),AdministrativeApprovalActivity.class));
                break;
            case R.id.rl3:
                Toast.makeText(getActivity(),"收文待批",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),ShouWenDaiPiActivity.class));
                break;
            case R.id.rl4:
                Toast.makeText(getActivity(),"发文待批",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),FaWenDaiPiActivity.class));
                break;
            case R.id.rl5:
                //startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl6:
                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.table1:
                startActivity(new Intent(getActivity(),MeetingHandleActivity.class));
                break;
            case R.id.table2:
                Toast.makeText(getActivity(),"待批工作",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(),IncomingFilesDealActivity.class));
                break;
            case R.id.table3:
                Toast.makeText(getActivity(),"已批工作",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(),IncomingFilesDealActivity.class));
                break;
            case R.id.table4:
                startActivity(new Intent(getActivity(),IncomingFilesDealActivity.class));
                break;
            case R.id.table5:
                startActivity(new Intent(getActivity(),SendFilesDealActivity.class));
                break;
            case R.id.table6:
                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
                break;
            case R.id.table7:
                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
                break;
            case R.id.table8:
                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
                break;
            case R.id.table9:
//                startActivity(new Intent(getActivity(),IntranetActivity.class));
//                startActivity(new Intent(getActivity(),RouteSetOwnerActivity.class));//测试日期时间选择器的
//                startActivity(new Intent(getActivity(),OpenWordFromWpsAndInsideActivity.class));//测试生成word文档
                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
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
                    bumber1.setText(response.body().getMeetingCount());
                    bumber2.setText(response.body().getAdministrationCount());
                    bumber4.setText(response.body().getDispatchCount());
                    bumber3.setText(response.body().getInCount());
                }
            }

            @Override
            public void onFailure(Call<AllWaitDealSize> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
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
