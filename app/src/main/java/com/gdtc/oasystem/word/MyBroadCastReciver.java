package com.gdtc.oasystem.word;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gdtc.oasystem.bean.EventUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wangjiawei on 2018-2-12.
 */

public class MyBroadCastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case WpsModel.Reciver.ACTION_BACK://返回键广播
                System.out.println(WpsModel.Reciver.ACTION_BACK);
                Log.e("------返回键广播----------",WpsModel.Reciver.ACTION_BACK);
                break;
            case WpsModel.Reciver.ACTION_CLOSE://关闭文件时候的广播
                Log.e("------关闭广播----------",WpsModel.Reciver.ACTION_CLOSE);
                EventBus.getDefault().post(new EventUtil("发送消息"));
                context.startActivity(new Intent(context,PdfWpsActivity.class));
                break;
            case WpsModel.Reciver.ACTION_HOME://home键广播
                System.out.println(WpsModel.Reciver.ACTION_HOME);
                Log.e("-------home键广播---------",WpsModel.Reciver.ACTION_HOME);
                break;
            case WpsModel.Reciver.ACTION_SAVE://保存广播
                System.out.println(WpsModel.Reciver.ACTION_SAVE);
                Log.e("-------保存广播---------",WpsModel.Reciver.ACTION_SAVE);
                break;
            default:
                break;
        }

    }

}
