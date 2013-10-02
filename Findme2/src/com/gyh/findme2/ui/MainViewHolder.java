package com.gyh.findme2.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.gyh.findme2.R;
import com.gyh.findme2.model.NumberInfo;
import com.gyh.findme2.service.SendLocationService;
import com.gyh.findme2.util.SysUtil;

public class MainViewHolder extends ViewHolder {

	private static final int CONTACT_PICKER_REQUEST = 0;
	private static final String[] COUNTRIES = new String[] { "18623123421",
			"15923328165" };

	private AutoCompleteTextView inputView;

	public MainViewHolder(MainActivity mMainActivity) {
		super(mMainActivity);
	}

	@Override
	public View getView() {
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mianView = layoutInflater.inflate(R.layout.layout_activity_main,
				null);
		mianView.findViewById(R.id.iv_add_contact).setOnClickListener(this);
		mianView.findViewById(R.id.btn_get_him).setOnClickListener(this);
		inputView = (AutoCompleteTextView) mianView
				.findViewById(R.id.et_main_input);
		inputView.setThreshold(1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.textview_list_item, COUNTRIES);
		inputView.setAdapter(adapter);
		return mianView;
	}

	@Override
	public void performClick(final View view) {
		switch (view.getId()) {
		case R.id.iv_add_contact:
			pickContact();
			break;
		case R.id.btn_get_him:
			Intent intent = new Intent(getActivity(), SendLocationService.class);
			getActivity().startService(intent);
			makeLongToast(getActivity(), "please keep your eyes on the notification bar");
			view.setVisibility(View.INVISIBLE);
			postDelayed(new Runnable() {
				@Override
				public void run() {
					view.setVisibility(View.VISIBLE);
				}
			}, 3000);
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
		if (SysUtil.isIntentAvailable(getActivity(), contactPickerIntent)) {
			getActivity().startActivityForResult(contactPickerIntent,
					CONTACT_PICKER_REQUEST);
		} else {
			// intent not available
			makeLongToast(getActivity(),
					R.string.cannot_launch_the_contact_application);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK
				&& requestCode == CONTACT_PICKER_REQUEST) {
			Uri resultUrl = data.getData();
			String contactId = resultUrl.getLastPathSegment();
			Cursor cursor = getActivity().getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			String phoneNumber;
			String displayName = null;
			int itype;
			final List<NumberInfo> numberList = new ArrayList<NumberInfo>();
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
					NumberInfo numberInfo = new NumberInfo(getActivity(),
							displayName, phoneNumber, itype);
					numberList.add(numberInfo);
				}
			}
			cursor.close();

			if (numberList.size() != 0) {
				createNumberDialog(displayName, numberList);
			} else {
				makeShortToast(getActivity(),
						R.string.no_phone_number_found);
			}

		}
	}

	private void createNumberDialog(String displayName,
			final List<NumberInfo> numberList) {
		final Dialog numberDialog = new Dialog(getActivity());
		ListView numberListView = new ListView(getActivity());
		numberListView.setAdapter(new ArrayAdapter<NumberInfo>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, numberList));
		numberListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = numberList.get(position).getNumber();
				inputView.setText(text);
				inputView.setSelection(text.length());
				numberDialog.dismiss();
				inputView.requestFocus();
			}
		});
		numberDialog.setContentView(numberListView);
		if (displayName != null) {
			numberDialog.setTitle(displayName);
		}
		numberDialog.show();
	}
}
