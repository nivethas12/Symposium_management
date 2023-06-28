package com.example.symposium_manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SetJavaScriptEnabled")
public class StuQry extends Activity {
 
	private ListView Lst;
	

	private TextView TxtUN;
	private TextView TxtSName;
	private TextView TxtCge;
	private TextView TxtDept;
	private TextView TxtRNO;
	private TextView TxtCNO;
	private TextView TxtICNO;
	private TextView TxtQ;
	
    private Button Sub;
    private Button Back;

	String UN;
	String IUN;
	String MbNox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendq);

        Sub=(Button) findViewById(R.id.CmdSendQ);
        Back=(Button) findViewById(R.id.CmdBackQ);
        TxtUN=(TextView) findViewById(R.id.TxtUNQ);
        TxtSName=(TextView) findViewById(R.id.TxtSNameQ);
        TxtCge=(TextView) findViewById(R.id.TxtCgeQ);
        TxtDept=(TextView) findViewById(R.id.TxtDeptQ);
        TxtRNO=(TextView) findViewById(R.id.TxtRegNoQ);
        TxtCNO=(TextView) findViewById(R.id.TxtCNOQ);
        TxtICNO=(TextView) findViewById(R.id.TxtICNOQ);
        TxtQ=(TextView) findViewById(R.id.TxtQryQ);
		
		Intent i = getIntent();
		if( i.getStringExtra("UN")!=null){
			UN=	i.getStringExtra("UN");			
		}
		

		Intent i1 = getIntent();
		if( i1.getStringExtra("IUN")!=null){
			IUN=	i1.getStringExtra("IUN");			
		}
		
		
		
		
		
		Lst = (ListView) findViewById(R.id.LstView1VR);
		GetUserDetails1(UN);
		GetUserDetails11(IUN);
		
		  Sub.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String alert="";
	     			if(TxtQ.getText().toString().trim().length()<=0){
	     				alert+="Enter Query to Sent\n";
	     			}
	     			if((TxtICNO.getText().toString().trim().length()<=0)||(TxtICNO.getText().toString().trim().length()>10)||(TxtICNO.getText().toString().trim().length()<10)){
	     				alert+="Invalid Institution Mobile Number\n";
	     			}
	     			if(!alert.trim().equals("")){
	     				Toast.makeText(StuQry.this,""+alert,Toast.LENGTH_LONG).show();     
	     	        }
	     			else{
	     				String Msg="";
	     				Msg+="Name: " + TxtSName.getText();
	     				Msg+="\n";
	     				Msg+="College: " + TxtCge.getText();
	     				Msg+="\n";
	     				Msg+="Dept: " + TxtDept.getText();
	     				Msg+="\n";
	     				Msg+="Contact: " + TxtCNO.getText();
	     				Msg+="\n";
	     				Msg+="Query: " + TxtQ.getText();
	     				
	     				SendSMS(TxtICNO.getText().toString(),Msg);
	     				
	     				// Toast.makeText(getApplicationContext(), "Msg  : " + Msg, Toast.LENGTH_LONG).show();
	     				//Toast.makeText(getApplicationContext(), "Name  : " + TxtSName.getText().toString(), Toast.LENGTH_LONG).show();
	     				 
	     				
	     				PushingDataToServer(TxtSName.getText().toString(),TxtCge.getText().toString(),TxtDept.getText().toString(),TxtRNO.getText().toString(),TxtCNO.getText().toString(),IUN,TxtQ.getText().toString());
	     				Sub.setEnabled(false);
	     			}
				}
			});
	        
	        Back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent a=new Intent(StuQry.this,smain.class);
	                a.putExtra("UN", UN.toString());
	                startActivity(a);
				}
			});
}

private void GetUserDetails1(final String UNm) {
    class Login extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        String url = "http://arihant2789.ipage.com/Symposium_Details/getStu.php?UN="+UNm;
        WebService web = new WebService(url);


        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(StuQry.this, "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            showUserDtls(s);
        }

        @Override
        protected String doInBackground(String... param) {
            HashMap<String, String> params = new HashMap<String, String>(1);
            params.put("UN", UNm);
            String result = web.HttpPostData(url, params);
            return result;
        }
    }
    
    Login log = new Login();
    log.execute();
}

