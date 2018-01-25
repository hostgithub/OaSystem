package com.gdtc.oasystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.ui.AdministrativeApprovalActivity;
import com.gdtc.oasystem.ui.FaWenDaiPiActivity;
import com.gdtc.oasystem.ui.IncomingFilesDealActivity;
import com.gdtc.oasystem.ui.MeetingHandleActivity;
import com.gdtc.oasystem.ui.SendFilesDealActivity;
import com.gdtc.oasystem.ui.ShouWenDaiPiActivity;
import com.gdtc.oasystem.word.OpenWordFromWpsAndInsideActivity;

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
                startActivity(new Intent(getActivity(),OpenWordFromWpsAndInsideActivity.class));//测试生成word文档
//                Toast.makeText(getActivity(),"此模块待定",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
