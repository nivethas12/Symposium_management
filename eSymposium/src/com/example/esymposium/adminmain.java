package com.example.esymposium;

import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class adminmain extends Activity {

    private Button Submit;
    private Button VM;
    private Button Back;
    
    private TextView TxtUName;
    private TextView TxtMbNo;
    private TextView TxtUN;
    private TextView TxtPwd;
    private TextView TxtAddr;
    private TextView TxtDesc;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminmain);
        

        Submit=(Button) findViewById(R.id.CmdSubUser);
        VM=(Button) findViewById(R.id.CmdViewUser);
        Back=(Button) findViewById(R.id.CmdBackUser);
        TxtUName=(TextView) findViewById(R.id.TxtNameUR);
        TxtMbNo=(TextView) findViewById(R.id.TxtMbUR);
        TxtUN=(TextView) findViewById(R.id.TxtUNUR);
        TxtPwd=(TextView) findViewById(R.id.TxtPwdUR);
        TxtAddr=(TextView) findViewById(R.id.TxtAddrUR);
        TxtDesc=(TextView) findViewById(R.id.TxtDescUR);
        
        Submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String alert="";
     			if(TxtUName.getText().toString().trim().length()<=0){
     				alert+="Enter Institution Name\n";
     			}
     			if((TxtMbNo.getText().toString().trim().length()<=0)||(TxtMbNo.getText().toString().trim().length()>10)||(TxtMbNo.getText().toString().trim().length()<10)){
     				alert+="Enter Institution Contact Number\n";
     			}
     			if(TxtUN.getText().toString().trim().length()<=0){
     				alert+="Enter Username\n";
     			}
     			if(TxtPwd.getText().toString().trim().length()<=0){
     				alert+="Enter Password\n";
     			}
     			if(TxtAddr.getText().toString().trim().length()<=0){
     				alert+="Enter Address\n";
     			}
     			if(TxtDesc.getText().toString().trim().length()<=0){
     				alert+="Enter Description\n";
     			}
     			if(!alert.trim().equals("")){
     				Toast.makeText(adminmain.this,""+alert,Toast.LENGTH_LONG).show();     
     	        }
     			else{
     				PushingDataToServer(TxtUName.getText().toString(),TxtMbNo.getText().toString(),TxtUN.getText().toString(),TxtPwd.getText().toString(),TxtAddr.getText().toString(),TxtDesc.getText().toString());
	     			Cleardata();
     			}
			}
		});
        
        Back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent a=new Intent(adminmain.this,MainActivity.class);
                startActivity(a);
			}
		});
        
        VM.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent a=new Intent(adminmain.this,mainpage.class);
                startActivity(a);
			}
		});
        
    }

    private void Cleardata(){
    	TxtUName.setText("");
    	TxtPwd.setText("");
    	TxtMbNo.setText("");
    	TxtUN.setText("");
    	TxtAddr.setText("");
    	TxtDesc.setText("");
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
    
    private void PushingDataToServer(final String UName, final String Mb, final String UN, final String Pwd, final String Addr, final String Desc) {
        class Login extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            String url = "http://arihant2789.ipage.com/Symposium_Details/InstReg.php";
            WebService web = new WebService(url);

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(adminmain.this, "Please Wait...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);   
                loading.dismiss();
                if(s.trim().equalsIgnoreCase("suc")){
                	String Msg="";
                	Msg+="Username : " + UN;
                	Msg+="\n";
                	Msg+="Password : " + Pwd;
                	
                	SendSMS(Mb,Msg);
                	
                	Toast.makeText(adminmain.this,"Institution Details Registered Successfully",Toast.LENGTH_LONG).show();
                }else  if(s.trim().equalsIgnoreCase("und")){
                	Toast.makeText(adminmain.this,"Institution Details Duplicated",Toast.LENGTH_LONG).show();
                }                
                else{
                    Toast.makeText(adminmain.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... param) {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("IName", UName);
                params.put("MbNo", Mb);
                params.put("UN", UN);
                params.put("Pwd", Pwd);
                params.put("Addr", Addr);
                params.put("Descs", Desc);
                String result = web.HttpPostData(url, params);
                return result;
            }
        }

        Login log = new Login();
        log.execute();
    }
}