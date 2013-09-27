package com.gyh.findme2.control;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.gyh.findme2.util.LogUtil;

public class LocationController {

	private static final int MIN_TIME = 0;
	private static final int MIN_DISTANCE = 0;
	private static final int DURATION= 1000 * 60 * 1;
	private static final float ACCURACY = 50;

	private MyLocationListener gpsLocationListener;
	private MyLocationListener networkLocationListener;
	protected LocationManager mLocationManager;
	protected Location mLocation;
	private OnLocationResultListener mOnLocationResultListener;

	public LocationController(Context mContext) {
		mLocationManager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		gpsLocationListener = new MyLocationListener();
		networkLocationListener = new MyLocationListener();
	}

	public void requstLocation() {
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				MIN_TIME, MIN_DISTANCE, gpsLocationListener);
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE,
				networkLocationListener);
		Timer mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				onFinish();
			}
		}, DURATION);
	}

	protected void chooseBetterLocation(Location location) {
		if (mLocation == null) {
			mLocation = location;
		} else {
			int accuracyDelta = (int) (location.getAccuracy() - mLocation
					.getAccuracy());
			boolean isMoreAccurate = accuracyDelta < 0;
			if (isMoreAccurate) {
				mLocation = location;
			}
		}
		LogUtil.d("Accuracy:" + mLocation.getAccuracy());
		if (mLocation.hasAccuracy() && mLocation.getAccuracy() < ACCURACY) {
			onFinish();
		}
	}

	private void onFinish() {
		LogUtil.d("onfinish");
		mLocationManager.removeUpdates(gpsLocationListener);
		mLocationManager.removeUpdates(networkLocationListener);
		if (mOnLocationResultListener != null) {
			mOnLocationResultListener.onLocationResult(mLocation);
		}
	}

	public void setOnLocationResultListener(
			OnLocationResultListener mOnLocationResultListener) {
		this.mOnLocationResultListener = mOnLocationResultListener;
	}

	protected class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			chooseBetterLocation(location);
			LogUtil.d("onLocationChanged " + location.getAccuracy());
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
			LogUtil.d("onProviderDisabled");
			mLocationManager.removeUpdates(this);
		}
	}

	public interface OnLocationResultListener {
		public void onLocationResult(Location location);
	}
}
