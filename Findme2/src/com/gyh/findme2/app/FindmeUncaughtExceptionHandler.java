package com.gyh.findme2.app;

import java.lang.Thread.UncaughtExceptionHandler;

public class FindmeUncaughtExceptionHandler implements UncaughtExceptionHandler {
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		//LogUtil.e(ex.getMessage());
		ex.printStackTrace();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
