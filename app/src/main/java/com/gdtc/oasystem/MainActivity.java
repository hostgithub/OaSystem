package com.gdtc.oasystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.ui.Private_SettingActivity;
import com.gdtc.oasystem.ui.SendFilesDealActivity;
import com.gdtc.oasystem.ui.ZwsqSubmitActivity;
import com.gdtc.oasystem.utils.SharePreferenceTools;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private boolean isExit;
    private SharePreferenceTools sp;
    @BindView(R.id.title_right)
    TextView title_right;
    @BindView(R.id.userNameTv)
    TextView userNameTv;
    @BindView(R.id.danwei)
    TextView danwei;
    @BindView(R.id.bumen)
    TextView bumen;
    @BindView(R.id.post)
    TextView post;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        sp=new SharePreferenceTools(this);
        title_right.setText(sp.getString(Config.USER_NAME));
        userNameTv.setText("您好,"+sp.getString(Config.USER_NAME));
    }

    @OnClick({ R.id.iv_set,R.id.zwsq,R.id.fwbl})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_set:
                startActivity(new Intent(MainActivity.this,Private_SettingActivity.class));
                break;
            case R.id.fwbl:
                startActivity(new Intent(MainActivity.this,SendFilesDealActivity.class));
                break;
            case R.id.zwsq:
                startActivity(new Intent(MainActivity.this,ZwsqSubmitActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit=null;
        if(!isExit){
            isExit=true;
            Toast.makeText(MainActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit=new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;//取消退出
                }
            },2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        }else{
            finish();
            System.exit(0);
        }
    }
}
