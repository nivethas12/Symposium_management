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
public class ListView_EventsStu extends Activity {

	// For Total Summary
	private static final String URL_TRACK_DTLS = "http://arihant2789.ipage.com/Symposium_Details/ListEvents.php";

	MyTrackAdapter dataAdapter = null;

	private ListView Lst;
	

	private TextView Txt1;
	
    private Button Find;
    private Button Rst;

	String UN;
	String EIDx,ENamex,Venuex,DTx,Instx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lst_events_lst);

		
		Intent i = getIntent();
		if( i.getStringExtra("UN")!=null){
			UN=	i.getStringExtra("UN");			
		}
		
		Lst = (ListView) findViewById(R.id.LstView1);

		getUserDetails();

		
		//Search for Data
		 Lst.setOnItemClickListener(new OnItemClickListener() {
	      	  @Override
	      	  public void onItemClick(AdapterView<?> listView, View view,int position, long id) {
	      		
	      		LayoutInflater factory = LayoutInflater.from(ListView_EventsStu.this);
	      		ListView_Get_Set_Events su = (ListView_Get_Set_Events) listView.getItemAtPosition(position);
				
	      		String EID = su.getEID();
	      		String EName = su.getEName();
	      		String Inst = su.getInst();
		        String CNO = su.getCNO();
		        String Venue = su.getVenue();
		        String DT = su.getDT();	       
		        String Dept = su.getDept();
	      		String Descr = su.getDesc();
	      		
	      		EIDx=EID;
	      		Venuex=Venue;
	      		DTx=DT;
	      		Instx=Inst;
	      		ENamex=EName;
		    	
		    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    	    @Override
		    	    public void onClick(DialogInterface dialog, int choice) {
		    	        switch (choice) {
		    	            case DialogInterface.BUTTON_POSITIVE:

		    	                Intent a=new Intent(ListView_EventsStu.this,StuQry.class);
		    	                a.putExtra("UN", UN.toString());
		    	                a.putExtra("IUN", Instx.toString());
		    	                startActivity(a);
		    	                
							break;
		    	            case DialogInterface.BUTTON_NEGATIVE:
		    	            	Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_LONG).show();
		    	                break;
		    	        }
		    	    }
		    	};

		    	AlertDialog.Builder builder = new AlertDialog.Builder(ListView_EventsStu.this);
		    	builder.setMessage("Are you sure to Send Query to this Event: " + ENamex + "???")
		    	        .setPositiveButton("Yes", dialogClickListener)
		    	        .setNegativeButton("No", dialogClickListener).show();	      	  
	      	  }
	      	  });	
}

private class MyTrackAdapter extends ArrayAdapter<ListView_Get_Set_Events> {
	 private ArrayList<ListView_Get_Set_Events>ListUserDetails;
	
	 public MyTrackAdapter(Context context, int textViewResourceId, ArrayList<ListView_Get_Set_Events> stuView) {
	 super(context, textViewResourceId, stuView);
	 this.ListUserDetails = new ArrayList<ListView_Get_Set_Events>();
	 this.ListUserDetails.addAll(stuView);
	 }
	 private class ViewHolder {
		 TextView TxtEID;
		 TextView TxtEName;
		 TextView TxtInst;
		 TextView TxtCNO;
		 TextView TxtVenue;
		 TextView TxtDT;
		 TextView TxtDept;
		 TextView TxtDesc;		
	 }
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	 ViewHolder holder = null;
	 
	 if (convertView == null) {
	 LayoutInflater vi = (LayoutInflater)getSystemService(
	 Context.LAYOUT_INFLATER_SERVICE);
	 convertView = vi.inflate(R.layout.lst_events_txt, null);
	 holder = new ViewHolder();
	 
	 holder.TxtEID = (TextView) convertView.findViewById(R.id.TxtEIDVSE);
	 holder.TxtEName = (TextView) convertView.findViewById(R.id.TxtENameVSE);
	 holder.TxtCNO = (TextView) convertView.findViewById(R.id.TxtMbVSE);
	 holder.TxtInst = (TextView) convertView.findViewById(R.id.TxtIUNVSE);
	 holder.TxtVenue = (TextView) convertView.findViewById(R.id.TxtVenueCSE);
	 holder.TxtDT = (TextView) convertView.findViewById(R.id.TxtDTVSE);
	 holder.TxtDept = (TextView) convertView.findViewById(R.id.TxtDeptVSE);
	 holder.TxtDesc= (TextView) convertView.findViewById(R.id.TxtDescVSE);
	 
	 convertView.setTag(holder);
 
	 }
	 else {
		 holder = (ViewHolder) convertView.getTag();
	 }
	
