package com.example.info;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;
//����������������ֻ������ͼƬ��ʹͼƬ��С��ͼƬʹ�����Ҳ��Ҫ��д��������ɾ����
public class PictureUtils {

	  public PictureUtils(){
		  
	  }
	
	  //��ͼƬ���ŵ��豸�ؼ�Ĭ�ϵ���ʾ�ߴ�
	  public static BitmapDrawable getScaDrawable(Activity a , String path){
		    
		    Display  display = a.getWindowManager().getDefaultDisplay();
		    float  destWidth = display.getWidth();
		    float  destHeight = display.getHeight();
		    //��ȡ������ͼƬ�ĳߴ缰��С
		    BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(path,options);
		    float srcWidth = options.outWidth;
		    float srcHeight = options.outHeight;
		    int inSampleSize =1;
		    if(srcHeight > destHeight || srcWidth >destWidth){
		    	if(srcWidth > srcHeight){
		    		inSampleSize = Math.round(srcHeight / destHeight);
		    	}else{
		    		inSampleSize = Math.round(srcWidth / destWidth);
		    	}
		    	
		    }
		    options = new BitmapFactory.Options();
		    options.inSampleSize = inSampleSize;
		    Bitmap bitmap = BitmapFactory.decodeFile(path,options);
		    return new BitmapDrawable(a.getResources(), bitmap);
		  
	  }
	  
	  //ж��ͼƬ������ImageView��BitmapDrawable
	  public static void cleanIamgeView(ImageView imageview){
		  
		  if(!(imageview.getDrawable() instanceof BitmapDrawable))
			  return ;
		  //
		  BitmapDrawable b = (BitmapDrawable)imageview.getDrawable();
		  b.getBitmap().recycle();
		  imageview.setImageDrawable(null);
	  }
	  
	
}
