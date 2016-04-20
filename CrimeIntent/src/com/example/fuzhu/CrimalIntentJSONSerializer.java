package com.example.fuzhu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.provider.OpenableColumns;
import android.util.Log;

import com.example.info.Crime;

/**
 *应用读取文件最便捷的方式是使用Context类的I/O方法
 *存取和加载本地文件
 *filename 为内存中的地址，安全性比较好
 * @author jsjxy
 *
 */
public class CrimalIntentJSONSerializer {

	private static final int MODE_PRIVATE = 0;
	private static final String TAG = null;
	private   Context context;
	private    String  filename = "data.txt";   //文件存储的地址

	public CrimalIntentJSONSerializer(Context   con , String  str){
		this.context  = con;
		filename = str;
	}

	public CrimalIntentJSONSerializer(){

	}
	//把数据写入到文件中
	public   void saveCrimes(List<Crime> array, String filename) throws JSONException, IOException{

		JSONArray  jsonarray = new JSONArray();
		for(Crime c : array){
			jsonarray.put(c.toJson());
		}
		//把json格式的字符串写入文件
		FileOutputStream   out  = context.openFileOutput(filename, MODE_PRIVATE);
		byte []  content = new byte[1024];
		String  sentence =  jsonarray.toString();
		content = sentence.getBytes();
		int  len = content.length;

		out.write(content, 0, len);
		/*  Writer  writer = null;
	    	  OutputStream out;
			try {
				out = context.openFileOutput(filename, MODE_PRIVATE);
				  writer = new OutputStreamWriter(out);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  try {
				writer.write(jsonarray.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(writer!= null){
					writer.close();
				}
			}*/
	}
	//读取文件中的信息，在程序重新启动时	 
	public  List<Crime>  loadCrimes() throws IOException, JSONException{
     
	 	List<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		//open and read the file into a bufferBuilder
		try {
			InputStream  in  = context.openFileInput("data.txt");
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder  jsonString = new StringBuilder();
			String line = null;
			while( (line = reader.readLine()) != null){
				jsonString.append(line);
			}
			JSONArray  array =(JSONArray)  new JSONTokener(jsonString.toString()).nextValue();
			for(int i = 0 ; i < array.length() ; i++){
				crimes.add(new Crime(array.getJSONObject(i)));
				Log.i(TAG, array.length() +"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(reader != null){
				reader.close();
			}
		}
          Log.i(TAG, "高阔");
		return crimes; 
		
		

	}




}
