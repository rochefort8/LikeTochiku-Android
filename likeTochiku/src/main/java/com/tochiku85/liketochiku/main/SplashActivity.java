package com.tochiku85.liketochiku.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.login.LoginActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent =  new Intent(SplashActivity.this, LoginActivity.class) ;
               intent.putExtra("LOGIN",true) ;
               startActivity(intent);
               finish();
            }
//        BeerDataDownloader beerDataDownloader = new BeerDataDownloader(getBaseContext()) ;
//		beerDataDownloader.Get() ;
        },3000);
    }
}