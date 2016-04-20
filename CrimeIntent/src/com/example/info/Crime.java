package com.example.info;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
        
	    //���ͼƬ��Ϣ
	     private     UUID mId;   //ͨ��Ψһ��׼��
	     private    String   mTitle  ;   //��������
	     private  Date   mDate = new Date();       //����
	     private   Boolean  mSolved ;             //�Ƿ���
	     private  Photo mPhoto ;       //���ͼƬ��Ϣ
	     private  String  mSespect ;   //�������������
	 
	     public Photo getmPhoto() {
			return mPhoto;
		}
		public void setmPhoto(Photo mPhoto) {
			this.mPhoto = mPhoto;
		}
	//  ��װ�ַ���
	  public  JSONObject toJson() throws JSONException{
		      JSONObject  object = new JSONObject();
		      object.put("JSON_ID", mId);
		      object.put("JSON_TITLE	", mTitle);
		      object.put("JSON_DATE", mDate);
		      object.put("JSON_MSOLVED", mSolved);
		      if(mPhoto != null){
		    	    object.put("JSON_FILENAME", mPhoto.toJSON());
		      }
		      object.put("JSON_SUSPECT", mSespect);
		      return object;
	  }
	public String getmSespect() {
		return mSespect;
	}
	public void setmSespect(String mSespect) {
		this.mSespect = mSespect;
	}
	//��json�ļ�
	  public   Crime(JSONObject json) throws JSONException{
		  mId = UUID.fromString(json.getString("JSON_ID"));
		  if(json.has("JSON_TITLE")){
		    mTitle = json.getString("JSON_TITLE");
		    }
		  mSolved = json.getBoolean("JSON_MSOLVED");
		  mDate = new Date(json.getLong("JSON_DATE"));
		 //���ͼƬ��Ϣ
		  if(json.has("JSON_FILENAME")){
			      mPhoto = new Photo(json.getJSONObject("JSON_FILENAME"));
	     //��������Ϣ
			      if(json.has("JSON_SUSPECT")){
			    	  mSespect = json.getString("JSON_SUSPECT");
			      }
		  }
		  	   
	  }
	
	    public   Crime(){
	    	     mId = UUID.randomUUID();   //����Ψһ��׼��
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
