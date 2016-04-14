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
	  //ʵ������ص�����
	  @SuppressWarnings("deprecation")
	  private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
			//չʾ���յĹ��̣�Ҳ�����Ǹ�������
			mProgressContainner.setVisibility(View.VISIBLE);
		}
	};
	  
	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			//�����ļ�����
			String filename = UUID.randomUUID().toString()+".jpg";
			//��jpeg�ļ��浽������
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
    	 SurfaceHolder holder  = mSurfaceView.getHolder();  //��ȡSurfaceHolderʵ������
    	
    	 holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);  //���Ԥ��ʹ��SetType����
    	 holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				//����һֱ�����������Ԥ��
				if(mCamera != null){
					mCamera.stopPreview();
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				//�������������ǰԤ��������
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
				//��surface�Ĵ�С�ı�󣬸��������Ԥ���ĳߴ��С
				Camera.Parameters parameters = mCamera.getParameters();
			//	Size s  = null;  //���Ԥ����СΪ��
				Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width, s.height);
				//����ͼƬ�ĳߴ��С��Ҫ��SurfaceView�ĳߴ�һ��
				s = getBestSupportedSize(parameters.getSupportedPictureSizes(), width, height);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);
				
				
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					// TODO Auto-generated catch block

                    mCamera.release();   //���������Ԥ��ʱ��һ��Ҫ�ͷ������Դ
                    mCamera = null;
				}
				
				
			}
		});
    	 
    	return cameraview;
    }
     //�����������Ԥ���ߴ�
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
	//��������״γ�������Ļ��Ҳ�����onResume����
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