	 ListView_Get_Set_Events stu = ListUserDetails.get(position);
	 holder.TxtEID.setText(stu.getEID());
	 holder.TxtEName.setText(stu.getEName());
	 holder.TxtCNO.setText(stu.getCNO());
	 holder.TxtInst.setText(stu.getInst());
	 holder.TxtVenue.setText(stu.getVenue());
	 holder.TxtDT.setText(stu.getDT());
	 holder.TxtDept.setText(stu.getDept());
	 holder.TxtDesc.setText(stu.getDesc());
	
	return convertView;
	 }	 
 }

private void getUserDetails(){
    class GetDetails extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ListView_EventsStu.this,"Fetching Event Details...","Wait...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            showUserDtls(s);
        }

        @Override
        protected String doInBackground(Void... params) {
        	WebService ruc = new WebService(URL_TRACK_DTLS);
            String s = ruc.sendGetRequest();
            return s;
        }
    }
    GetDetails dtls = new GetDetails();
    dtls.execute();
}

private void showUserDtls(String json){
			JSONObject jsonObject = null;
			ArrayList<ListView_Get_Set_Events> List = new ArrayList<ListView_Get_Set_Events>(); 
			
			try {
		        jsonObject = new JSONObject(json);
		        // StudentDetails is a name which is passed from PHP file
		        JSONArray result = jsonObject.getJSONArray("UserDetails");
		         
		        for(int i = 0; i<result.length(); i++){
		        	
		        	JSONObject c = result.getJSONObject(i);
			        
		        	String eid = c.getString("eid");
			        String ename = c.getString("ename");
			        String cno = c.getString("mbno");
			        String inst = c.getString("un");	       
			        String venue = c.getString("venue");     
			        String dt = c.getString("dt");     
			        String dept = c.getString("dept");     
			        String desc = c.getString("desc");
			       
			        ListView_Get_Set_Events stu = new ListView_Get_Set_Events();
		      
			        stu.setEID(eid);
			        stu.setEName(ename);
			        stu.setCNO(cno);
			        stu.setInst(inst);
			        stu.setVenue(venue);
			        stu.setDT(dt);
			        stu.setDept(dept);
			        stu.setDesc(desc);
			        
					List.add(stu);		          
		        }
		    } catch (JSONException e) {
		        e.printStackTrace();
		    }
			dataAdapter = new MyTrackAdapter(this,R.layout.lst_events_txt, List);
			// Assign adapter to ListView
			Lst.setAdapter(dataAdapter);
	}


private void PushingDataToServer(final String EID, final String EName, final String Venue, final String EDT, final String IUN, final String SUN) {
    class Login extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        String url = "http://arihant2789.ipage.com/Symposium_Details/EventReg.php";
        WebService web = new WebService(url);

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ListView_EventsStu.this, "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);   
            loading.dismiss();
            if(s.trim().equalsIgnoreCase("suc")){
            	Toast.makeText(ListView_EventsStu.this,"Applied to Event Successfully. Event Name: "+ EName,Toast.LENGTH_LONG).show();
            }else  if(s.trim().equalsIgnoreCase("und")){
            	Toast.makeText(ListView_EventsStu.this,"Duplicate Identity (or) Already Applied...",Toast.LENGTH_LONG).show();
            }                
            else{
                // Toast.makeText(ListView_Events.this,s,Toast.LENGTH_LONG).show();
            	Toast.makeText(ListView_EventsStu.this,"Applied to Event Successfully. Event Name: "+ EName,Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... param) {
            HashMap<String, String> params = new HashMap<String, String>(1);
            params.put("EID", EID);
            params.put("EName", EName);
            params.put("Venue", Venue);
            params.put("EDT", EDT);
            params.put("IUN", IUN);
            params.put("SUN", SUN);
            String result = web.HttpPostData(url, params);
            return result;
        }
    }
    Login log = new Login();
    log.execute();
}
}