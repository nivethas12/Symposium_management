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
public class ListView_ERegs extends Activity {

	MyTrackAdapter dataAdapter = null;

	private ListView Lst;
	

	private TextView Txt1;
	
    private Button Find;
    private Button Rst;

	String UN;
	String MbNox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lst_reg_lst);

		
		Intent i = getIntent();
		if( i.getStringExtra("UN")!=null){
			UN=	i.getStringExtra("UN");			
		}
		
		Lst = (ListView) findViewById(R.id.LstView1VR);
		GetUserDetails1(UN);
}


private class MyTrackAdapter extends ArrayAdapter<ListView_Get_Set_EReg> {
	 private ArrayList<ListView_Get_Set_EReg>ListUserDetails;
	
	 public MyTrackAdapter(Context context, int textViewResourceId, ArrayList<ListView_Get_Set_EReg> stuView) {
	 super(context, textViewResourceId, stuView);
	 this.ListUserDetails = new ArrayList<ListView_Get_Set_EReg>();
	 this.ListUserDetails.addAll(stuView);
	 }
	 private class ViewHolder {
		 TextView TxtEID;
		 TextView TxtEName;
		 TextView TxtInst;
		 TextView TxtVenue;
		 TextView TxtDT;
		 TextView TxtSUN;
		 TextView TxtSName;
		 TextView TxtDept;
		 TextView TxtCNO;
		 TextView TxtAON;		
	 }
	 
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	 ViewHolder holder = null;
	 
	 if (convertView == null) {
	 LayoutInflater vi = (LayoutInflater)getSystemService(
	 Context.LAYOUT_INFLATER_SERVICE);
	 convertView = vi.inflate(R.layout.lst_reg_txt, null);
	 holder = new ViewHolder();
	 
	 holder.TxtEID = (TextView) convertView.findViewById(R.id.TxtEIDVSEVR);
	 holder.TxtEName = (TextView) convertView.findViewById(R.id.TxtENameVSEVR);
	 holder.TxtInst = (TextView) convertView.findViewById(R.id.TxtIUNVSEVR);
	 holder.TxtVenue = (TextView) convertView.findViewById(R.id.TxtVenueCSEVR);
	 holder.TxtDT = (TextView) convertView.findViewById(R.id.TxtDTVSEVR);
	 holder.TxtSUN = (TextView) convertView.findViewById(R.id.TxtSUNVSEVR);
	 holder.TxtSName = (TextView) convertView.findViewById(R.id.TxtSNameVSEVR);
	 holder.TxtDept = (TextView) convertView.findViewById(R.id.TxtDeptVSEVR);
	 holder.TxtCNO = (TextView) convertView.findViewById(R.id.TxtCNOVSEVR);
	 holder.TxtAON = (TextView) convertView.findViewById(R.id.TxtAONVSEVR);
	 convertView.setTag(holder); 
	 }
	 else {
		 holder = (ViewHolder) convertView.getTag();
	 }
	
	 ListView_Get_Set_EReg stu = ListUserDetails.get(position);
	 holder.TxtEID.setText(stu.getEID());
	 holder.TxtEName.setText(stu.getEName());
	 holder.TxtInst.setText(stu.getInst());
	 holder.TxtVenue.setText(stu.getVenue());
	 holder.TxtDT.setText(stu.getDT());
	 holder.TxtSUN.setText(stu.getSUN());
	 holder.TxtSName.setText(stu.getSName());
	 holder.TxtDept.setText(stu.getDept());
	 holder.TxtCNO.setText(stu.getCNO());
	 holder.TxtAON.setText(stu.getAON());
	
	return convertView;
	}	 
}

private void GetUserDetails1(final String UNm) {
    class Login extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        String url = "http://arihant2789.ipage.com/Symposium_Details/ListERegs.php?UN="+UNm;
        WebService web = new WebService(url);


        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ListView_ERegs.this, "Please Wait...", null, true, true);
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
private void showUserDtls(String json){
			JSONObject jsonObject = null;
			ArrayList<ListView_Get_Set_EReg> List = new ArrayList<ListView_Get_Set_EReg>(); 
			
			try {
		        jsonObject = new JSONObject(json);
		        // StudentDetails is a name which is passed from PHP file
		        JSONArray result = jsonObject.getJSONArray("UserDetailsx");
		         
		        for(int i = 0; i<result.length(); i++){
		        	
		        	JSONObject c = result.getJSONObject(i);
			        
		        	String eid = c.getString("eid");
			        String ename = c.getString("ename"); 
			        String venue = c.getString("venue");
			        String dt = c.getString("dt");	      
			        String inst = c.getString("iun");       
			        String sun = c.getString("sun");  
			        String aon = c.getString("aon");          
			        String sname = c.getString("sname");       
			        String dept = c.getString("dept");        
			        String cno = c.getString("cno"); 
			       
			        ListView_Get_Set_EReg stu = new ListView_Get_Set_EReg();
		      
			        stu.setEID(eid);
			        stu.setEName(ename);
			        stu.setVenue(venue);
			        stu.setDT(dt);
			        stu.setInst(inst);
			        stu.setSUN(sun);
			        stu.setAON(aon);
			        stu.setSName(sname);
			        stu.setDept(dept);
			        stu.setCNO(cno);
			        List.add(stu);
					//   Toast.makeText(getApplicationContext(), "UN  : " + UN, Toast.LENGTH_LONG).show();				        
		        }
		    } catch (JSONException e) {
		        e.printStackTrace();
		        Toast.makeText(getApplicationContext(), "ERROR  : " + e.getMessage(), Toast.LENGTH_LONG).show();
		    }
			dataAdapter = new MyTrackAdapter(this,R.layout.lst_reg_txt, List);
			// Assign adapter to ListView
			Lst.setAdapter(dataAdapter);
	}
}