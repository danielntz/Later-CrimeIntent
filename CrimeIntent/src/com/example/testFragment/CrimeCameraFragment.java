package com.example.testFragment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.testmenu.CrimeCameraActivity;
import com.example.testmenu.R;

public class CrimeCameraFragment  extends Fragment implements OnClickListener{
      
	  protected static final String TAG = null;
	  private Camera mCamera;
	  private SurfaceView  mSurfaceView;
	  private Button  takephoto;
	  private View  mProgressContainner;
	  private Boolean  success = true;
	  private  static  final String  photo_name= "PHOTO";
	  //实现相机回调方法
	  @SuppressWarnings("deprecation")
	  private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
			//展示拍照的过程，也就是那个进度条
			mProgressContainner.setVisibility(View.VISIBLE);
		}
	};
	  
	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			//建立文件名字
			String filename = UUID.randomUUID().toString()+".jpg";
			//把jpeg文件存到磁盘上
			FileOutputStream os = null;
			boolean success  = true;
			try {
				os = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
				try {
					  os.write(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					success = false;
					Log.e(TAG, "error writing to file",e);
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(os != null){
					try {
						os.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						success =false;
						e.printStackTrace();
						Log.e(TAG, "Error closing file " + filename, e);
					}
				}
			}
			if(success){
				Intent i  = new Intent();
				i.putExtra(photo_name, filename);
				getActivity().setResult(Activity.RESULT_OK, i);
			}
			else{
				getActivity().setResult(Activity.RESULT_CANCELED);
			}
			getActivity().finish();
		}
	};
	  
     @SuppressWarnings("deprecation")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	 
    	 View cameraview = inflater.inflate(R.layout.fragment_crime_camera, container, false);
    	 mProgressContainner = cameraview.findViewById(R.id.crime_camera_progressContainer);
    	 mSurfaceView = (SurfaceView)cameraview.findViewById(R.id.crime_camera_surfaceView);
    	 takephoto = (Button)cameraview.findViewById(R.id.crime_camera_takePicture);
    	 mProgressContainner.setVisibility(View.INVISIBLE);
    	 takephoto.setOnClickListener(this);
    	 SurfaceHolder holder  = mSurfaceView.getHolder();  //获取SurfaceHolder实例对象
    	
    	 holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);  //相机预览使用SetType方法
    	 holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				//不能一直在这个界面上预览
				if(mCamera != null){
					mCamera.stopPreview();
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				//告诉相机这是提前预览的区域
				if(mCamera != null){
					try {
						mCamera.setPreviewDisplay(holder);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				if(mCamera == null) return ;
				//当surface的大小改变后，更新相机的预览的尺寸大小
				Camera.Parameters parameters = mCamera.getParameters();
			//	Size s  = null;  //相机预览大小为空
				Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width, s.height);
				//设置图片的尺寸大小需要跟SurfaceView的尺寸一致
				s = getBestSupportedSize(parameters.getSupportedPictureSizes(), width, height);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);
				
				
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					// TODO Auto-generated catch block

                    mCamera.release();   //当相机不能预览时，一定要释放相机资源
                    mCamera = null;
				}
				
				
			}
		});
    	 
    	return cameraview;
    }
     //用来计算最佳预览尺寸
     public Size getBestSupportedSize(List<Size> sizes , int Width , int Height){
    	   
    	 Size bestsize = sizes.get(0);
    	 int largesArea = bestsize.width*bestsize.height;
    	 for(Size s : sizes){
    		 int area = s.width *s.height;
    		 if(area > largesArea){
    			  bestsize = s;
    			  largesArea = area;
    		 }
    	 }
    	 return bestsize;
     }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 switch (v.getId()) {
		case R.id.crime_camera_takePicture:
			// getActivity().finish();
			if(mCamera != null){
				mCamera.takePicture(mShutterCallback, null, mJpegCallback);
			}
			break;
		
		default:
			break;
		}
	}
	//打开相机，首次出现在屏幕上也会调用onResume方法
	@SuppressWarnings("deprecation")
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
			mCamera = android.hardware.Camera.open(0);
		}
		else{
			mCamera = Camera.open();
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mCamera != null){
			mCamera.release();
			mCamera = null;
		}
	}
	
}
