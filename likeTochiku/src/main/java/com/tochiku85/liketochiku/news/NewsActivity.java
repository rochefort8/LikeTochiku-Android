package com.tochiku85.liketochiku.news;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.R.anim;
import com.tochiku85.liketochiku.R.id;
import com.tochiku85.liketochiku.R.layout;
import com.tochiku85.liketochiku.contents.LikeTochikuContents;
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.memories.MemoriesSliderActivity;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.quiz.QuizMainActivity;
import com.tochiku85.liketochiku.tochikuji.TochikujiActivity;
import com.tochiku85.liketochiku.umanatsu.UmanatsuActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.os.Build;
import android.app.Activity;

import java.util.ArrayList;

public class NewsActivity extends ActionBarActivity  implements LikeTochikuContents.LikeTochikuContentsCallback {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	static int nPageNo = 0 ;
    int requestCode ;
	private ViewFlipper mViewFlipper;
	private AnimationListener mAnimationListener;
	private Context mContext;

    private NewsListAdapter newsListAdapter ;
    private ListView lvNewsItems;
    private NewsContents mNewsContents ;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mNewsContents.loadData();
        }
    };

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		mContext = this;

        final android.app.ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("ニュース");

        mNewsContents = new NewsContents(this) ;
        mNewsContents.setCallback(this);
        mNewsContents.loadData() ;

        newsListAdapter  = new NewsListAdapter(this, mNewsContents) ;
        lvNewsItems = (ListView) findViewById(id.news_listView);
        lvNewsItems.setAdapter(newsListAdapter);
        newsListAdapter.notifyDataSetChanged();

        lvNewsItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("NEWS_TITLE",   mNewsContents.getTitle(position)) ;
                intent.putExtra("NEWS_ARTICLE", mNewsContents.getArticle(position)) ;
                intent.putExtra("NEWS_IMAGE_PATH",mNewsContents.getImagePath(position)) ;
                startActivityForResult(intent, 0);
            }
        });
        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
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

    public void callbackMethod(){
        Log.d("callbackMethod", "called");
        newsListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void callbackMethod(Object param) {
    }

}
