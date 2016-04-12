package com.example.fuzhu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

import com.example.info.Crime;

/**
 *Ӧ�ö�ȡ�ļ����ݵķ�ʽ��ʹ��Context���I/O����
 *��ȡ�ͼ��ر����ļ�
 *filename Ϊ�ڴ��еĵ�ַ����ȫ�ԱȽϺ�
 * @author jsjxy
 *
 */
public class CrimalIntentJSONSerializer {
  
	     private static final int MODE_PRIVATE = 0;
		private   Context context;
	     private    String  filename;   //�ļ��洢�ĵ�ַ
	     
	     public CrimalIntentJSONSerializer(Context   con , String  str){
	    	     con  = this.context;
	    	     filename = str;
	     }
	//������д�뵽�ļ���
	     public   void saveCrimes(List<Crime> array) throws JSONException, IOException{
	    	         
	    	  JSONArray  jsonarray = new JSONArray();
	    	  for(Crime c : array){
	    		      jsonarray.put(c.toJson());
	    	  }
	    	 //��json��ʽ���ַ���д���ļ�
	    	  Writer  writer = null;
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
			}
	     }
	    //��ȡ�ļ��е���Ϣ���ڳ�����������ʱ	 
	     public  List<Crime>  loadCrimes() throws IOException, JSONException{
			   
	    	     List<Crime> crimes = new ArrayList<Crime>();
	    	     BufferedReader reader = null;
	    	     //open and read the file into a bufferBuilder
	    	     try {
					InputStream  in  = context.openFileInput(filename);
					 reader = new BufferedReader(new InputStreamReader(in));
					 StringBuilder  jsonString = new StringBuilder();
					 String line = null;
					 while( (line = reader.readLine()) != null){
						   jsonString.append(line);
					 }
  	 JSONArray  array =(JSONArray)  new JSONTokener(jsonString.toString()).nextValue();
  	 for(int i = 0 ; i < array.length() ; i++){
					  crimes.add(new Crime(array.getJSONObject(i)));
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
	    	 
	    	     return crimes;
	    	 
	     }
	     
	
	
	
}
