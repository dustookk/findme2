package com.gyh.findme2.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gyh.findme2.R;
import com.gyh.findme2.util.ToastUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends SherlockActivity {

	private SlidingMenu slidingMenu;
	private boolean confirmExit = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_main);
		getSupportActionBar().setTitle("");
		setupSlidingMenu(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem sttingItem = menu.add("");
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
			//toggle the sliding menu
			if(slidingMenu.isMenuShowing()) {
				slidingMenu.showContent();
				return true;
			}
			//confirm to exit
			if(confirmExit) {
				ToastUtil.makeShortToast(this, R.string.confirm_exit);
				confirmExit = false;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						confirmExit = true;
					}
				}, 2000);
				return true;
			}else {
				return super.onKeyDown(keyCode, event);
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void setupSlidingMenu(Context context) {
		slidingMenu = new SlidingMenu(context);
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.layout_sliding);
	}

}
