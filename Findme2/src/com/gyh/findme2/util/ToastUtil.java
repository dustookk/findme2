package com.gyh.findme2.util;

import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.widget.Toast;

public class ToastUtil {
	
	private static Handler handler = new Handler();
	
    public static void makeLongToast(final Context context,final int resId) {
        if (Process.myTid() == getUIThreadId()) {
            // 调用在UI线程
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        } else {
            // 调用在非UI线程
        	handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    
    public static void makeLongToast(final Context context,final String content) {
        if (Process.myTid() == getUIThreadId()) {
            // 调用在UI线程
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        } else {
            // 调用在非UI线程
        	handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, content, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    
    public static void makeShortToast(final Context context,final int resId) {
        if (Process.myTid() == getUIThreadId()) {
            // 调用在UI线程
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        } else {
            // 调用在非UI线程
        	handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    
    public static void makeShortToast(final Context context,final String content) {
        if (Process.myTid() == getUIThreadId()) {
            // 调用在UI线程
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } else {
            // 调用在非UI线程
        	handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static int getUIThreadId() {
    	return android.os.Process.getThreadPriority(android.os.Process.myTid());
    }
}
