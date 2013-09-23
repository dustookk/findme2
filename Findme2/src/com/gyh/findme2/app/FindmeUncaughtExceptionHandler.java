package com.gyh.findme2.app;

import java.lang.Thread.UncaughtExceptionHandler;

import com.gyh.findme2.util.LogUtil;

public class FindmeUncaughtExceptionHandler implements UncaughtExceptionHandler {
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		LogUtil.e(ex);
		ex.printStackTrace();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
