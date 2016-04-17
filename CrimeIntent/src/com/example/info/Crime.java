package com.example.info;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
        
	    //添加图片信息
	     private     UUID mId;   //通用唯一标准码
	     private    String   mTitle  ;   //罪行名字
	     private  Date   mDate = new Date();       //日期
	     private   Boolean  mSolved ;             //是否解决
	     private  Photo mPhoto ;       //添加图片信息
	     private  String  mSespect ;   //添加嫌疑人名字
	 
	     public Photo getmPhoto() {
			return mPhoto;
		}
		public void setmPhoto(Photo mPhoto) {
			this.mPhoto = mPhoto;
		}
	//  封装字符串
	  public  JSONObject toJson() throws JSONException{
		      JSONObject  object = new JSONObject();
		      object.put("JSON_ID", mId);
		      object.put("JSON_TITLE	", mTitle);
		      object.put("JSON_DATE", mDate);
		      object.put("JSON_MSOLVED", mSolved);
		      if(mPhoto != null){
		    	    object.put("JSON_FILENAME", mPhoto.toJSON());
		      }
		      object.put("JSON_SUSPECT", mSespect.toString());
		      return object;
	  }
	public String getmSespect() {
		return mSespect;
	}
	public void setmSespect(String mSespect) {
		this.mSespect = mSespect;
	}
	//读json文件
	  public   Crime(JSONObject json) throws JSONException{
		  mId = UUID.fromString(json.getString("JSON_ID"));
		  if(json.has("JSON_TITLE")){
		    mTitle = json.getString("JSON_TITLE");
		    }
		  mSolved = json.getBoolean("JSON_MSOLVED");
		  mDate = new Date(json.getLong("JSON_DATE"));
		 //添加图片信息
		  if(json.has("JSON_FILENAME")){
			      mPhoto = new Photo(json.getJSONObject("JSON_FILENAME"));
	     //嫌疑人信息
			      if(json.has("JSON_SUSPECT")){
			    	  mSespect = json.getString("JSON_SUSPECT");
			      }
		  }
		  	   
	  }
	
	    public   Crime(){
	    	     mId = UUID.randomUUID();   //生成唯一标准码
	    	     mDate = new Date();
	    }

		public String getMmTitle() {
			return mTitle;
		}

		public void setMmTitle(String mmTitle) {
			this.mTitle = mmTitle;
		}

		public Date getmDate() {
			return mDate;
		}

		public void setmDate(Date mDate) {
			this.mDate = mDate;
		}

		public boolean ismSolved() {
			return mSolved;
		}

		public void setmSolved(boolean mSolved) {
			this.mSolved = mSolved;
		}
		public UUID getmId() {
			return mId;
		}
		public void setmId(UUID mId) {
			this.mId = mId;
		}
		
	
	  
	
}
