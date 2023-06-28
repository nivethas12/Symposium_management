package com.example.symposium_manager;

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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class stureg extends Activity {

    private Button Submit;
    private Button Back;
    
    private TextView TxtUName;
    private TextView TxtMbNo;
    private TextView TxtUN;
    private TextView TxtPwd;
    private TextView TxtCge;
    private TextView TxtRNO;
    private TextView TxtDept;
    private TextView TxtAddr;
    private TextView TxtCity;
    private TextView TxtState;
    private TextView TxtCountry;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stureg);
        

        Submit=(Button) findViewById(R.id.CmdSubUser);
        Back=(Button) findViewById(R.id.CmdBackUser);
        TxtUName=(TextView) findViewById(R.id.TxtNameUR);
        TxtMbNo=(TextView) findViewById(R.id.TxtMbUR);
        TxtUN=(TextView) findViewById(R.id.TxtUNUR);
        TxtPwd=(TextView) findViewById(R.id.TxtPwdUR);
        TxtCge=(TextView) findViewById(R.id.TxtCgeUR);
        TxtRNO=(TextView) findViewById(R.id.TxtRegNoUR);
        TxtDept=(TextView) findViewById(R.id.TxtDeptUR);
        TxtAddr=(TextView) findViewById(R.id.TxtAddrUR);
        TxtCity=(TextView) findViewById(R.id.TxtCityUR);
        TxtState=(TextView) findViewById(R.id.TxtStateUR);
        TxtCountry=(TextView) findViewById(R.id.TxtCountryUR);
        
        Submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String alert="";
     			if(TxtUName.getText().toString().trim().length()<=0){
     				alert+="Enter Name\n";
     			}
     			if((TxtMbNo.getText().toString().trim().length()<=0)||(TxtMbNo.getText().toString().trim().length()>10)||(TxtMbNo.getText().toString().trim().length()<10)){
     				alert+="Enter Mobile Number\n";
     			}
     			if(TxtPwd.getText().toString().trim().length()<=0){
     				alert+="Enter Password\n";
     			}
     			if(TxtUN.getText().toString().trim().length()<=0){
     				alert+="Enter Username\n";
     			}
     			if(TxtCge.getText().toString().trim().length()<=0){
     				alert+="Enter College Name\n";
     			}
     			if(TxtRNO.getText().toString().trim().length()<=0){
     				alert+="Enter Registration Number\n";
     			}
     			if(TxtDept.getText().toString().trim().length()<=0){
     				alert+="Enter Department\n";
     			}
     			if(TxtAddr.getText().toString().trim().length()<=0){
     				alert+="Enter Address\n";
     			}
     			if(TxtCity.getText().toString().trim().length()<=0){
     				alert+="Enter City\n";
     			}
     			if(TxtState.getText().toString().trim().length()<=0){
     				alert+="Enter State\n";
     			}
     			if(TxtCountry.getText().toString().trim().length()<=0){
     				alert+="Enter Country\n";
     			}
     			if(!alert.trim().equals("")){
     				Toast.makeText(stureg.this,""+alert,Toast.LENGTH_LONG).show();     
     	        }
     			else{
     				PushingDataToServer(TxtUName.getText().toString(),TxtMbNo.getText().toString(),TxtUN.getText().toString(),TxtPwd.getText().toString(),TxtCge.getText().toString(),TxtRNO.getText().toString(),TxtDept.getText().toString(),TxtAddr.getText().toString(),TxtCity.getText().toString(),TxtState.getText().toString(),TxtCountry.getText().toString());
	     			Cleardata();
     			}
			}
		});
        
        Back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent a=new Intent(stureg.this,slogin.class);
                startActivity(a);
			}
		});
    }

    private void Cleardata(){
    	TxtUName.setText("");
    	TxtUN.setText("");
    	TxtPwd.setText("");
    	TxtMbNo.setText("");
    	TxtCge.setText("");
    	TxtRNO.setText("");
    	TxtDept.setText("");
    	TxtAddr.setText("");
    	TxtCity.setText("");
    	TxtState.setText("");
    	TxtCountry.setText("");
    }

    private void PushingDataToServer(final String UName, final String Mb, final String UN,final String Pwd, final String Cge, final String RNO, final String Dept, final String Addr, final String City, final String State, final String Country) {
        class Login extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            String url = "http://arihant2789.ipage.com/Symposium_Details/sreg.php";
            WebService web = new WebService(url);

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(stureg.this, "Please Wait...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);   
                loading.dismiss();
                if(s.trim().equalsIgnoreCase("suc")){
                	Toast.makeText(stureg.this,"Student Details Registered Successfully",Toast.LENGTH_LONG).show();
                }else  if(s.trim().equalsIgnoreCase("und")){
                	Toast.makeText(stureg.this,"Student Details Duplicated",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(stureg.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... param) {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("SName", UName);
                params.put("MbNo", Mb);
                params.put("UN", UN);
                params.put("Pwd", Pwd);
                params.put("Cge", Cge);
                params.put("RegNo", RNO);
                params.put("Dept", Dept);
                params.put("Addr", Addr);
                params.put("City", City);
                params.put("State", State);
                params.put("Country", Country);
                String result = web.HttpPostData(url, params);
                return result;
            }
        }
        Login log = new Login();
        log.execute();
    }
}