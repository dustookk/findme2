package com.gyh.findme2.app;


import com.gyh.findme2.util.LogUtil;

import android.app.Application;

public class FindmeApplication extends Application {
	@Override
	public void onCreate() {
		try {
            //Thread.setDefaultUncaughtExceptionHandler(new FindmeUncaughtExceptionHandler());
        } catch (Exception e) {
            LogUtil.e(e);
        }
	};
}
