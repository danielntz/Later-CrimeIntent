package com.example.testmenu;

import java.util.UUID;

import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.example.testFragment.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {
   
	
	   
	
	@Override
	public Fragment createFragment() {
		// TODO Auto-generated method stu
		//return new CrimeFragment();
		UUID crimeid = (UUID) getIntent().getSerializableExtra("Crime_Id");
		return CrimeFragment.newInstance(crimeid);
		
	}
  
	 @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
		
	}

	




	
}
