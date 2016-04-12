package com.example.testmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.example.adapter.CrimeAdapter;
import com.example.info.Crime;
import com.example.info.CrimeGet;
import com.example.info.CrimeList;
//界面布局不一定非要写到xml布局文件中，用代码的方式定义视图层级结构,必须需要一个资源ID
public class CrimePagerActivity  extends FragmentActivity{
     
	  private static final String TAG = null;
	  private   ViewPager  mViewPager;
	  private   CrimeAdapter   adapter;
	  private   List<Crime> crime = new ArrayList<Crime>();
	   @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		init();
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				 Crime crimeselect = crime.get(arg0) ;
		        
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


	public  void init() {
		// TODO Auto-generated method stub
		  //  crime = new CrimeList().CrimeList2();
		      crime = CrimeGet.getCrimeget();
		    adapter = new CrimeAdapter(getSupportFragmentManager(), crime);
		    mViewPager.setAdapter(adapter);
		    UUID crimeID = (UUID)getIntent().getSerializableExtra("Crime_Id");
		 // Toast.makeText(getApplicationContext(), crimeID+"", 0).show();
		    for(int i = 0 ; i < crime.size(); i++){
		    	if(crime.get(i).getmId().equals(crimeID)){
		    		
		    		mViewPager.setCurrentItem(i);
		    		break;
		    	}
		    }
	}
	//为ViewPager添加监听事件
	
}
