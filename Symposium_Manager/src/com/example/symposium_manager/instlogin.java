package com.example.symposium_manager;

import java.util.HashMap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class instlogin extends Activity {
	
	private TextView Pwd;
	private TextView UN;
	
    private Button submit;

    private Button SE; 
    private Button SrE; 
    
    String alert="";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instlogin);

        submit=(Button) findViewById(R.id.CmdLoginiL);
        
        Pwd=(TextView) findViewById(R.id.TxtPasswordIL);
		UN=(TextView) findViewById(R.id.TxtUNIL);
		
		 submit.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	String alert="";
	                if(UN.getText().toString().trim().length()<=0){
	                    alert+="Enter Username\n";
	                }
	                if(Pwd.getText().toString().trim().length()<=0){
	                    alert+="Enter Password\n";
	                }
	                if(!alert.trim().equals("")){
	                    AlertDialog.Builder b= new AlertDialog.Builder(instlogin.this);
	                    b.setTitle("Cannot Proceed");
	                    b.setMessage(alert);
	                    b.setIcon(R.drawable.ic_launcher);
	                    b.show();
	                }
	                else {
	                	UserLogin(UN.getText().toString(),Pwd.getText().toString());
	                }
	            }
	            });
    }

    private void UserLogin(final String UN, final String Pwd) {
        class Login extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            String url = "http://arihant2789.ipage.com/Symposium_Details/iLogin.php";
            WebService web = new WebService(url);

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(instlogin.this, "Please Wait...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                
                if(s.trim().equalsIgnoreCase("Success")){
                	//Toast.makeText(UserLogin.this,"Successfully Logged In",Toast.LENGTH_LONG).show();
                	Intent i = new Intent(instlogin.this,instmain.class);
                    i.putExtra("UN", UN.toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }else  if(s.trim().equalsIgnoreCase("Fail")){
                	 AlertDialog.Builder b= new AlertDialog.Builder(instlogin.this);
                     b.setTitle("Cannot Proceed");
                     b.setMessage("Invalid Authentication");
                     b.setIcon(R.drawable.ic_launcher);
                     b.show();
                }else{
                    Toast.makeText(instlogin.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... param) {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("UN", UN);
                params.put("Pwd", Pwd);
                String result = web.HttpPostData(url, params);
                return result;
            }
        }
        Login log = new Login();
        log.execute();
    }
}