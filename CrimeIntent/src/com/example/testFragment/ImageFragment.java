package com.example.testFragment;

import com.example.info.PictureUtils;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment  extends DialogFragment{

	  private ImageView view;
	  public static final String extra_path = "image_path";
	 
	  public static ImageFragment newInstance(String imagePath){
		   
		      Bundle args = new Bundle();
		      args.putSerializable(extra_path, imagePath);
		      ImageFragment fragment = new ImageFragment();
              fragment.setArguments(args);
              fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
              return fragment;
	 
	  }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		  view = new ImageView(getActivity());
		  String path = (String)getArguments().getSerializable(extra_path);
		  BitmapDrawable image = PictureUtils.getScaDrawable(getActivity(), path);
		  view.setImageDrawable(image);
		  return view;
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		PictureUtils.cleanIamgeView(view);   //Ïú»ÙÍ¼Æ¬
	}
}
