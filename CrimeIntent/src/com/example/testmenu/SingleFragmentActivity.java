package com.example.testmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
//抽象类复用
public  abstract class SingleFragmentActivity extends FragmentActivity{
    
	    private   FragmentManager  fm ;
	    private   FragmentTransaction  trans ;
	
	
	   public   abstract  Fragment createFragment();   //抽象方法
	
	    @Override
	    protected void onCreate(Bundle arg0) {
	    	// TODO Auto-generated method stub
	    	super.onCreate(arg0);
	    	setContentView(R.layout.activity_main);
	    	fm = getSupportFragmentManager();
	    	Fragment fragment = fm.findFragmentById(R.id.crimefragment);
	    	if(fragment == null){
	    		fragment = createFragment();
	    		fm.beginTransaction().add(R.id.crimefragment, fragment).commit();
	        }
	    	
	    }
	
}
