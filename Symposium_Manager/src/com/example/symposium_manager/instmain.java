package com.example.symposium_manager;

import android.os.Bundle;
import android.provider.CalendarContract.EventDays;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class instmain extends Activity {

    private Button ED;
    private Button VR;
    private Button VQ;
    private Button SO;
    private TextView UNTxt;
    
    String UN;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instmain);

        ED=(Button) findViewById(R.id.CmdEVentIM);
        VR=(Button) findViewById(R.id.CmdVRIM);
        VQ=(Button) findViewById(R.id.CmdVQIM);
        SO=(Button) findViewById(R.id.CmdSOIM);

		UNTxt=(TextView) findViewById(R.id.TxtMPPID);
		
		
        Intent i = getIntent();
		if( i.getStringExtra("UN")!=null){
			UN=	i.getStringExtra("UN");			
		}
		UNTxt.setText(UN);

		ED.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(instmain.this,eventreg.class);
                a.putExtra("UN", UN.toString());
                startActivity(a);
            }
           });

		VR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(instmain.this,ListView_ERegs.class);
                a.putExtra("UN", UN.toString());
                startActivity(a);
            }
           });		

		VQ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(instmain.this,mainpage.class);
                startActivity(a);
            }
           });		

		SO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(instmain.this,MainActivity.class);
                startActivity(a);
            }
           });		
    }    
}