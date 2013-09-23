package com.gyh.findme2.ui;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.gyh.findme2.R;

@SuppressWarnings("deprecation")
public class SettingActivity extends SherlockPreferenceActivity {
	private static final String KEY_RESULT_MODE = "the_way_to_show_result";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle(R.string.setting);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		addPreferencesFromResource(R.xml.preference);
		ListPreference findPreference = (ListPreference) findPreference(KEY_RESULT_MODE);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String title = preference.getTitle().toString();
		if (getString(R.string.trusts).equals(title)) {
		} else if (getString(R.string.history).equals(title)) {
			
		} else if (getString(R.string.donate).equals(title)) {

		} else if (getString(R.string.about).equals(title)) {

		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
