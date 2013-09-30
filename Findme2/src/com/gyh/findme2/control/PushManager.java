package com.gyh.findme2.control;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;

import com.gyh.findme2.R;
import com.gyh.findme2.ui.MapActivity;

public class PushManager {
	
	private static final int RESULT_ID = 0;
	private Context mContext;
	private NotificationManager mNotificationManager; 
	private static PushManager mPushManager = null;
	
	private PushManager(Context context) {
		this.mContext = context;
		this.mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

	}
	
	public static synchronized PushManager getInstance(Context context) {
		if(mPushManager == null) {
			mPushManager = new PushManager(context);
		}
		return mPushManager;
	}
	
	
	
	public void notifyResult(Location location) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(mContext)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("We found him !")
		        .setContentText(location.getLatitude()+" "+location.getLongitude());
		Intent resultIntent = new Intent(mContext, MapActivity.class);
		resultIntent.putExtra("latitude", location.getLatitude());
		resultIntent.putExtra("longitude", location.getLongitude());
		PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		mBuilder.setContentIntent(resultPendingIntent);
		Notification resultNotification = mBuilder.build();
		mNotificationManager.notify(RESULT_ID, resultNotification);
	}
	
	
}
