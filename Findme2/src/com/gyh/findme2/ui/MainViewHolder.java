package com.gyh.findme2.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.gyh.findme2.R;

public class MainViewHolder implements OnClickListener {
	private Activity mActivity;
	private static final String[] COUNTRIES = new String[] { "18623123421",
			"15923328165" };

	public MainViewHolder(Activity activity) {
		this.mActivity = activity;
	}

	public View getView() {
		LayoutInflater layoutInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mianView = layoutInflater.inflate(R.layout.layout_activity_main,
				null);
		mianView.findViewById(R.id.iv_add_contact).setOnClickListener(this);
		mianView.findViewById(R.id.btn_get_him).setOnClickListener(this);
		AutoCompleteTextView inputView = (AutoCompleteTextView) mianView
				.findViewById(R.id.et_main_input);
		inputView.setThreshold(1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity,
				R.layout.simple_dropdown_item_1line, COUNTRIES);
		inputView.setAdapter(adapter);
		return mianView;
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add_contact:

			break;
		case R.id.btn_get_him:

			break;
		default:
			break;
		}
	}

}
