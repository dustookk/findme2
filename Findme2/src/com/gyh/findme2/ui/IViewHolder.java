package com.gyh.findme2.ui;

import android.content.Intent;
import android.view.View;

public interface IViewHolder {
	public View getView();
	public void onActivityStart();
	public void onActivityResume();
	public void onActivityPause();
	public void onActivityStop();
	public void onActivityResult(int requestCode, int resultCode, Intent data);
}
