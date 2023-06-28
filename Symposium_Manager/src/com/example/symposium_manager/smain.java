package com.example.symposium_manager;

import android.os.Bundle;
import android.provider.CalendarContract.EventDays;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class smain extends Activity {

    private Button VE;
    private Button Q;
    private Button SO;
    
    private TextView UNTxt;
    
    String UN;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smain);

        VE=(Button) findViewById(R.id.CmdVESM);
        Q=(Button) findViewById(R.id.CmdQSM);
        SO=(Button) findViewById(R.id.CmdSOSM);

		UNTxt=(TextView) findViewById(R.id.TxtMPPIDSM);
		
		
        Intent i = getIntent();
		if( i.getStringExtra("UN")!=null){
			UN=	i.getStringExtra("UN");			
		}
		UNTxt.setText(UN);

		VE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(smain.this,ListView_Events.class);
                a.putExtra("UN", UN.toString());
                startActivity(a);
            }
           });

		Q.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(smain.this,ListView_EventsStu.class);
                a.putExtra("UN", UN.toString());
                startActivity(a);
            }
           });		


		SO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(smain.this,MainActivity.class);
                startActivity(a);
            }
           });		
    }    
}