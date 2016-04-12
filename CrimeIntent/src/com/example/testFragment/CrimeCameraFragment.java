package com.example.testFragment;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
      
	  private Camera mCamera;
	  private SurfaceView  mSurfaceView;
	  private Button  takephoto;
	  private View  mProgressContainner;
	
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
			 getActivity().finish();
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
