package com.gdtc.oasystem.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.fragment.HomeFragmentTest;
import com.gdtc.oasystem.fragment.InfoFragmentTest;
import com.gdtc.oasystem.fragment.JobFragmentTest;
import com.gdtc.oasystem.fragment.MineFragmentTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageActivity extends BaseActivity {

    @BindView(R.id.rg_menu)
    RadioGroup rg_menu;
    @BindView(R.id.btn_first)
    RadioButton btn_first;
    @BindView(R.id.btn_second)
    RadioButton btn_second;
    @BindView(R.id.btn_third)
    RadioButton btn_third;
    @BindView(R.id.btn_four)
    RadioButton btn_four;

    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private FragmentManager fragmentManager;
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;
    private boolean isExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        btn_first.setSelected(true);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));
            fragments.add(fragmentManager.findFragmentByTag(2+""));
            fragments.add(fragmentManager.findFragmentByTag(3+""));
            //恢复fragment页面
            restoreFragment();

        }else{      //正常启动时调用

            fragments.add(new HomeFragmentTest());
            fragments.add(new InfoFragmentTest());
            fragments.add(new JobFragmentTest());
            fragments.add(new MineFragmentTest());
            showFragment();
        }
    }

    @OnClick({ R.id.btn_first,R.id.btn_second,R.id.btn_third,R.id.btn_four})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_first:
                btn_first.setSelected(true);
                btn_second.setSelected(false);
                btn_third.setSelected(false);
                btn_four.setSelected(false);
                currentIndex = 0;
                showFragment();
                break;
            case R.id.btn_second:
                btn_first.setSelected(false);
                btn_second.setSelected(true);
                btn_third.setSelected(false);
                btn_four.setSelected(false);
                currentIndex = 1;
                showFragment();
                break;
            case R.id.btn_third://
                btn_first.setSelected(false);
                btn_second.setSelected(false);
                btn_third.setSelected(true);
                btn_four.setSelected(false);
                currentIndex = 2;
                showFragment();
                break;
            case R.id.btn_four://
                btn_first.setSelected(false);
                btn_second.setSelected(false);
                btn_third.setSelected(false);
                btn_four.setSelected(true);
                currentIndex = 3;
                showFragment();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fragments.get(currentIndex).isAdded()){
            transaction
                    .hide(currentFragment)
                    .add(R.id.content,fragments.get(currentIndex),""+currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag
        }else{
            transaction
                    .hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }
        currentFragment = fragments.get(currentIndex);
        transaction.commit();
    }

    /**
     * 恢复fragment
     */
    private void restoreFragment(){
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if(i == currentIndex){
                mBeginTreansaction.show(fragments.get(i));
            }else{
                mBeginTreansaction.hide(fragments.get(i));
            }
        }
        mBeginTreansaction.commit();
        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);
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
            Toast.makeText(HomePageActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
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
