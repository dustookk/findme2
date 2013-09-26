package com.gyh.findme2.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gyh.findme2.R;
import com.gyh.findme2.persist.DataPref;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

public class MainActivity extends SherlockActivity {
	
	private ViewHolder mMainViewHolder;
	private ViewHolder slidingViewHolder;
	private SlidingMenu slidingMenu;
	private Handler mHandler;
	private List<ViewHolder> viewHolderList;
	private boolean confirmExit = true;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewHolderList = new ArrayList<ViewHolder>(2);
		mHandler = new Handler();
		mMainViewHolder = new MainViewHolder(this);
		setContentView(mMainViewHolder.getView());
		setupSlidingMenu();
		getSupportActionBar().setTitle(null);
		setUpTheFirstTime();
	}

	private void setUpTheFirstTime() {
		DataPref mDataPref = DataPref.getInstance(this);
		if(mDataPref.isFirstTime()) {
			//TODO show a dialog
			mDataPref.setNotification(true);
			mDataPref.setResultMode("3");
			mDataPref.setFirstTime(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem sttingItem = menu.add("open menu");
		sttingItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		sttingItem.setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_light);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		slidingMenu.toggle();
		return true;
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			slidingMenu.toggle();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			// toggle the sliding menu
			if (slidingMenu.isMenuShowing()) {
				slidingMenu.showContent();
				return true;
			}
			// confirm to exit
			if (confirmExit) {
				mMainViewHolder.makeShortToast(this, R.string.confirm_exit);
				confirmExit = false;
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						confirmExit = true;
					}
				}, 2000);
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void setupSlidingMenu() {
		slidingMenu = new SlidingMenu(this);
		slidingViewHolder = new SlidingViewHolder(this);
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindWidthRes(R.dimen.slidingmenu_offset);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setMenu(slidingViewHolder.getView());
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setSecondaryOnOpenListner(new OnOpenListener() {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			@Override
			public void onOpen() {
				imm.hideSoftInputFromWindow(findViewById(R.id.et_main_input)
						.getWindowToken(), 0);
			}
		});
	}
	
	
	@Override
	protected void onStart() {
		for (ViewHolder mViewHolder : viewHolderList) {
			mViewHolder.onActivityStart();
		}
		super.onStart();
	}
	@Override
	protected void onResume() {
		for (ViewHolder mViewHolder : viewHolderList) {
			mViewHolder.onActivityResume();
		}
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		for (ViewHolder mViewHolder : viewHolderList) {
			mViewHolder.onActivityPause();
		}
		super.onPause();
	}
	@Override
	protected void onStop() {
		for (ViewHolder mViewHolder : viewHolderList) {
			mViewHolder.onActivityStop();
		}
		super.onStop();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		for (ViewHolder mViewHolder : viewHolderList) {
			mViewHolder.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	public void addHolder(ViewHolder mViewHolder) {
		viewHolderList.add(mViewHolder);
	}
}
