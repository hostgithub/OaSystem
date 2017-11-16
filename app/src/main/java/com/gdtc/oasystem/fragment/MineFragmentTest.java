package com.gdtc.oasystem.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseFragment;
import com.gdtc.oasystem.ui.ChangePasswordActivity;
import com.gdtc.oasystem.ui.LoginTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wangjiawei on 2017-11-13.
 */

public class MineFragmentTest extends BaseFragment {

    private Unbinder mUnbinder;
    @BindView(R.id.title_center)
    TextView title_center;
    @BindView(R.id.btn_switch)
    Button btn_switch;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
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
        title_center.setText("我的");
    }

    @Override
    protected void lazyFetchData() {

    }

    @OnClick({R.id.updataPasswordRlt,R.id.clear,R.id.logOutRlt,R.id.btn_switch})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updataPasswordRlt://改密码
                startActivity(new Intent(getActivity(),ChangePasswordActivity.class));
                break;
            case R.id.btn_switch:
                if(btn_switch.isSelected()==true){
                    btn_switch.setSelected(false);
                }else {
                    btn_switch.setSelected(true);
                }
                break;
            case R.id.clear:
                Toast.makeText(getActivity(),"清除缓存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logOutRlt://退出登录
                Toast.makeText(getActivity(),"退出登录",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("退出登录");//设置对话框的标题
                builder.setMessage("退出登录将前往登录界面,确定退出登录吗");//设置对话框的内容
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(),LoginTestActivity.class));
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog b=builder.create();
                b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                break;
            default:
                break;
        }
    }
}
