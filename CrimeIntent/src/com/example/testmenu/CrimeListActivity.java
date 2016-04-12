package com.example.testmenu;

import com.example.testFragment.CrimeListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class CrimeListActivity  extends SingleFragmentActivity{
   
	//调用写好的onCreate方法，也就是父类中的onCreate方法
	
	@Override
	public Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

	
}
