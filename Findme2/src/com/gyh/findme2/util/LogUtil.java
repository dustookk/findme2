package com.gyh.findme2.util;

import android.util.Log;

public class LogUtil {
	private static String TAG = "findme2";
	
	public static void d(String msg) {
		Log.d(TAG, msg);
	}
	
	public static void d(Throwable tr) {
		Log.d(TAG,"", tr);
	}
	
	public static void e(String msg) {
		Log.e(TAG, msg);
	}
	
	public static void e(Throwable tr) {
		Log.d(TAG,"", tr);
	}
}
