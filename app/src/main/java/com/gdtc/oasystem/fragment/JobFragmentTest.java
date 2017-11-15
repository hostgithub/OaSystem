package com.gdtc.oasystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.ui.SendFilesDealActivity;
import com.gdtc.oasystem.ui.ZhengwuApplyActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wangjiawei on 2017-11-13.
 */

public class JobFragmentTest extends BaseFragment {

    private Unbinder mUnbinder;

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
    }

    @Override
    public void initLoadData() {

    }

    @Override
    protected void lazyFetchData() {

    }

    @OnClick({ R.id.rl1,R.id.rl2,R.id.rl3,R.id.rl4,R.id.rl5,R.id.rl6,R.id.table5})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl1:
                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.rl2:
                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.rl3:
                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.rl4:
                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.rl5:
                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.rl6:
                startActivity(new Intent(getActivity(),ZhengwuApplyActivity.class));
                break;
            case R.id.table5:
                startActivity(new Intent(getActivity(),SendFilesDealActivity.class));
                break;
            default:
                break;
        }
    }
}
