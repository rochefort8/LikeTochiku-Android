package com.tochiku85.liketochiku.tochikuji;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.R.id;
import com.tochiku85.liketochiku.R.layout;
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class TochikujiResultActivity extends ActionBarActivity implements OnClickListener {

	int requestCode ;
	Button button1 ;
	private ArrayList<String> imageList ;
	private ArrayList<Integer> playList ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tochikuji_result);

		final android.app.ActionBar actionBar = getActionBar();
		actionBar.setTitle("とーちくじ！");

		button1 = (Button)findViewById(R.id.button_tochikuji_back) ;
		button1.setOnClickListener(this);

		Intent intent = getIntent() ;
		String imagePath = intent.getStringExtra("IMAGE") ;
		Bitmap bmp=BitmapFactory.decodeFile(getFilesDir() + "/" + imagePath) ;
		ImageView oImage = (ImageView)findViewById(id.image_tochikuji_result);
		oImage.setImageBitmap(bmp);
	}

	public void onClick(View v) {

		   switch (v.getId()) {
		   case R.id.button_tochikuji_back:
			   Intent intent = new Intent(this, TochikujiActivity.class) ;
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

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Intent intent;

		switch (item.getItemId()) {
			case R.id.action_home:
				intent = new Intent(this, MainActivity.class) ;
				startActivityForResult(intent, requestCode);
				break;
			case R.id.action_photo_relay:
				intent = new Intent(this, PhotosActivity.class);
				startActivityForResult(intent, requestCode);
				break;

			case R.id.action_settings:
			case R.id.action_others:
				intent = new Intent(this, FunctionListActivity.class);
				startActivityForResult(intent, requestCode);
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true ;
	}

}

