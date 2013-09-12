package com.gyh.findme2.persist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataPref {
	private static final String CONFIG_NAME = "config";
	
	
	
	
	
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
            mPref = mContext.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
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
