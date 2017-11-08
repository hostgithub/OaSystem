package com.gdtc.oasystem.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;

import butterknife.OnClick;

public class Private_SettingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_private__setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({ R.id.title_left,R.id.updataPasswordRlt,R.id.clear,R.id.logOutRlt})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.updataPasswordRlt://改密码
                startActivity(new Intent(Private_SettingActivity.this,ChangePasswordActivity.class));
                break;
            case R.id.clear:
                Toast.makeText(Private_SettingActivity.this,"清除缓存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logOutRlt://退出登录
                Toast.makeText(Private_SettingActivity.this,"退出登录",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(Private_SettingActivity.this);
                builder.setTitle("退出登录");//设置对话框的标题
                builder.setMessage("退出登录将前往登录界面,确定退出登录吗");//设置对话框的内容
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Private_SettingActivity.this,LoginTestActivity.class));
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
