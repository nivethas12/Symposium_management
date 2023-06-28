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

public class eventreg extends Activity {

    private Button Submit;
    private Button Back;

    private TextView TxtUN;
    private TextView TxtEName;
    private TextView TxtMbNo;
    private TextView TxtVenue;
    private TextView TxtDT;
    private TextView TxtDept;
    private TextView TxtDesc;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdetails);
        

        Submit=(Button) findViewById(R.id.CmdSubER);
        Back=(Button) findViewById(R.id.CmdBackER);
        TxtUN=(TextView) findViewById(R.id.TxtUNER);
        TxtEName=(TextView) findViewById(R.id.TxtENameER);
        TxtMbNo=(TextView) findViewById(R.id.TxtMbER);
        TxtVenue=(TextView) findViewById(R.id.TxtVenueER);
        TxtDT=(TextView) findViewById(R.id.TxtDTER);
        TxtDept=(TextView) findViewById(R.id.TxtDeptER);
        TxtDesc=(TextView) findViewById(R.id.TxtDescER);

        Intent i = getIntent();
		if(i.getStringExtra("UN")!=null){
			TxtUN.setText(i.getStringExtra("UN"));			
		}
	
		
        Submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String alert="";
     			if(TxtEName.getText().toString().trim().length()<=0){
     				alert+="Enter Event Name\n";
     			}
     			if((TxtMbNo.getText().toString().trim().length()<=0)||(TxtMbNo.getText().toString().trim().length()>10)||(TxtMbNo.getText().toString().trim().length()<10)){
     				alert+="Enter Mobile Number\n";
     			}
     			if(TxtVenue.getText().toString().trim().length()<=0){
     				alert+="Enter Venue\n";
     			}
     			if(TxtUN.getText().toString().trim().length()<=0){
     				alert+="Invalid Institution Username\n";
     			}
     			if(TxtDT.getText().toString().trim().length()<=0){
     				alert+="Select Correct Event Date\n";
     			}
     			if(TxtDept.getText().toString().trim().length()<=0){
     				alert+="Enter Department\n";
     			}
     			if(TxtDesc.getText().toString().trim().length()<=0){
     				alert+="Enter Description\n";
     			}
     			if(!alert.trim().equals("")){
     				Toast.makeText(eventreg.this,""+alert,Toast.LENGTH_LONG).show();     
     	        }
     			else{
     				PushingDataToServer(TxtUN.getText().toString(),TxtEName.getText().toString(),TxtMbNo.getText().toString(),TxtVenue.getText().toString(),TxtDT.getText().toString(),TxtDept.getText().toString(),TxtDesc.getText().toString());
	     			Cleardata();
     			}
			}
		});
        
        Back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent a=new Intent(eventreg.this,instmain.class);
                a.putExtra("UN", TxtUN.toString());
                startActivity(a);
			}
		});
    }

    private void Cleardata(){
    	TxtUN.setText("");
    	TxtEName.setText("");
    	TxtMbNo.setText("");
    	TxtVenue.setText("");
    	TxtDT.setText("");
    	TxtDept.setText("");
    	TxtDesc.setText("");
    }

    private void PushingDataToServer(final String UN,final String EName, final String Mb, final String Venue, final String DT, final String Dept, final String Desc) {
        class Login extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            String url = "http://arihant2789.ipage.com/Symposium_Details/eventdtls.php";
            WebService web = new WebService(url);

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(eventreg.this, "Please Wait...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);   
                loading.dismiss();
                if(s.trim().equalsIgnoreCase("suc")){
                	Toast.makeText(eventreg.this,"Event Details Registered Successfully",Toast.LENGTH_LONG).show();
                }else  if(s.trim().equalsIgnoreCase("und")){
                	Toast.makeText(eventreg.this,"Event Details Duplicated",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(eventreg.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... param) {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("UN", UN);
                params.put("EName", EName);
                params.put("MbNo", Mb);
                params.put("Venue", Venue);
                params.put("DT", DT);
                params.put("Dept", Dept);
                params.put("Descr", Desc);
                String result = web.HttpPostData(url, params);
                return result;
            }
        }
        Login log = new Login();
        log.execute();
    }
}