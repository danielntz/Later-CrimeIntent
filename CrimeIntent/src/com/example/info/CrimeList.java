package com.example.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CrimeList {
      
	  private  List<HashMap<String,Object>> collect = new ArrayList<HashMap<String,Object>>();
	  private   List<Crime> crimecollect = new ArrayList<Crime>();
	  public CrimeList(){
		    
		
		  
       }
	  //测试数据
	  public  List<HashMap<String,Object>> CrimeList1(){
		     
		    for(int i = 0 ; i < 12 ; i++){
		       HashMap<String, Object> map = new HashMap<String, Object>();
		       map.put("Crime_Name", "#"+ i);
		       map.put("Crime_Date", "#" + "Thus Oct 18 10:30:27");
		       collect.add(map);
		    }
		  return collect;
		  
	  }
	  //Crime数据
	  public  List<Crime> CrimeList2(){
		    
		    for(int i = 0 ; i < 13 ; i++){
		    	  Crime crime = new Crime();
		    	  crime.setMmTitle("#" + i);
		    	  crime.setmDate(new Date());
		    	
		    	  if(i % 2 == 0)
		    	  crime.setmSolved(false);
		    	  else
		    	  crime.setmSolved(true);
		    	  crimecollect.add(crime);
		    }
		  return  crimecollect;
	  }
	  
}
