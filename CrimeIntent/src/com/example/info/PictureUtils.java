package com.example.info;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;
//这个类是用来处理手机拍摄的图片，使图片缩小。图片使用完后，也需要编写代码清理删除它
public class PictureUtils {

	  public PictureUtils(){
		  
	  }
	
	  //将图片缩放到设备控件默认的显示尺寸
	  public static BitmapDrawable getScaDrawable(Activity a , String path){
		    
		    Display  display = a.getWindowManager().getDefaultDisplay();
		    float  destWidth = display.getWidth();
		    float  destHeight = display.getHeight();
		    //读取磁盘上图片的尺寸及大小
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
	  
	  //卸载图片，清理ImageView的BitmapDrawable
	  public static void cleanIamgeView(ImageView imageview){
		  
		  if(!(imageview.getDrawable() instanceof BitmapDrawable))
			  return ;
		  //
		  BitmapDrawable b = (BitmapDrawable)imageview.getDrawable();
		  b.getBitmap().recycle();
		  imageview.setImageDrawable(null);
	  }
	  
	
}
