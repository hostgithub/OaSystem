package com.gdtc.oasystem.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangjiawei on 2017-11-13.
 */
public abstract class TestBaseFragment extends Fragment {

    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = bindLayout(inflater);
        return rootView;
    }

    /**
     * 子类必须实现
     *
     * @param inflater
     * @return
     */
    protected abstract View bindLayout(LayoutInflater inflater);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListener();
    }


    /**
     * 子类可以不实现
     */
    private void initData() {

    }

    /**
     * 子类可以不实现
     */
    private void initListener() {

    }

    /**
     * 加载过场动画
     *
     * @param intent 意图
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        /**
         * 过场动画
         */
        //mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 加载过场动画
     *
     * @param intent      意图
     * @param requestCode 请求码
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        //mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 加载过场动画
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //mActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
