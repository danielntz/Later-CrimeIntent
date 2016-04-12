package com.example.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.info.Crime;
import com.example.testFragment.CrimeFragment;

public class CrimeAdapter  extends FragmentStatePagerAdapter{
   
	    private  List<Crime> mCrimes;
	 
	
	public CrimeAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public    CrimeAdapter(FragmentManager  frag, List<Crime> crimes){
		super(frag);
	     this.mCrimes = crimes;
	}
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
          Crime crime = mCrimes.get(position);
          return CrimeFragment.newInstance(crime.getmId());
			 
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCrimes.size();
	}

}
