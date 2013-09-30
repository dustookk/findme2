package com.gyh.findme2.control;

public class PushManager {
	private static PushManager mPushManager = null;
	
	private PushManager() {
		
	}
	
	public static synchronized PushManager getInstance() {
		if(mPushManager == null) {
			mPushManager = new PushManager();
		}
		return mPushManager;
	}
	
}