private void SendSMS(String MbNo, String Msg)
{
	 try {
			SmsManager smsManager = SmsManager.getDefault();
		    smsManager.sendTextMessage(MbNo, null, Msg, null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent to " + MbNo, Toast.LENGTH_LONG).show();
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
		
		}
}
private void showUserDtls(String json){
			JSONObject jsonObject = null;
			ArrayList<ListView_Get_Set_EReg> List = new ArrayList<ListView_Get_Set_EReg>(); 
			
			try {
		        jsonObject = new JSONObject(json);
		        // StudentDetails is a name which is passed from PHP file
		        JSONArray result = jsonObject.getJSONArray("UserDetailsx");
		         
		        for(int i = 0; i<result.length(); i++){
		        	
		        	JSONObject c = result.getJSONObject(i);
			        
		        	String sname = c.getString("sname");
			        String cge = c.getString("cge"); 
			        String dept = c.getString("dept");
			        String regno = c.getString("regno");	      
			        String cno = c.getString("cno");

						TxtUN.setText(UN);
						TxtSName.setText(sname);
						TxtCge.setText(cge);
						TxtDept.setText(dept);
						TxtRNO.setText(regno);
						TxtCNO.setText(cno);
				        
		        }
		    } catch (JSONException e) {
		        e.printStackTrace();
		        Toast.makeText(getApplicationContext(), "ERROR  : " + e.getMessage(), Toast.LENGTH_LONG).show();
		    }			
	}


private void GetUserDetails11(final String UNm) {
    class Login extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        String url = "http://arihant2789.ipage.com/Symposium_Details/getInst.php?UN="+IUN;
        WebService web = new WebService(url);


        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(StuQry.this, "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            showUserDtls1(s);
        }

        @Override
        protected String doInBackground(String... param) {
            HashMap<String, String> params = new HashMap<String, String>(1);
            params.put("IUN", IUN);
            String result = web.HttpPostData(url, params);
            return result;
        }
    }
    
    Login log = new Login();
    log.execute();
}
private void showUserDtls1(String json){
			JSONObject jsonObject = null;
			ArrayList<ListView_Get_Set_EReg> List = new ArrayList<ListView_Get_Set_EReg>(); 
			
			try {
		        jsonObject = new JSONObject(json);
		        // StudentDetails is a name which is passed from PHP file
		        JSONArray result = jsonObject.getJSONArray("UserDetailsx");
		         
		        for(int i = 0; i<result.length(); i++){
		        	
		        	JSONObject c = result.getJSONObject(i);
			              
			        String cno = c.getString("cno");

						TxtICNO.setText(cno);
				        
		        }
		    } catch (JSONException e) {
		        e.printStackTrace();
		        Toast.makeText(getApplicationContext(), "ERROR  : " + e.getMessage(), Toast.LENGTH_LONG).show();
		    }			
	}

private void PushingDataToServer(final String SName, final String Cge, final String Dept,final String RegNo, final String CNO, final String IUN, final String Qry) {
    class Login extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        String url = "http://arihant2789.ipage.com/Symposium_Details/qry.php";
        WebService web = new WebService(url);

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(StuQry.this, "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);   
            loading.dismiss();
            if(s.trim().equalsIgnoreCase("suc")){
            	Toast.makeText(StuQry.this,"Query Sent Successfully",Toast.LENGTH_LONG).show();
            }else  if(s.trim().equalsIgnoreCase("und")){
            	Toast.makeText(StuQry.this,"Invalid Details Found",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(StuQry.this,s,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... param) {
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("SName", SName);
            params.put("Cge", Cge);
            params.put("Dept", Dept);
            params.put("RNO", RegNo);
            params.put("CNO", CNO);
            params.put("IUN", IUN);
            params.put("Qry", Qry);
            String result = web.HttpPostData(url, params);
            return result;
        }
    }
    Login log = new Login();
    log.execute();
}
}