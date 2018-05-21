package com.gdtc.oasystem.push;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.gdtc.oasystem.R;

/**
 * Polling service
 * @Author Ryan
 * @Create 2013-7-13 上午10:18:44
 */
public class PollingService extends Service {

	public static final String ACTION = "com.ryantang.service.PollingService";
	
	private Notification mNotification;
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		initNotifiManager();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		new PollingThread().start();
	}

	private void initNotifiManager() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
		mManager = (NotificationManager) getSystemService(
				NOTIFICATION_SERVICE);
		int icon = R.mipmap.ic_launcher;
		mNotification = new Notification();
		mNotification.icon = icon;
		mNotification.tickerText = "New Message";
		//mNotification.defaults |= Notification.DEFAULT_ALL;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;

		//   //设置在通知发出的时候的音频
		Uri mediaUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		grantUriPermission("com.android.systemui", mediaUri,
				Intent.FLAG_GRANT_READ_URI_PERMISSION);
		mNotification.sound = mediaUri;

   //设置手机震动
   //第一个，0表示手机静止的时长，第二个，1000表示手机震动的时长
   //第三个，1000表示手机震动的时长，第四个，1000表示手机震动的时长
   //此处表示手机先震动1秒，然后静止1秒，然后再震动1秒
        long[] vibrates = {0, 3000, 1000, 3000};
		mNotification.vibrate = vibrates;

   //设置LED指示灯的闪烁
   //ledARGB设置颜色
   //ledOnMS指定LED灯亮起的时间
   //ledOffMS指定LED灯暗去的时间
   //flags用于指定通知的行为
		mNotification.ledARGB = Color.GREEN;
		mNotification.ledOnMS = 1000;
		mNotification.ledOffMS = 1000;
		mNotification.flags = Notification.FLAG_SHOW_LIGHTS;
	}

	private void showNotification() {
		mNotification.when = System.currentTimeMillis();
		//Navigator to the new activity when click the notification title
		Intent intent = new Intent(this, MessageActivity.class);
		intent.putExtra("news_title","新闻标题");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		//创建 Notification.Builder 对象
//		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//				.setSmallIcon(R.mipmap.ic_launcher)
//				//点击通知后自动清除
//				.setAutoCancel(true)
//				.setContentTitle("我是带Action的Notification")
//				.setContentText("You have a new message!")
//				.setContentIntent(pendingIntent);
//
//		//发送通知
//		mManager.notify(3, builder.build());
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		mNotification = builder
				.setContentTitle("这是通知标题")
				.setContentText("这是通知内容")
				//点击通知后自动清除
				.setAutoCancel(true)
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.mipmap.ic_launcher)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
				.build();
		mManager.notify(1, mNotification);
	}

	/**
	 * Polling thread
	 * @Author Ryan
	 * @Create 2013-7-13 上午10:18:34
	 */
	int count = 0;
	class PollingThread extends Thread {
		@Override
		public void run() {
			System.out.println("Polling...");
			count ++;
			if (count % 2 == 0) {
				showNotification();
				System.out.println("New message!");
			}
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Service:onDestroy");
	}


    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        mManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        mManager.createNotificationChannel(channel);
    }

}
