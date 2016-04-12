package com.example.testFragment;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.info.Crime;
import com.example.info.CrimeGet;
import com.example.testmenu.CrimeCameraActivity;
import com.example.testmenu.R;
/**
 * 罪行编辑框
 * @author lenovo
 *
 */
public class CrimeFragment  extends Fragment implements android.view.View.OnClickListener  { 

	private static final String DIALOG_DATE = "date";
	private static final String TAG = null;
	private   Button   buttondate;
	private   Date  mDate;  
	private   Crime  crimeselect;
	private   CheckBox  box;
	private   EditText  title;
	private   static  final int REQUEST_DATE = 0;
	private    ImageButton takephoto;
	public   static CrimeFragment newInstance(UUID crimeid){
		Bundle bundle  = new Bundle();
		bundle.putSerializable("Crime_Id", crimeid);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View   view  =  inflater.inflate(R.layout.crime, container, false);
		buttondate =(Button) view.findViewById(R.id.crime_date);
		takephoto = (ImageButton)view.findViewById(R.id.crime_take);
		title = (EditText)view.findViewById(R.id.crime_name);
		box = (CheckBox)view.findViewById(R.id.crime_solve);
		//1获得点击列表项的UUID 2获得重新编辑框的UUID
		UUID crimeid = (UUID)getArguments().getSerializable("Crime_Id");
		Log.i(TAG, crimeid+"");
		//获得当前点击列表项上的Crime信息
		for(int i= 0 ; i < CrimeGet.getCrimeget().size();i++) {
			if(CrimeGet.getCrimeget().get(i).getmId().equals(crimeid)){
				crimeselect = CrimeGet.getCrimeget().get(i);
				break;
			}
			//对编辑框编辑后，Crime集合要进行更改，根据UUID唯一标示符

		}
		//实现层级式导航、1 元数据 在配置文件中加入父activity属性 ，不采用intent方式
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			if(NavUtils.getParentActivityName(getActivity()) != null){
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		title.setText(crimeselect.getMmTitle());
		box.setChecked(crimeselect.ismSolved());
		buttondate.setText(crimeselect.getmDate().toString().replace("格林尼治标准时间", ""));
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		//   crimeselect.setMmTitle(title.getText().toString());   
		//   crimeselect.setmDate(new Date());
		//   crimeselect.setmSolved(box.isChecked());
		//   CrimeGet.setCrimeget(CrimeGet.getCrimeget());
		buttondate.setOnClickListener(this);
		takephoto.setOnClickListener(this);
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);

	}
	//响应日期对话框的内容,设置相应Crime的日期，并且刷新日期按钮
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != Activity.RESULT_OK) return;
		if(requestCode ==REQUEST_DATE){
			Date date = (Date) data.getSerializableExtra("Crime_Date");
			crimeselect.setmDate(date);
			buttondate.setText(crimeselect.getmDate().toString().replace("格林尼治标准时间", ""));
		}
	} 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity()) != null){
				  NavUtils.navigateUpFromSameTask(getActivity());   //导航到父Activity
			}
			 return true;
			

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.crime_date:
			FragmentManager fm = getActivity().getSupportFragmentManager();
			//   DatePickerFragment   dialgo = new DatePickerFragment();
			DatePickerFragment  dialog  =  DatePickerFragment.newInstance(crimeselect.getmDate());
			dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);  //设置Fragment的目标
			dialog.show(fm, DIALOG_DATE);
			break;
		case R.id.crime_take:
			Intent i  = new Intent(getContext(),CrimeCameraActivity.class);
			 startActivity(i);
			 break;
		default:
			break;
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "stop");
		//crimeselect.setmDate((Date)buttondate.getText());
		crimeselect.setMmTitle(title.getText().toString());
		crimeselect.setmSolved(box.isChecked());
	} 

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "Destroy");
	}

}
