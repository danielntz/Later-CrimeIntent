package com.example.testFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.adapter.CrimeListAdapter;
import com.example.info.Crime;
import com.example.info.CrimeGet;
import com.example.info.CrimeList;
import com.example.testmenu.CrimeActivity;
import com.example.testmenu.CrimePagerActivity;
import com.example.testmenu.R;
/**
 * �����б���,ListFragment�Դ�ListView
 * @author jsjxy
 *
 */
public class CrimeListFragment  extends  ListFragment{
   
       private static final String TAG = null;
	   public   List<Crime> crime_item = new ArrayList<Crime>();
       public   CrimeList  crime_hhh = new CrimeList();   //ע���ָ��
       public   CrimeListAdapter  adapter  = new CrimeListAdapter();
       public   CrimeListAdapter  adapterlist;
       public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	getActivity().setTitle("Crime List");
    	crime_item =  crime_hhh.CrimeList2();
    	CrimeGet.setCrimeget(crime_item);    //�ѵ�ǰlist�е�crime��Ϣ��������
        adapter = new CrimeListAdapter(crime_item, getContext());
    	setListAdapter(adapter);
    	setHasOptionsMenu(true);   //����ѡ��˵������ص�
    }
    //�����ǰ���б���б�������ݴ����༭����
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	//super.onListItemClick(l, v, position, id);
          String  crime_name = adapter.crime_list.get(position).getMmTitle().toString();
          String  crime_date = adapter.crime_list.get(position).getmDate().toString();
          UUID    crime_id = adapter.crime_list.get(position).getmId();
          Log.i(TAG, crime_name);
          Log.i(TAG, crime_date);
          Log.i(TAG, crime_id +"");
       //����CrimeActivity
        //  Intent  crimeactivity = new Intent(getActivity(),CrimeActivity.class);
         // crimeactivity.putExtra("Crime_Id",crime_id );
       //   startActivity(crimeactivity);
      //����CrimePagerActivity
          Intent crimepagerActivity  = new Intent(getActivity(),CrimePagerActivity.class);
          crimepagerActivity.putExtra("Crime_Id", crime_id);
          startActivity(crimepagerActivity);
       
    } 
    //���༭�����˸ı���û��б�Ҳ���Ÿ���
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
        adapter.refresh(CrimeGet.getCrimeget());
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	// TODO Auto-generated method stub
    	super.onCreateOptionsMenu(menu, inflater);
    	inflater.inflate(R.menu.fragment_crime_list, menu);
    }
    //��Ӧ�˵�ѡ���¼������һ���µ�Crime��¼
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			 //�����Ӧ�¼�
			 Crime crime = new Crime();
			 CrimeGet.getCrimeget().add(crime);
			 crime.setmSolved(false);
			 crime.setmDate(new Date());
			 crime.setMmTitle(null);
			 Intent i = new Intent(getContext(),CrimePagerActivity.class);
			 i.putExtra("Crime_Id",crime.getmId());
		//	 Log.i(TAG, crime.getmId()+"");
			 
			 startActivity(i);
			 return true;	
			 //�˵��������ӱ����������ʾ
		case R.id.menu_item_show_subtitle:
			if(getActivity().getActionBar().getSubtitle() == null){
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
			}
			else{
				 getActivity().getActionBar().setSubtitle(null);
				 item.setTitle(R.string.show_subtitle);
			}
			 
			 
			 return true;
		default:
			return super.onOptionsItemSelected(item);
		}
         
    }
    
    	
}
