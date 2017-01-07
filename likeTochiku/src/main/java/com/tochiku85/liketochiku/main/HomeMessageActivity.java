package com.tochiku85.liketochiku.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

import java.util.ArrayList;

public class HomeMessageActivity extends ActionBarActivity {

    private ParseApplication parseApplication ;
    private MessageContents mMessageContents ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_message);
        final android.app.ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("当番期より");

        parseApplication = (ParseApplication)this.getApplication();
        mMessageContents = parseApplication.getMessageContents() ;

        TextView titleText = (TextView)findViewById(R.id.text_message_title) ;
        titleText.setText(mMessageContents.getTitle(0));

        TextView messageText = (TextView)findViewById(R.id.text_message) ;
        messageText.setText(mMessageContents.getMessage(0));

        ImageView imageView = (ImageView)findViewById(R.id.message_image);
        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/" + mMessageContents.getImagePath(0)) ;
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
