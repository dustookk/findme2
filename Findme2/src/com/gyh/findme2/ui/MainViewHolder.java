package com.gyh.findme2.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.gyh.findme2.R;
import com.gyh.findme2.model.NumberInfo;
import com.gyh.findme2.util.LogUtil;
import com.gyh.findme2.util.SysUtil;
import com.gyh.findme2.util.ToastUtil;

public class MainViewHolder implements OnClickListener {

	private Activity mActivity;

	private static final int CONTACT_PICKER_REQUEST = 0;
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
			pickContact();
			break;
		case R.id.btn_get_him:

			break;
		default:
			break;
		}
	}

	/**
	 * pick a contact
	 */
	private void pickContact() {
		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
				Contacts.CONTENT_URI);
		if (SysUtil.isIntentAvailable(mActivity, contactPickerIntent)) {
			mActivity.startActivityForResult(contactPickerIntent,
					CONTACT_PICKER_REQUEST);
		} else {
			// intent not available
			ToastUtil.makeLongToast(mActivity,
					R.string.cannot_launch_the_contact_application);
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK
				&& requestCode == CONTACT_PICKER_REQUEST) {
			Uri resultUrl = data.getData();
			String contactId = resultUrl.getLastPathSegment();
			Cursor cursor = mActivity.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			String phoneNumber;
			String displayName;
			int itype;
			List<NumberInfo> numberList = new ArrayList<NumberInfo>();
			while (cursor.moveToNext()) {
				displayName = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				phoneNumber = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				itype = cursor
						.getInt(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

				boolean isMobile = itype == ContactsContract.CommonDataKinds.Phone.TYPE_MAIN
						|| itype == ContactsContract.CommonDataKinds.Phone.TYPE_HOME
						|| itype == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
						|| itype == ContactsContract.CommonDataKinds.Phone.TYPE_OTHER
						|| itype == ContactsContract.CommonDataKinds.Phone.TYPE_WORK
						|| itype == ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE;
				if (isMobile) {
					NumberInfo numberInfo = new NumberInfo(mActivity,
							phoneNumber, itype);
					numberList.add(numberInfo);
				}
			}
			cursor.close();
		}
	}
}
