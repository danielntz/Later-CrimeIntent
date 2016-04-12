package com.example.testmenu;

import com.example.testFragment.CrimeCameraFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

public class CrimeCameraActivity  extends SingleFragmentActivity{
  
	 @Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 super.onCreate(arg0);
	
	}
	
	
	@Override
	public Fragment createFragment() {
		// TODO Auto-generated method stub
		return  new CrimeCameraFragment();
	}

}
