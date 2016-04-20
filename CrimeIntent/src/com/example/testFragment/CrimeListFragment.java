package com.example.testFragment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import com.example.adapter.CrimeAdapter;
import com.example.adapter.CrimeListAdapter;
import com.example.fuzhu.CrimalIntentJSONSerializer;
import com.example.info.Crime;
import com.example.info.CrimeGet;
import com.example.info.CrimeList;
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
       private  String content  = null;
       public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	getActivity().setTitle("Crime List");
    //	crime_item =  crime_hhh.CrimeList2();  //���ò�������
    	
		//	crime_item  = crime_hhh.CrimeList3();
    		
    		
				try {
					content = CrimeList3();
					crime_item = getListperson(content);
					
					for(int i = 0 ; i< crime_item.size(); i++){
						  Crime crime = crime_item.get(i);
						  Log.i(TAG, crime.getMmTitle());
						 
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		 //���Ա����ļ��е�����
    	CrimeGet.setCrimeget(crime_item);    //�ѵ�ǰlist�е�crime��Ϣ��������
        adapter = new CrimeListAdapter(crime_item, getContext());
    	setListAdapter(adapter);
    	setHasOptionsMenu(true);   //����ѡ��˵������ص�
    }
       //Ϊ�����Ĳ˵��Ǽ���ͼ
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	View v =  super.onCreateView(inflater, container, savedInstanceState);
    	ListView listview = (ListView)v.findViewById(android.R.id.list);
         registerForContextMenu(listview);   //�������Ĳ˵���ģʽ��ɾ����¼
    	//��ע��Ϊ�������Ĳ���ģʽ��ɾ����¼
         /*	if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
    		  registerForContextMenu(listview);  
    	}
    	else{
    		listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE_MODAL);
    		listview.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void onDestroyActionMode(ActionMode arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					  MenuInflater  inflater = mode.getMenuInflater();
					  inflater.inflate(R.menu.crime_list_item_context, menu);
					  return true;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					// TODO Auto-generated method stub
					switch (item.getItemId()) {
					case R.id.menu_item_delete_crime:
						 CrimeListAdapter adapter = (CrimeListAdapter)getListAdapter();
						 for(int i = adapter.getCount() -1 ; i>=0 ; i--){
							 if(getListView().isItemChecked(i)){
								 CrimeGet.getCrimeget().remove(adapter.getItem(i));
								 
							 }
						 }
						 mode.finish();
						 adapter.refresh(CrimeGet.getCrimeget());
						 return true;
						

					default:
						return false;
					}
				}
				
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					// TODO Auto-generated method stub
					
				}
			});
    	}*/
    	 
    	return v;
    	
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
        //�������ˣ���Ҫ����д���ļ�
        if(CrimeGet.isAdd()){
        	try {
  			  CrimalIntentJSONSerializer  crimewrite = new CrimalIntentJSONSerializer(getActivity(), "data.txt");
  			   crimewrite.saveCrimes(CrimeGet.getCrimeget(),"data.txt");
  			   CrimeGet.setAdd(false);
  		} catch (JSONException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		};
  	 }else if(CrimeGet.isEdit()){
  		try {
			  CrimalIntentJSONSerializer  crimewrite = new CrimalIntentJSONSerializer(getActivity(), "data.txt");
			   crimewrite.saveCrimes(CrimeGet.getCrimeget(),"data.txt");
			   CrimeGet.setAdd(false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
            CrimeGet.setEdit(false);
        }else if(CrimeGet.isCancel()){
        	try {
    			  CrimalIntentJSONSerializer  crimewrite = new CrimalIntentJSONSerializer(getActivity(), "data.txt");
    			   crimewrite.saveCrimes(CrimeGet.getCrimeget(),"data.txt");
    			   CrimeGet.setAdd(false);
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		};
        	CrimeGet.setCancel(false);
        }
        
        
        
       // Log.i(TAG, "sdfsdf");
    }
    //������������ѡ��˵�
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
			 CrimeGet.setAdd(true);
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
    //���������Ĳ˵�
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }
    	//���������Ĳ˵�ѡ���¼�
      @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	 AdapterContextMenuInfo info =(AdapterContextMenuInfo) item.getMenuInfo();
    	 int position = info.position;
    	 CrimeGet.getCrimeget().remove(position);   //ˢ���б�
    	 adapter.refresh(CrimeGet.getCrimeget());   //ˢ���б�
    	 try {
			  CrimalIntentJSONSerializer  crimewrite = new CrimalIntentJSONSerializer(getActivity(), "data.txt");
			   crimewrite.saveCrimes(CrimeGet.getCrimeget(),"data.txt");
			   CrimeGet.setAdd(false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    	 return super.onContextItemSelected(item);
    }
      //���ļ��ж�ȡJSON�ַ���
      public  String CrimeList3() throws IOException, JSONException{
    	 // String  content;
  		FileInputStream  input = getActivity().openFileInput("data.txt");
  		byte  []sentenece = new byte[input.available()];
  		input.read(sentenece);
  		content = new String(sentenece);   
	     return  content;
} 
      
      //����json����(û�йؼ��ֵ�json�ַ���)
  	@SuppressLint("UseValueOf")
	@SuppressWarnings("deprecation")
	public   List<Crime> getListperson(String jsonstring) throws Exception{
  		          
  		     List<Crime> list = new ArrayList<Crime>();
  		   //  JSONObject jsonobject = new JSONObject(jsonstring);
  		     
  		      JSONArray jsonarrty = new JSONArray(jsonstring);  
  		      Log.i(TAG, jsonarrty.length() + "");
  		     for(int i = 0;i < jsonarrty.length(); i++){
  		    //	 JSONObject   jsonobject2 =  jsonarrty.getJSONObject(i);      //json�������ж��json����
  		    	String s = String.valueOf(jsonarrty.get(i));
  		    	Log.i(TAG, s);
  		    	JSONObject  jsonobject = new JSONObject(s);
  		    	Crime crime = new Crime();
  		    	String id = jsonobject.getString("JSON_ID");
  		    	crime.setmId(UUID.fromString(id));
  		    	crime.setMmTitle(jsonobject.getString("JSON_TITLE\t"));
  		    	crime.setmSolved(new Boolean(jsonobject.getString("JSON_MSOLVED")));
  		    	crime.setmDate(new Date(jsonobject.getString("JSON_DATE").replace("�������α�׼ʱ��+0800","")));
  		    	list.add(crime);
  		     }
  		return list;
  	}
    
}
