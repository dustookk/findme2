package com.gyh.findme2.ui;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.gyh.findme2.R;
import com.gyh.findme2.service.FindmeService;
import com.gyh.findme2.util.LogUtil;
import com.gyh.findme2.util.SysUtil;

public class SlidingViewHolder extends ViewHolder {
	private ImageView switchView;

	public SlidingViewHolder(MainActivity mMainActivity) {
		super(mMainActivity);
	}

	@Override
	public View getView() {
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View slidingView = layoutInflater
				.inflate(R.layout.layout_sliding, null);
		slidingView.findViewById(R.id.rl_switch).setOnClickListener(this);
		slidingView.findViewById(R.id.rl_exit).setOnClickListener(this);
		slidingView.findViewById(R.id.rl_setting).setOnClickListener(this);
		switchView = (ImageView) slidingView
				.findViewById(R.id.iv_service_swich);
		return slidingView;
	}

	@Override
	public void performClick(int viewId){
		switch (viewId) {
		case R.id.rl_switch:
			switchServiceState();
			break;
		case R.id.rl_setting:
			getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
			break;
		case R.id.rl_exit:
			getActivity().finish();
			break;
		default:
			break;
		}
	}

	private void switchServiceState() {
		Intent serviceIntent = new Intent(getActivity(), FindmeService.class);
		boolean isServiceOn = checkIfServiceIsStarted();
		if (isServiceOn) { // service was on
			switchView.setImageResource(R.drawable.switch_off);
			getActivity().stopService(serviceIntent);
		} else { // service was off
			boolean isLocationEnabled = SysUtil.isLocationEnabled(getActivity());
			if (!isLocationEnabled) {
				LogUtil.d("location not enabled");
				switchView.setImageResource(R.drawable.switch_on);
				postDelayed(new Runnable() {
					@Override
					public void run() {
						switchView.setImageResource(R.drawable.switch_off);
					}
				}, 300);
				return;
			}
			switchView.setImageResource(R.drawable.switch_on);
			getActivity().startService(serviceIntent);
		}
	}

	private boolean checkIfServiceIsStarted() {
		ActivityManager activityManager = (ActivityManager) getActivity()
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
		if (isServiceOn) { // service is on
			switchView.setImageResource(R.drawable.switch_on);
		} else { // service is off
			switchView.setImageResource(R.drawable.switch_off);
		}
	}
}
