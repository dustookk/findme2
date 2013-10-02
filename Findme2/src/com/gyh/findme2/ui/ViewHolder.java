package com.gyh.findme2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public abstract class ViewHolder implements OnClickListener {
	private Activity mActivity;
	private Handler mHandler;
	private int mUIThreadId;
	
	public ViewHolder(MainActivity mActivity) {
		this.mActivity = mActivity;
		mActivity.addHolder(this);
		mHandler = new Handler();
		mUIThreadId = Process.myTid();
	}
	
	@Override
	public void onClick(View v) {
		performClick(v);
	}
	protected Activity getActivity() {
		return mActivity;
	}
	protected Handler getHandler() {
		return mHandler;
	}
	
	
	public boolean post(Runnable r) {
        return mHandler.post(r);
    }

    public boolean postDelayed(Runnable r, long delayMillis) {
        return mHandler.postDelayed(r, delayMillis);
    }
    
	public void makeLongToast(final Context context,final int resId) {
        if (Process.myTid() == mUIThreadId) {
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        } else {
        	mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    
    public void makeLongToast(final Context context,final String content) {
        if (Process.myTid() == mUIThreadId) {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        } else {
        	mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, content, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    
    public void makeShortToast(final Context context,final int resId) {
        if (Process.myTid() == mUIThreadId) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        } else {
        	mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    
    public void makeShortToast(final Context context,final String content) {
        if (Process.myTid() ==mUIThreadId) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } else {
        	mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
	
	public void onActivityStart() {
	}

	public void onActivityStop() {
	}

	public void onActivityResume() {
	}

	public void onActivityPause() {
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}
	
	public abstract View getView();
	public abstract void performClick(View view);
}
