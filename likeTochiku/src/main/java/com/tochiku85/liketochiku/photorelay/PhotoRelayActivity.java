package com.tochiku85.liketochiku.photorelay;

import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.memories.MemoriesSliderActivity;
import com.tochiku85.liketochiku.quiz.QuizMainActivity;
import com.tochiku85.liketochiku.tochikuji.TochikujiActivity;
import com.tochiku85.liketochiku.umanatsu.UmanatsuActivity;
import com.tochiku85.liketochiku.news.NewsActivity;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PhotoRelayActivity extends ActionBarActivity implements OnClickListener {

	ImageButton button1, button2 ;
	int requestCode ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_relay);

        final ActionBar actionBar = getActionBar();
        actionBar.setTitle("画用紙リレー");
		button1 = (ImageButton)findViewById(R.id.button_capture) ;
		button1.setOnClickListener(this);     
	}
   @Override
	public void onClick(View v) {
	   Intent intent ;
	   
	   switch (v.getId()) {
           case R.id.button_capture:
               intent = new Intent(this, CaptureActivity.class);
               startActivityForResult(intent, requestCode);
               break;
       }
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
        Intent intent ;
        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class) ;
                startActivityForResult(intent, requestCode);
                break;

            case R.id.action_others:
                intent = new Intent(this, FunctionListActivity.class) ;
                startActivityForResult(intent, requestCode);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true ;
	}
}
