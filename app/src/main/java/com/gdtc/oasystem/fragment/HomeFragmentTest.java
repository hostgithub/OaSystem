package com.gdtc.oasystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.bean.AllWaitDealSize;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.ui.AdministrativeApprovalActivity;
import com.gdtc.oasystem.ui.FaWenDaiPiActivity;
import com.gdtc.oasystem.ui.MeetingHandleActivity;
import com.gdtc.oasystem.ui.ShouWenDaiPiActivity;
import com.gdtc.oasystem.utils.DataString;
import com.gdtc.oasystem.utils.SharePreferenceTools;

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

    @BindView(R.id.load_progress)
    ProgressBar load_progress;

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

        initData(sp.getString(Config.USER_ID));
    }

    @Override
    public void initLoadData() {
        tv_date.setText(DataString.StringData());
    }

    @Override
    protected void lazyFetchData() {

    }

    @OnClick({ R.id.rl1,R.id.rl2,R.id.rl3,R.id.rl4,R.id.rl5,R.id.rl6})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl1:
                startActivity(new Intent(getActivity(),MeetingHandleActivity.class));
                Toast.makeText(getActivity(),"会议通知",Toast.LENGTH_SHORT).show();
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
            case R.id.rl5:
                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.rl6:
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
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
                    tv_size1.setText(response.body().getMeetingCount());
                    tv_size2.setText(response.body().getAdministrationCount());
                    tv_size3.setText(response.body().getDispatchCount());
                    tv_size4.setText(response.body().getInCount());
                    load_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AllWaitDealSize> call, Throwable t) {
                load_progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
