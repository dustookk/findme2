package com.gyh.findme2.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gyh.findme2.R;
import com.gyh.findme2.util.LogUtil;

public class MapActivity extends SherlockActivity {
	private static final String MAP_URL = "file:///android_asset/google_maps_v3.html";
	private static final int COUNT_DOWN_TOTAL = 10000;
	private static final int COUNT_DOWN_INTERVAL = 300;

	private WebView mWebView;
	private TextView loadingTextView;
	private LoadingCountDown loadingCountDown;

	protected double mLatitude;
	protected double mLongitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpActionBar();
		setUpFromIntent();
		setContentView(R.layout.layout_result);
		mWebView = (WebView) findViewById(R.id.wv_map);
		loadingTextView = (TextView) findViewById(R.id.tv_loading);
		loadingCountDown = new LoadingCountDown(COUNT_DOWN_TOTAL,
				COUNT_DOWN_INTERVAL);
		loadingCountDown.start();
		mWebView.setVisibility(View.INVISIBLE);
		loadingTextView.setVisibility(View.VISIBLE);
		setupWebView();
	}

	private void setUpFromIntent() {
		Intent intent = getIntent();
		mLatitude = intent.getDoubleExtra("latitude", 0);
		mLongitude = intent.getDoubleExtra("longitude", 0);
	}

	private void setUpActionBar() {
		getSupportActionBar().setTitle(R.string.map);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
		Intent intent = null;
		// try {
		// intent = Intent
		// .getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content=百度奎科大厦&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
		// } catch (URISyntaxException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		String urlStr = "geo:0,0?q=" + mLatitude + "," + mLongitude + "";
		intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
		intent.setPackage("com.google.android.apps.maps");
		startActivity(intent);
		return true;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setupWebView() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.addJavascriptInterface(new JavaScriptInterface(), "android");
		mWebView.loadUrl(MAP_URL);
	}

	protected class JavaScriptInterface {
		@JavascriptInterface
		public double getLatitude() {
			return mLatitude;
		}

		@JavascriptInterface
		public double getLongitude() {
			return mLongitude;
		}

		@JavascriptInterface
		public void onLoad() {
			loadingCountDown.cancel();
			runOnUiThread(new Runnable() {
				public void run() {
					loadingTextView.setVisibility(View.GONE);
					mWebView.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	protected class LoadingCountDown extends CountDownTimer {
		private String[] dots;
		private int length;
		private int index;

		public LoadingCountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			dots = new String[] { "", ".", "..", "..." };
			length = dots.length;
			index = 0;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			String loadingText = getString(R.string.loading)
					+ dots[index % length];
			loadingTextView.setText(loadingText);
			index++;
		}

		@Override
		public void onFinish() {
			LogUtil.d("map_loading_failed");
			loadingTextView.setText(R.string.map_loading_failed);
		}
	}
}
