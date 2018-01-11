package com.gdtc.oasystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MyApplication;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.ui.ZhengwuApplyActivity;
import com.gdtc.oasystem.utils.DataString;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wangjiawei on 2017-11-13.
 */

public class HomeFragmentTest extends BaseFragment {

    private Unbinder mUnbinder;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_username)
    TextView tv_username;
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
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                Toast.makeText(getActivity(),"会议通知",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl2:
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                Toast.makeText(getActivity(),"行政待批",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl3:
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                Toast.makeText(getActivity(),"发文待批",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl4:
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                Toast.makeText(getActivity(),"收文待批",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl5:
                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.rl6:
//                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                Toast.makeText(getActivity(),"办案待批",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
