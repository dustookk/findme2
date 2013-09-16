package com.gyh.findme2.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gyh.findme2.R;
import com.gyh.findme2.util.ToastUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

@SuppressLint("NewApi")
public class MainActivity extends SherlockActivity {

	private SlidingMenu slidingMenu;
	private boolean confirmExit = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MainViewHolder(this).getView());
		setupSlidingMenu(this);
		getSupportActionBar().setTitle(null);
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
				ToastUtil.makeShortToast(this, R.string.confirm_exit);
				confirmExit = false;
				new Handler().postDelayed(new Runnable() {
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

	private void setupSlidingMenu(Activity mActivity) {
		slidingMenu = new SlidingMenu(mActivity);
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindWidthRes(R.dimen.slidingmenu_offset);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setMenu(new SlidingViewHolder(mActivity).getView());
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
}
