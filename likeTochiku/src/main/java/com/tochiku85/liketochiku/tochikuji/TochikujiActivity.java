package com.tochiku85.liketochiku.tochikuji;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.R.id;
import com.tochiku85.liketochiku.R.layout;
import com.tochiku85.liketochiku.contents.LikeTochikuContents;
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.news.NewsContents;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TochikujiActivity extends ActionBarActivity implements OnClickListener,LikeTochikuContents.LikeTochikuContentsCallback {

	int requestCode ;
	Button button1, button2 ;
	private ParseApplication parseApplication ;
	private TochikujiContents mTochikujiContents ;
	private ArrayList<String> imageList ;
	private ArrayList<Integer> playList ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tochikuji);

		button1 = (Button)findViewById(R.id.button_tochikuji_start) ;
		button1.setOnClickListener(this);
		imageList = new ArrayList<String>() ;
		playList = new ArrayList<Integer>() ;

		final android.app.ActionBar actionBar = getActionBar();
		actionBar.setTitle("とーちくじ！");

        mTochikujiContents = new TochikujiContents(this) ;
        mTochikujiContents.setCallback(this);
        mTochikujiContents.loadData() ;


	}

    public void setList() {

        int totalCount =  mTochikujiContents.getObjectCount() ;

		for (int i = 0;i < totalCount;i++) {
			String filePath = mTochikujiContents.getFilePath(i,"image") ;
			imageList.add(filePath);
		}
        int l = imageList.size() ;
        int ll[] = new int[l] ;

        for (int i = 0;i < l;i++) {
            ll[i] = i ;
        }
        int _l = l ;
        Random rand = new Random() ;

        for (int i = 0;i < l;i++) {
            int n = rand.nextInt(_l) ;
            Integer ii = new Integer(ll[n]) ;
			Log.d("JJJ",Integer.toString(n) + " / " + Integer.toString(ii)) ;
            playList.add(ii) ;
            for (int j = n;j < l-1;j++) {
                ll[j] = ll[j+1] ;
            }
            _l-- ;

        }

        for (int i = 0;i < playList.size();i++) {
            Log.d("III",Integer.toString(playList.get(i))) ;
        }

        for (int i = 0;i < imageList.size();i++) {
            Log.d("CCC",imageList.get(i)) ;
        }
    }

    @Override
    public void callbackMethod() {
        Log.d("Quiz:callbackMethod", "called");
    }

    @Override
    public void callbackMethod(Object param) {
    }

	public void onClick(View v) {

		Intent intent ;

		   switch (v.getId()) {
		   case R.id.button_tochikuji_start:
               setList() ;

			   intent = new Intent(this, TochikujiSlideActivity.class) ;
			   intent.putExtra("IMAGELIST",imageList) ;
			   intent.putExtra("PLAYLIST",playList) ;
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

