package com.gdtc.oasystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import butterknife.BindView;


public class LoginTestActivity extends BaseActivity {

    @BindView(R.id.login_phoneNumber)
    EditText userName;
    @BindView(R.id.login_password)
    EditText password;
    @BindView(R.id.checkBox1)
    CheckBox rem_pw;
    @BindView(R.id.id_loginBtn)
    Button btn_login;

    private String userNameValue,passwordValue;
    private SharePreferenceTools sp;

    @Override
    protected int getLayoutId() {
        //去除标题
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
          /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        //获得实例对象
        sp = new SharePreferenceTools(this);
        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK", false))
        {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            userName.setText(sp.getString(Config.USER_NAME, ""));
            password.setText(sp.getString(Config.PASSWORD, ""));
            //判断自动登陆多选框状态
//            if(sp.getBoolean("AUTO_ISCHECK", false))
//            {
//                //设置默认是自动登录状态
//                auto_login.setChecked(true);
//                //跳转界面
//                Intent intent = new Intent(LoginActivity.this,LogoActivity.class);
//                LoginActivity.this.startActivity(intent);
//
//            }
        }

        // 登录监听事件  现在默认为用户名为：wjw 密码：123
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();

                if(userNameValue.equals("wjw")&&passwordValue.equals("123"))
                {
                    Toast.makeText(LoginTestActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                    //登录成功和记住密码框为选中状态才保存用户信息
                    if(rem_pw.isChecked())
                    {
                        //记住用户名、密码、
                        //SharedPreferences.Editor editor = sp.edit();
                        sp.putString(Config.USER_NAME, userNameValue);
                        sp.putString(Config.PASSWORD,passwordValue);
                        //editor.commit();
                    }
                    //跳转界面
                    Intent intent = new Intent(LoginTestActivity.this,HomePageActivity.class);
//                    Intent intent = new Intent(LoginTestActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{

                    Toast.makeText(LoginTestActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                }

            }
        });

        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.putBoolean("ISCHECK", true);

                }else {

                    System.out.println("记住密码没有选中");
                    sp.putBoolean("ISCHECK", false);

                }

            }
        });
    }
}
