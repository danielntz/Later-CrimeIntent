package com.example.info;

import org.json.JSONException;
import org.json.JSONObject;

//ͼƬ��
public class Photo {


	private 	String mfilename;   //ͼƬ�ļ�������
	public Photo(){

	}
	public String getMfilename() {
		return mfilename;
	}
	public void setMfilename(String mfilename) {
		this.mfilename = mfilename;
	}
     //��װͼƬ��Ϣ��json
    public  JSONObject toJSON() throws JSONException{
    	
    	JSONObject json = new JSONObject();
    	json.put("JSON_FILENAME", mfilename);
    	return json;
    	
    }
   //��json�ļ�
   public Photo(JSONObject json) throws JSONException{
	   mfilename = json.getString("JSON_FILENAME");
   }
    

}
