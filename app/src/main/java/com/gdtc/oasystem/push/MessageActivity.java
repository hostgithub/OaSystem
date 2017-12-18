package com.gdtc.oasystem.push;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {

	@BindView(R.id.tv_title)
	TextView tv_title;
	@BindView(R.id.title_center)
	TextView title_center;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_notice_detail;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {

		title_center.setText("消息");

		NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent=getIntent();
		if(intent!=null){
			tv_title.setText(intent.getStringExtra("news_title"));
		}
		manager.cancelAll();
	}

	@OnClick({ R.id.title_left})
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.title_left:
				finish();
				break;
			default:
				break;
		}
	}
}
