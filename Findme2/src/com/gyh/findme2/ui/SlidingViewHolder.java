package com.gyh.findme2.ui;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.gyh.findme2.R;
import com.gyh.findme2.service.FindmeService;

public class SlidingViewHolder implements OnClickListener,IViewHolder {
	private Activity mActivity;
	private ImageView switchView;
	
	public SlidingViewHolder(Activity activity) {
		this.mActivity = activity;
	}
	@Override
	public View getView() {
		LayoutInflater layoutInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View slidingView = layoutInflater.inflate(R.layout.layout_sliding, null);
		slidingView.findViewById(R.id.rl_switch).setOnClickListener(this);
		slidingView.findViewById(R.id.rl_exit).setOnClickListener(this);
		slidingView.findViewById(R.id.rl_setting).setOnClickListener(this);
		switchView = (ImageView) slidingView.findViewById(R.id.iv_service_swich);
		return slidingView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_switch:
			switchServiceState();
			break;
		case R.id.rl_setting:
			mActivity.startActivity(new Intent(mActivity,SettingActivity.class));
			break;
		case R.id.rl_exit:
			mActivity.finish();
			break;
		default:
			break;
		}
	}

	private void switchServiceState() {
		Intent serviceIntent = new Intent(mActivity, FindmeService.class);
		boolean isServiceOn = checkIfServiceIsStarted();
		if(isServiceOn) { // service was on
			switchView.setImageResource(R.drawable.switch_off);
			mActivity.stopService(serviceIntent);
		}else { //service was off
			switchView.setImageResource(R.drawable.switch_on);
			mActivity.startService(serviceIntent);
		}
	}
	
	private boolean checkIfServiceIsStarted() {
		ActivityManager activityManager = (ActivityManager) mActivity
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServiceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		for (RunningServiceInfo runningServiceInfo : runningServiceList) {
			ComponentName service = runningServiceInfo.service;
			String className = service.getClassName();
			if ("com.gyh.findme2.service.FindmeService".equals(className)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onActivityStart() {
		boolean isServiceOn = checkIfServiceIsStarted();
		if(isServiceOn) { // service is on
			switchView.setImageResource(R.drawable.switch_on);
		}else { //service is off
			switchView.setImageResource(R.drawable.switch_off);
		}
	}

	@Override
	public void onActivityStop() {
		
	}
	@Override
	public void onActivityResume() {
		
	}
	@Override
	public void onActivityPause() {
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	}
	
}
