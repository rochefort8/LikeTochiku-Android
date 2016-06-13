package com.tochiku85.liketochiku.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;

public class SalesActivity extends ActionBarActivity {

    private WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("記念品通販");

        webView = (WebView) findViewById(R.id.sales_webview);
        webView.loadUrl("http://tokyo2015.tochiku.com/goods.html");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_photo_relay:
                intent = new Intent(this, PhotosActivity.class);
                startActivity(intent);
                break;

            case R.id.action_settings:
            case R.id.action_others:
                intent = new Intent(this, FunctionListActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
