package com.gyh.findme2.persist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class DataPref {
	private static DataPref mDataPref;
	private Context mContext;
	private SharedPreferences mPref;
    private Editor mEditor;
    
    private DataPref(Context context) {
    	this.mContext = context;
    }
    
    public static DataPref getInstance(Context context) {
		if(mDataPref == null) {
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

}
