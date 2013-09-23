package com.gyh.findme2.persist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class DataPref {
	
	private static final String FIRST_TIME = "FIRST_TIME";
	private static final String THE_WAY_TO_SHOW_RESULT = "THE_WAY_TO_SHOW_RESULT";
	private static final String NOTIFICATION = "NOTIFICATION";
	
	

	private static DataPref mDataPref;
	private Context mContext;
	private SharedPreferences mPref;
	private Editor mEditor;

	private DataPref(Context context) {
		this.mContext = context;
	}

	public static DataPref getInstance(Context context) {
		if (mDataPref == null) {
			mDataPref = new DataPref(context);
		}
		return mDataPref;
	}

	private SharedPreferences getSharedPreferences() {
		if (null == mPref) {
			mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		return mPref;
	}

	private Editor getEditor() {
		if (null == mEditor) {
			mEditor = getSharedPreferences().edit();
		}
		return mEditor;
	}

	public boolean setFirstTime(boolean firstTime) {
		Editor editor = getEditor();
	    editor.putBoolean(FIRST_TIME, firstTime);
		return editor.commit();
	}

	public boolean isFirstTime() {
		return getSharedPreferences().getBoolean(FIRST_TIME, true);
	}
	
	public boolean setResultMode(String resultMode) {
		Editor editor = getEditor();
	    editor.putString(THE_WAY_TO_SHOW_RESULT, resultMode);
		return editor.commit();
	}
	
	public String getResultMode() {
		return getSharedPreferences().getString(THE_WAY_TO_SHOW_RESULT, "3");
	}
	
	public boolean setNotification(boolean notification) {
		Editor editor = getEditor();
	    editor.putBoolean(NOTIFICATION, notification);
		return editor.commit();
	}
	
	public boolean ifShowNotification() {
		return getSharedPreferences().getBoolean(NOTIFICATION, true);
	}
	
}
