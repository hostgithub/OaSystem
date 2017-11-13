package com.gdtc.oasystem.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.title_center)
    TextView title;
    @BindView(R.id.edt_old_password)
    EditText edt_old_password;
    @BindView(R.id.edt_new_password)
    EditText edt_new_password;
    @BindView(R.id.edt_submit_password)
    EditText edt_submit_password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("修改密码");
    }

    @OnClick({ R.id.title_left,R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_save://改密码
                if(edt_old_password.getText().toString().trim().equals("")||
                        edt_new_password.getText().toString().trim().equals("")||
                        edt_submit_password.getText().toString().trim().equals("")){
                    Toast.makeText(this,"请您将信息填写完整!",Toast.LENGTH_SHORT).show();
                } else if(edt_old_password.getText().toString().trim().equals(edt_new_password.getText().toString().trim())){
                    Toast.makeText(this,"原密码不能与新密码相同!",Toast.LENGTH_SHORT).show();
                }else if(!edt_new_password.getText().toString().trim().equals(edt_submit_password.getText().toString().trim())){
                    Toast.makeText(this,"确认密码与新密码不同,请重新输入!",Toast.LENGTH_SHORT).show();
                }else {
                    //post();
                    Toast.makeText(this,"提交成功!",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
