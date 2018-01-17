package com.gdtc.oasystem.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.widget.TimePickerDialog;

public class RouteSetOwnerActivity extends Activity implements TimePickerDialog.TimePickerDialogInterface{

    private TimePickerDialog mTimePickerDialog;
    private TextView mTv_getOffWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_set_owner);
        mTv_getOffWork= (TextView) findViewById(R.id.tv_time);
        mTv_getOffWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.showDateAndTimePickerDialog();
            }
        });
        mTimePickerDialog = new TimePickerDialog(RouteSetOwnerActivity.this);
    }

    @Override
    public void positiveListener() {
        int hour = mTimePickerDialog.getHour();
        int minute = mTimePickerDialog.getMinute();
        Log.i("=====","=======year======"+mTimePickerDialog.getYear());
        Log.i("=====","=======getMonth======"+mTimePickerDialog.getMonth());
        Log.i("=====","=======getDay======"+mTimePickerDialog.getDay());
        Log.i("=====","=======getHour======"+mTimePickerDialog.getHour());
        Log.i("=====","=======getMinute======"+mTimePickerDialog.getMinute());
        mTv_getOffWork.setText(hour+":"+minute);
    }

    @Override
    public void negativeListener() {

    }
}
