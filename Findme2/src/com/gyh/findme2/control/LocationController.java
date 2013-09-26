package com.gyh.findme2.control;

import android.content.Context;
import android.location.LocationManager;

public class LocationController {
	
	private LocationManager mLocationManager;
	
	public LocationController(Context mContext) {
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
	}
	
	public void getLocation() {
		
	}
}
