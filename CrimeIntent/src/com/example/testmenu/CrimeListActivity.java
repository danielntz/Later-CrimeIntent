package com.example.testmenu;

import com.example.testFragment.CrimeListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class CrimeListActivity  extends SingleFragmentActivity{
   
	//����д�õ�onCreate������Ҳ���Ǹ����е�onCreate����
	
	@Override
	public Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

	
}
