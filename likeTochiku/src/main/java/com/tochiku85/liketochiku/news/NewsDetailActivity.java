package com.tochiku85.liketochiku.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

import java.util.ArrayList;

public class NewsDetailActivity extends ActionBarActivity implements View.OnClickListener {

    private Button button_news_detail_back ;
    private Button button_news_detail_link ;
    private NewsListAdapter newsListAdapter ;
    private ListView lvNewsItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("ニュース");

        String title = getIntent().getStringExtra("NEWS_TITLE") ;
        String article = getIntent().getStringExtra("NEWS_ARTICLE") ;
        String imagePath = getIntent().getStringExtra("NEWS_IMAGE_PATH") ;

        TextView titleView = (TextView)findViewById(R.id.text_news_title);
        TextView contentView = (TextView)findViewById(R.id.text_news_contents);

        titleView.setText(title) ;
        contentView.setText(article) ;

        ImageView imageView = (ImageView)findViewById(R.id.news_detail_image);
        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/" + imagePath) ;
        imageView.setImageBitmap(bitmap);
        /*
        button_news_detail_back = (Button) findViewById(R.id.button_news_detail_back);
        button_news_detail_back.setOnClickListener(this) ;
        */
    }

    public void onClick(View v) {
        Intent intent;
        int answer = 0;

        switch (v.getId()) {
            /*
            case R.id.button_news_detail_back:
                intent = new Intent(this, NewsActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.button_news_detail_link:
                break ;
                */
            default :
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
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
                startActivityForResult(intent, 0);
                break;
            case R.id.action_photo_relay:
                intent = new Intent(this, PhotosActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.action_settings:
            case R.id.action_others:
                intent = new Intent(this, FunctionListActivity.class);
                startActivityForResult(intent, 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true ;
    }
}
