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
		Log.e(TAG,"", tr);
	}
	public static void i(String msg) {
		Log.i(TAG, msg);
	}
	
	public static void i(Throwable tr) {
		Log.i(TAG,"", tr);
	}
	
	public static void v(String msg) {
		Log.v(TAG, msg);
	}
	
	public static void v(Throwable tr) {
		Log.v(TAG,"", tr);
	}
	public static void w(String msg) {
		Log.w(TAG, msg);
	}
	
	public static void w(Throwable tr) {
		Log.w(TAG,"", tr);
	}
	
}
