package com.example.esymposium;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

@SuppressLint("SetJavaScriptEnabled")
public class mainpage extends Activity {

	  private WebView b1;
	  
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		 
		
        b1=(WebView)findViewById(R.id.webView1);

       
        WebSettings webSettings = b1.getSettings();
        webSettings.setBuiltInZoomControls(true);
        b1.getSettings().setJavaScriptEnabled(true);
        b1.setWebViewClient(new Callback());
        
         b1.setWebChromeClient(new WebChromeClient());

        
        
         b1.loadUrl("http://arihant2789.ipage.com/Symposium_Details/viewweb.php");
        	       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE. 

	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            return (false);
	        }
	    }
}
