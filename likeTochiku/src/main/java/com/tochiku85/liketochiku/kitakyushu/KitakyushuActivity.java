package com.tochiku85.liketochiku.kitakyushu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.contents.LikeTochikuContents;
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.news.NewsContents;
import com.tochiku85.liketochiku.news.NewsDetailActivity;
import com.tochiku85.liketochiku.news.NewsListAdapter;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

public class KitakyushuActivity extends ActionBarActivity  implements LikeTochikuContents.LikeTochikuContentsCallback {

    private KitakyushuContents mKitakyushuContents ;
    private Context mContext;

    private KitakyushuListAdapter kitakyushuListAdapter ;
    private ListView lvItems;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mKitakyushuContents.loadData();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitakyushu);
        mContext = this;

        final android.app.ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("いいね！東筑応援団");

        ParseApplication parseApplication = (ParseApplication)this.getApplication();
        mKitakyushuContents = parseApplication.getKitakyushuContents() ;
        mKitakyushuContents.setCallback(this);
        mKitakyushuContents.loadData() ;

        kitakyushuListAdapter  = new KitakyushuListAdapter(this, mKitakyushuContents) ;
        lvItems = (ListView) findViewById(R.id.kitakyushu_listView);
        lvItems.setAdapter(kitakyushuListAdapter);
        kitakyushuListAdapter.notifyDataSetChanged();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(mContext, KitakyushuWebsiteActivity.class);
                intent.putExtra("KITAKYUSHU_TITLE",   mKitakyushuContents.getTitle(position)) ;
                intent.putExtra("KITAKYUSHU_LINK",   mKitakyushuContents.getLink(position)) ;
                startActivity(intent);
            }
        });

        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    public void callbackMethod(){
        Log.d("callbackMethod", "called");
        kitakyushuListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void callbackMethod(Object param) {
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
