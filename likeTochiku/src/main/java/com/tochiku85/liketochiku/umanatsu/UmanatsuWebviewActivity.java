package com.tochiku85.liketochiku.umanatsu;

import java.util.ArrayList;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.R.id;
import com.tochiku85.liketochiku.R.layout;
import com.tochiku85.liketochiku.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UmanatsuWebviewActivity extends ActionBarActivity {

	String[] urllist = {
			"http://isoshigi.com/",
			"http://www.bakerymiyan.com/",
			"http://erakanpodo.jp/",
			"http://noon-nakajima.com/"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_umanatsu_webview);
		
		Intent intent = getIntent() ;
		int item_no = intent.getIntExtra("ITEM_NO",0) ;
		if (3 < item_no) { 
			Log.v("N",Integer.toString(item_no));
			item_no = 0 ;
		}
		WebView  webView = (WebView)findViewById(R.id.umanatsu_webview) ;
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(urllist[item_no]);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.umanatsu_webview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
