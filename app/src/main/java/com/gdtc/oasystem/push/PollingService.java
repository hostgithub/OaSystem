package com.gdtc.oasystem.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
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
		mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int icon = R.mipmap.ic_launcher;
		mNotification = new Notification();
		mNotification.icon = icon;
		mNotification.tickerText = "New Message";
		mNotification.defaults |= Notification.DEFAULT_SOUND;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
	}

	private void showNotification() {
		mNotification.when = System.currentTimeMillis();
		//Navigator to the new activity when click the notification title
		Intent intent = new Intent(this, MessageActivity.class);
		intent.putExtra("news_title","新闻标题");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//创建 Notification.Builder 对象
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.mipmap.ic_launcher)
				//点击通知后自动清除
				.setAutoCancel(true)
				.setContentTitle("我是带Action的Notification")
				.setContentText("You have a new message!")
				.setContentIntent(pendingIntent);
		//发送通知
		mManager.notify(3, builder.build());
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

}
