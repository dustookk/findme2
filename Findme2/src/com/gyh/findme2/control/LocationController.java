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
	private static final int DURATION = 1000 * 60 * 5;
	private static final long TWO_MINUTES = 1000 * 60 * 2;
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

	protected Location chooseBetterLocation(Location currentBestLocation,
			Location location) {

		if (!location.hasAccuracy()) {
			return currentBestLocation;
		}

		if (currentBestLocation == null) {
			return location;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isNewer = timeDelta > 0;
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;

		if (isSignificantlyNewer) {
			return location;
		}

		if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		boolean isFromSameProvider = isSameProvider(currentBestLocation,
				location);

		if (isMoreAccurate) {
			return location;
		} else if (isNewer && !isLessAccurate) {
			return location;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return location;
		}

		return currentBestLocation;
	}

	private boolean isSameProvider(Location loc1, Location loc2) {
		if (loc1 == null) {
			return loc2 == null;
		}
		String loc1Provider = loc1.getProvider();
		String loc2Provider = loc2.getProvider();
		if (loc1Provider == null) {
			return loc2Provider == null;
		}

		return loc1Provider.equals(loc2Provider);
	}

	protected void checkIfReady(Location lcoation) {
		if (lcoation != null && lcoation.hasAccuracy()) {
			float accuracy = lcoation.getAccuracy();
			if (accuracy < ACCURACY) {
				onFinish();
			}
		}
	}

	private void onFinish() {
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
			mLocation = chooseBetterLocation(mLocation, location);
			checkIfReady(mLocation);
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
