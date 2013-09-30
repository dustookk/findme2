package com.gyh.findme2.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.gyh.findme2.control.LocationController;
import com.gyh.findme2.control.LocationController.OnLocationResultListener;
import com.gyh.findme2.control.PushManager;
import com.gyh.findme2.util.LogUtil;

public class SendLocationService extends Service {
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LocationController mLocationController = new LocationController(this);
		mLocationController.setOnLocationResultListener(new OnLocationResultListener() {
			@Override
			public void onLocationResult(Location location) {
				LogUtil.d("onLocationResult in SendLocationService");
				if(location != null) {
//					Intent intent = new Intent(SendLocationService.this, MapActivity.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.putExtra("latitude", location.getLatitude());
//					intent.putExtra("longitude", location.getLongitude());
//					startActivity(intent);
					PushManager.getInstance(SendLocationService.this).notifyResult(location);
				}else {
					LogUtil.e("location in SendLocationService is null");
				}
				stopSelf();
			}
		});
		mLocationController.requstLocation();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
