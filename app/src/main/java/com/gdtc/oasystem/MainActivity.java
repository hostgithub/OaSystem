package com.gdtc.oasystem;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.utils.StatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private boolean isExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        TextView title = (TextView) findViewById(R.id.title_tv);
        title.setText("主页面");

        setStatusBar();
    }

    protected void setStatusBar() {

        StatusBarUtil.setColor(this,
                getResources().getColor(R.color.white_snow), 1);
    }

//    @OnClick({ R.id.zwsq,R.id.fwbl})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.fwbl:
//                startActivity(new Intent(MainActivity.this,SendFilesDealActivity.class));
//                break;
//            case R.id.zwsq:
//                startActivity(new Intent(MainActivity.this,ZwsqSubmitActivity.class));
//                break;
//            default:
//                break;
//        }
//    }

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
