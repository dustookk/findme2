package com.gyh.findme2.ui;

import java.util.Set;

import android.app.Dialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gyh.findme2.R;
import com.gyh.findme2.model.NumberInfo;

public class PickNumberDialogHolder {
	private Context mContext;
	private String title;
	private Set<NumberInfo> numberSet;
	public PickNumberDialogHolder(Context context,String title,Set<NumberInfo> numberSet) {
		this.mContext = context;
		this.title = title;
		this.numberSet = numberSet;
	}
	
	
	public Dialog getDialog() {
		Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.dialog_pick_number);
		ListView list = (ListView) dialog.findViewById(android.R.id.list);
		return null;
	}
	
	
	
	class pickNumberAdapter extends ArrayAdapter<NumberInfo>{

		public pickNumberAdapter(Context context, int resource) {
			super(context, resource);
		}
		
		
	}
}
