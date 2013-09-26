package com.gyh.findme2.model;

import com.gyh.findme2.R;

import android.content.Context;
import android.provider.ContactsContract;

public class NumberInfo {
	private String name;
	private String number;
	private String type;

	public NumberInfo(Context context,String name,String numberStr, int itype) {
		setName(name);
		setNumber(numberStr);
		setType(context, itype);
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setNumber(String numberStr) {
		this.number = numberStr.replaceAll("/[^0-9]/", "");
	}

	public void setType(Context context, int itype) {
		switch (itype) {
		case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
			this.type = context.getString(R.string.type_main);
			break;
		case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
			this.type = context.getString(R.string.type_home);
			break;
		case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
			this.type = context.getString(R.string.type_mobile);
			break;
		case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
			this.type = context.getString(R.string.type_other);
			break;
		case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
			this.type = context.getString(R.string.type_work);
			break;
		case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
			this.type = context.getString(R.string.type_work_mobile);
			break;
		}

	}
	@Override
	public String toString() {
		return this.number + " " + this.type;
	}
}
