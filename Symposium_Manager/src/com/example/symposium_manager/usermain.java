package com.example.symposium_manager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class usermain extends Activity {

    private Button I;
    private Button S;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        I=(Button) findViewById(R.id.CmdRegisterMA);
        S=(Button) findViewById(R.id.CmdLoginMA);
    

		I.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(usermain.this,instlogin.class);
                startActivity(a);
            }
           });

		S.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a=new Intent(usermain.this,slogin.class);
                startActivity(a);
            }
           });		
    }    
}