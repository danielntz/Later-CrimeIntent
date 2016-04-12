package com.example.info;

import java.util.List;

public class CrimeGet {
    //全局对象
	  private  static List<Crime> crimeget;

	public static List<Crime> getCrimeget() {
		return crimeget;
	}

	public static void setCrimeget(List<Crime> crimeget) {
		CrimeGet.crimeget = crimeget;
	}
	 
	
	
	
}
