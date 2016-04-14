package com.example.info;

import org.json.JSONException;
import org.json.JSONObject;

//图片类
public class Photo {


	private 	String mfilename;   //图片文件的名字
	public Photo(){

	}
	
	public  Photo( String filename){
		 this.mfilename = filename;
	}
	public String getMfilename() {
		return mfilename;
	}
	
     //封装图片信息成json
    public  JSONObject toJSON() throws JSONException{
    	
    	JSONObject json = new JSONObject();
    	json.put("JSON_FILENAME", mfilename);
    	return json;
    	
    }
   //读json文件
   public Photo(JSONObject json) throws JSONException{
	   mfilename = json.getString("JSON_FILENAME");
   }
    

}
