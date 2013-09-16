package com.gyh.findme2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.gyh.findme2.R;
import com.gyh.findme2.util.LogUtil;

public class SlidingViewHolder implements OnClickListener {
	private Activity mActivity;
	private ImageView switchView;
	private boolean isServiceOn = false;
	
	public SlidingViewHolder(Activity activity) {
		this.mActivity = activity;
	}
	
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
		LogUtil.d("click");
		switch (v.getId()) {
		case R.id.rl_switch:
			//TODO
			changePic();
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

	private void changePic() {
		if(isServiceOn) {
			switchView.setImageResource(R.drawable.switch_off);
		}else {
			switchView.setImageResource(R.drawable.switch_on);
		}
		isServiceOn = !isServiceOn;
	}
}
