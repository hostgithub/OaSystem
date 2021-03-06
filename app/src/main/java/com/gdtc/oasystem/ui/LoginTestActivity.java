package com.gdtc.oasystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.gdtc.oasystem.Config;
import com.gdtc.oasystem.MainActivity;
import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.bean.ResponseBean;
import com.gdtc.oasystem.service.Api;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginTestActivity extends BaseActivity {

    @BindView(R.id.login_phoneNumber)
    EditText userName;
    @BindView(R.id.login_password)
    EditText password;
    @BindView(R.id.checkBox1)
    CheckBox rem_pw;
    @BindView(R.id.id_loginBtn)
    Button btn_login;
    @BindView(R.id.id_loginTv)
    Button id_loginTv;

    private String userNameValue,passwordValue;
    private SharePreferenceTools sp;
    private String  dept_properties="topoffice";

    @Override
    protected int getLayoutId() {
        //去除标题
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
          /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获得实例对象
        sp = new SharePreferenceTools(this);

        //.getBoolean("main",false)；当找不到"main"所对应的键值是默认返回false
        if(sp.getBoolean("main",false)){
            Intent intent=new Intent(LoginTestActivity.this,HomePageActivity.class);
            startActivity(intent);
            LoginTestActivity.this.finish();
        }
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {



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

                if(userName.getText().toString().trim().equals("")||password.getText().toString().trim().equals(""))
                {

                    //Toast.makeText(LoginTestActivity.this,"用户名或密码不能为空", Toast.LENGTH_LONG).show();
                    showToast("用户名或密码不能为空");

                }else{

                    startProgressDialog();
                    post(userNameValue,passwordValue,dept_properties);

                }

            }
        });

        id_loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginTestActivity.this, MainActivity.class));
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

    private void post(String username,String password,String dept_properties){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);

        Call<ResponseBean> call=api.getLoginData(username,password,dept_properties);
        call.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {

                if(!response.body().success.equals("")&&response.body().success=="true"){

                    Log.e(Config.TAG,response.body().getResults().get(0).getInformation());

                    //Toast.makeText(LoginTestActivity.this,response.body().getResults().get(0).getInformation(), Toast.LENGTH_SHORT).show();
                    showToast(response.body().getResults().get(0).getInformation());
                    //登录成功和记住密码框为选中状态才保存用户信息
                    if(rem_pw.isChecked())
                    {
                        //记住用户名、密码、
                        //SharedPreferences.Editor editor = sp.edit();
                        sp.putString(Config.USER_NAME, userNameValue);
                        sp.putString(Config.PASSWORD,passwordValue);
                        //editor.commit();
                    }

                    sp.putString("userId",response.body().getResults().get(0).getPersonnelId());
                    Log.e("------------>>userId",response.body().getResults().get(0).getPersonnelId());
                    sp.putString("userName",response.body().getResults().get(0).getUserName());
                    Log.e("------------>>userName",response.body().getResults().get(0).getUserName());
                    sp.putString("deptName",response.body().getResults().get(0).getDepetName());
                    Log.e("----------->>deptName",response.body().getResults().get(0).getDepetName());
                    sp.putString("company",response.body().getResults().get(0).getCompany());
                    Log.e("----------->>company",response.body().getResults().get(0).getCompany());
                    sp.putString(Config.DEPTUNIT,response.body().getResults().get(0).getDepetUnit());
                    Log.e("------------>>deptunit",response.body().getResults().get(0).getDepetUnit());
                    sp.putString(Config.PATHDATA,response.body().getResults().get(0).getPathdata());
                    Log.e("------------>>pathdata",response.body().getResults().get(0).getPathdata());

                    sp.putString(Config.USER_DEPARTMENT,response.body().getResults().get(0).getUser_department());
                    Log.e("--user_department",response.body().getResults().get(0).getUser_department());

                    sp.putString(Config.USER_DEPARTMENT_BIG,response.body().getResults().get(0).getUser_department_big());
                    Log.e("--user_department_big",response.body().getResults().get(0).getUser_department_big());
                    sp.putBoolean("main",true);
                    sp.putString("ip",getLocalIpAddress());
                    Log.e("--ip--",getLocalIpAddress());
                    //跳转界面
                    Intent intent = new Intent(LoginTestActivity.this,HomePageActivity.class);
                    stopProgressDialog();
                    startActivity(intent);
                    finish();
                }else{
                    //Toast.makeText(LoginTestActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                    showToast("用户名或密码错误，请重新登录");
                }
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Log.e("sssss",t.getMessage());
                showErrorHint("网络异常请稍后重试");
                stopProgressDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sp!=null){
            sp=null;
        }
    }

    public String getLocalIpAddress()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {
            Log.e("WifiPreferenceIpAddress", ex.toString());
        }
        return null;
    }
}
