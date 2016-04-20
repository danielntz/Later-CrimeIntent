package com.example.testFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.fuzhu.CrimalIntentJSONSerializer;
import com.example.info.Crime;
import com.example.info.CrimeGet;
import com.example.info.Photo;
import com.example.info.PictureUtils;
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
	private   static  final int REQUEST_PHOTO = 1;
	private   ImageView image_show ;
	private   static final  String DIALOG_IMAGE = "image";
	private    ImageButton takephoto;
	private   static final int Request_Contact = 2 ;
	private   Button   mSuspectbutton;
	private   Button   mReportbutton;
	public   static CrimeFragment newInstance(UUID crimeid){
		Bundle bundle  = new Bundle();
		bundle.putSerializable("Crime_Id", crimeid);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(bundle);
		return fragment;
	}
	//用来把拍的照片显示在IamgeView上
	private void ShowPhoto(){
		Photo p = crimeselect.getmPhoto();
		BitmapDrawable b  = null;
		if( p != null){
			String path = getActivity().getFileStreamPath(p.getMfilename()).getAbsolutePath();
			b = PictureUtils.getScaDrawable(getActivity(), path);
			image_show.setImageDrawable(b);   //把图片显示在IamgeView上
		}
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
		image_show = (ImageView)view.findViewById(R.id.crime_show);  //显示图片
		mSuspectbutton = (Button)view.findViewById(R.id.crime_suspectButton);  //打开联系人应用按钮
		mReportbutton = (Button)view.findViewById(R.id.crime_reportButton);    //打开一个发送短信的应用
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
				CrimeGet.setAdd(false);
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
		//检查设备是否有相机
		PackageManager pm = getActivity().getPackageManager();
		boolean hasAcamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)|| pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1 ||
				Camera.getNumberOfCameras() > 0;
				if( !hasAcamera){
					takephoto.setEnabled(true);
				}
				if(crimeselect.getmSespect()!= null){
					mSuspectbutton.setText(crimeselect.getmSespect());
				}
				buttondate.setOnClickListener(this);
				takephoto.setOnClickListener(this);
				image_show.setOnClickListener(this);
				mSuspectbutton.setOnClickListener(this);
				mReportbutton.setOnClickListener(this);
				//box.setEnabled(false);
				//title.setEnabled(false);

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
		}else if(requestCode == REQUEST_PHOTO){
			//创建一个照片对象添加到Crime类中
			String filename = data.getStringExtra("PHOTO");
			if(filename != null){
				//	Log.i(TAG, "filename +" + filename);
				//把返回的图片信息放到photo类中，之所以添加photo类，而不添加photo属性
				//因为你需要对图片进行处理，对图片操作，这样就方便些
				Photo photo = new Photo(filename);
				crimeselect.setmPhoto(photo);
				ShowPhoto();
				//	Log.i(TAG, "Crime:" + crimeselect.getMmTitle()+"has a photo");
			}
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
		case R.id.menu_item_edit_crime:

			crimeselect.setMmTitle(title.getText().toString());
			crimeselect.setmSolved(box.isChecked());
			box.setEnabled(false);
			title.setEnabled(false);
			CrimeGet.setEdit(true);
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
			//以接受返回值的方式来启动CrimeCameraActivity
			startActivityForResult(i, REQUEST_PHOTO);
			// startActivity(i);
			break;
		case R.id.crime_show:
			Photo p  = crimeselect.getmPhoto();
			if( p == null){
				return ;
			}
			FragmentManager fm1 = getActivity().getSupportFragmentManager();
			String path = getActivity().getFileStreamPath(p.getMfilename()).getAbsolutePath();
			ImageFragment.newInstance(path).show(fm1, DIALOG_IMAGE);
			break;
		case R.id.crime_suspectButton:
			//隐式Intent
			Intent iii  = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(iii, Request_Contact);
			break;
		case R.id.crime_reportButton:
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
		crimeselect.setMmTitle(title.getText().toString());
		crimeselect.setmSolved(box.isChecked());
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ShowPhoto();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.crime_edit, menu);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "暂停");
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "我又回来了");
	}
		
}
