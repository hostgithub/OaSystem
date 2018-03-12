package com.gdtc.oasystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;

import butterknife.OnClick;

public class ZhengwuApplyActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhengwu_apply;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @OnClick({ R.id.title_back_img,R.id.rl1,R.id.rl2,R.id.rl3})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_img:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(ZhengwuApplyActivity.this,ZwsqSubmitActivity.class));
                break;
            case R.id.rl2:
                startActivity(new Intent(ZhengwuApplyActivity.this,ZwsqSubmit2Activity.class));
                break;
            case R.id.rl3:
                startActivity(new Intent(ZhengwuApplyActivity.this,ZwsqSubmit3Activity.class));
                break;
            default:
                break;
        }
    }
}
