package com.example.testFragment;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.testmenu.R;
//��Ƭ���ڽ���
public class DatePickerFragment   extends DialogFragment{

	protected static final String TAG = null;
	private  Date mdate;
	private  DatePicker picker;



	//����AlertDialog.Builder�����������ڶԻ���

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View  v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		picker = (DatePicker)v.findViewById(R.id.date_picker);
		//��ȡCrimeFragment������������ֵ��ʾ��DatePicker����
		mdate = (Date)getArguments().getSerializable("Crime_Date");
		//����Calender����Ȼ���date��������
		Calendar  calendar = Calendar.getInstance();
		calendar.setTime(mdate);
		int year = calendar.get(Calendar.YEAR);
		 int month = calendar.get(Calendar.MONTH);
		 int day = calendar.get(Calendar.DAY_OF_MONTH);
		  picker.init(year, month, day, new OnDateChangedListener() {
			//Date������
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
			//	mdate = new GregorianCalendar(year, month, day).getTime();
				mdate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
				Log.i(TAG, mdate.toString());
				getArguments().putSerializable("Crime_Date", mdate);  //Ϊ��ֹ�豸��ת����date�����д��fragment argument��
			}
		});
		



	//	return new android.app.AlertDialog.Builder(getActivity()).setView(v).setTitle("Date of time").setPositiveButton("OK	", null).create();
		return new android.app.AlertDialog.Builder(getActivity()).setView(v).setTitle("Date of time").setPositiveButton("OK	", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				  sendResult(Activity.RESULT_OK);
			}
		}).create();
	}	

	private void sendResult (int resultCode){
		if(getTargetFragment() == null)
			return ;
		Intent i = new Intent();
		i.putExtra("Crime_Date", mdate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);

	}
	public  static DatePickerFragment newInstance(Date date){

		Bundle bundle = new Bundle();
		bundle.putSerializable("Crime_Date", date);
		DatePickerFragment  fragment = new DatePickerFragment();
		fragment.setArguments(bundle);
		return fragment;

	}



}
