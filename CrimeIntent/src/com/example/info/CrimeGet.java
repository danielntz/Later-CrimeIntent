package com.example.info;

import java.util.List;

public class CrimeGet {
    //全局对象
	  private  static List<Crime> crimeget;
	  private  static boolean  add = false;
	  private  static boolean  edit = false;
	  private  static boolean  cancel = false;

	public static List<Crime> getCrimeget() {
		return crimeget;
	}

	public static void setCrimeget(List<Crime> crimeget) {
		CrimeGet.crimeget = crimeget;
	}

	public static boolean isAdd() {
		return add;
	}

	public static void setAdd(boolean add) {
		CrimeGet.add = add;
	}

	public static boolean isEdit() {
		return edit;
	}

	public static void setEdit(boolean edit) {
		CrimeGet.edit = edit;
	}

	public static boolean isCancel() {
		return cancel;
	}

	public static void setCancel(boolean cancel) {
		CrimeGet.cancel = cancel;
	}
	 
	
	
	
}
