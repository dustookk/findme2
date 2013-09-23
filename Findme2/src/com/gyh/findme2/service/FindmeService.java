package com.gyh.findme2.service;

import com.gyh.findme2.util.LogUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FindmeService extends Service {
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.d("serivce is running");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
