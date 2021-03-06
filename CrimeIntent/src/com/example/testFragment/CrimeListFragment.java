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
 * 罪行列表项,ListFragment自带ListView
 * @author jsjxy
 *
 */
public class CrimeListFragment  extends  ListFragment{
   
       private static final String TAG = null;
	   public   List<Crime> crime_item = new ArrayList<Crime>();
       public   CrimeList  crime_hhh = new CrimeList();   //注意空指针
       public   CrimeListAdapter  adapter  = new CrimeListAdapter();
       public   CrimeListAdapter  adapterlist;
       private  String content  = null;
       public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	getActivity().setTitle("Crime List");
    //	crime_item =  crime_hhh.CrimeList2();  //采用测试数据
    	
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
		
		 //测试本地文件中的数据
    	CrimeGet.setCrimeget(crime_item);    //把当前list中的crime信息保存起来
        adapter = new CrimeListAdapter(crime_item, getContext());
    	setListAdapter(adapter);
    	setHasOptionsMenu(true);   //接受选项菜单方法回调
    }
       //为上下文菜单登记视图
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	View v =  super.onCreateView(inflater, container, savedInstanceState);
    	ListView listview = (ListView)v.findViewById(android.R.id.list);
         registerForContextMenu(listview);   //以上下文菜单的模式来删除记录
    	//标注处为以上下文操作模式来删除记录
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
    //点击当前的列表项，列表项的内容传给编辑界面
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
       //测试CrimeActivity
        //  Intent  crimeactivity = new Intent(getActivity(),CrimeActivity.class);
         // crimeactivity.putExtra("Crime_Id",crime_id );
       //   startActivity(crimeactivity);
      //测试CrimePagerActivity
          Intent crimepagerActivity  = new Intent(getActivity(),CrimePagerActivity.class);
          crimepagerActivity.putExtra("Crime_Id", crime_id);
          startActivity(crimepagerActivity);
       
    } 
    //当编辑框做了改变后，用户列表也跟着更新
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
        adapter.refresh(CrimeGet.getCrimeget());
        //如果添加了，就要重新写入文件
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
    //创建操作栏的选项菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	// TODO Auto-generated method stub
    	super.onCreateOptionsMenu(menu, inflater);
    	inflater.inflate(R.menu.fragment_crime_list, menu);
    }
    //响应菜单选择事件，添加一个新的Crime记录
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			 //添加响应事件
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
			 //菜单项标题和子标题的联动显示
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
    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }
    	//监听上下文菜单选择事件
      @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	 AdapterContextMenuInfo info =(AdapterContextMenuInfo) item.getMenuInfo();
    	 int position = info.position;
    	 CrimeGet.getCrimeget().remove(position);   //刷新列表
    	 adapter.refresh(CrimeGet.getCrimeget());   //刷新列表
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
      //从文件中读取JSON字符串
      public  String CrimeList3() throws IOException, JSONException{
    	 // String  content;
  		FileInputStream  input = getActivity().openFileInput("data.txt");
  		byte  []sentenece = new byte[input.available()];
  		input.read(sentenece);
  		content = new String(sentenece);   
	     return  content;
} 
      
      //解析json数组(没有关键字的json字符串)
  	@SuppressLint("UseValueOf")
	@SuppressWarnings("deprecation")
	public   List<Crime> getListperson(String jsonstring) throws Exception{
  		          
  		     List<Crime> list = new ArrayList<Crime>();
  		   //  JSONObject jsonobject = new JSONObject(jsonstring);
  		     
  		      JSONArray jsonarrty = new JSONArray(jsonstring);  
  		      Log.i(TAG, jsonarrty.length() + "");
  		     for(int i = 0;i < jsonarrty.length(); i++){
  		    //	 JSONObject   jsonobject2 =  jsonarrty.getJSONObject(i);      //json数组中有多个json对象
  		    	String s = String.valueOf(jsonarrty.get(i));
  		    	Log.i(TAG, s);
  		    	JSONObject  jsonobject = new JSONObject(s);
  		    	Crime crime = new Crime();
  		    	String id = jsonobject.getString("JSON_ID");
  		    	crime.setmId(UUID.fromString(id));
  		    	crime.setMmTitle(jsonobject.getString("JSON_TITLE\t"));
  		    	crime.setmSolved(new Boolean(jsonobject.getString("JSON_MSOLVED")));
  		    	crime.setmDate(new Date(jsonobject.getString("JSON_DATE").replace("格林尼治标准时间+0800","")));
  		    	list.add(crime);
  		     }
  		return list;
  	}
    
}
