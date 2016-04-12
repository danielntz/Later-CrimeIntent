package com.example.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.info.Crime;
import com.example.testmenu.R;

public class CrimeListAdapter  extends BaseAdapter{
   
	 public     List<Crime> crime_list;
	 private   Context context;
	 private    LayoutInflater  inflater;
	 private   ViewHolder  viewholder = null;
	
	  
	 public  CrimeListAdapter(){
		 
	 }
	 
	 static class ViewHolder
	 {
		   public  TextView  crime_name;
		   public  TextView  crime_date;
		   public   CheckBox   box;
	 }
	 public   CrimeListAdapter(List <Crime> crime_list,Context context) {
		      this.crime_list = crime_list;    //装在数据的集合
		      this.context = context;
		      this.inflater = LayoutInflater.from(context);
	 }
	 
	
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.crime_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return crime_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
	      if(convertView == null){
	    	  viewholder = new ViewHolder();
	    	  convertView = inflater.inflate(R.layout.crime_item, null);
	    	  viewholder.crime_name = (TextView) convertView.findViewById(R.id.crime_name);
	    	  viewholder.crime_date = (TextView) convertView.findViewById(R.id.crime_date);
	    	  viewholder.box = (CheckBox) convertView.findViewById(R.id.true_false);
	         convertView.setTag(viewholder);
	      }
	      else{
	    	    viewholder =(ViewHolder) convertView.getTag();
	      }
	      viewholder.crime_name.setText(this.crime_list.get(position).getMmTitle());
	      viewholder.crime_date.setText(this.crime_list.get(position).getmDate().toString().replace("格林尼治标准时间", ""));
		  viewholder.box.setChecked(this.crime_list.get(position).ismSolved());
		return convertView;
		
	}
	
	public  void refresh(List<Crime> hhh){
		  this.crime_list = hhh;
		  notifyDataSetChanged();
	}
	
}
