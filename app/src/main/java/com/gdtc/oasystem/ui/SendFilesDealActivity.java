package com.gdtc.oasystem.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;
import com.gdtc.oasystem.fragment.OneFragment;
import com.gdtc.oasystem.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SendFilesDealActivity extends BaseActivity {

    @BindView(R.id.btn_wait_deal)
    Button btn_wait_deal;
    @BindView(R.id.btn_has_deal)
    Button btn_has_deal;
    @BindView(R.id.title_center)
    TextView title_center;

    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private FragmentManager fragmentManager;
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_files_deal;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title_center.setText("发文办理");
        btn_wait_deal.setSelected(true);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));
            //恢复fragment页面
            restoreFragment();

        }else{      //正常启动时调用

            fragments.add(new OneFragment());
            fragments.add(new TwoFragment());
            showFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }

    @OnClick({ R.id.title_left,R.id.btn_wait_deal,R.id.btn_has_deal})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_wait_deal://待办
                btn_has_deal.setSelected(false);
                btn_wait_deal.setSelected(true);
                currentIndex = 0;
                showFragment();
                Toast.makeText(SendFilesDealActivity.this,"待办",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_has_deal://已办
                btn_wait_deal.setSelected(false);
                btn_has_deal.setSelected(true);
                currentIndex = 1;
                showFragment();
                Toast.makeText(SendFilesDealActivity.this,"已办",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
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
}
