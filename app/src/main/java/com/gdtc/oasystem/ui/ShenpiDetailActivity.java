package com.gdtc.oasystem.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ShenpiDetailActivity extends BaseActivity {

    @BindView(R.id.title_center)
    TextView title_center;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    WebView tv_content;
    @BindView(R.id.edt_content)
    EditText edt_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shenpi_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title_center.setText("审批详情");
    }

    @OnClick({ R.id.title_left,R.id.btn_agree,R.id.btn_un_agree})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_agree:
                Toast.makeText(ShenpiDetailActivity.this,"同意",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_un_agree:
                Toast.makeText(ShenpiDetailActivity.this,"不同意",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
