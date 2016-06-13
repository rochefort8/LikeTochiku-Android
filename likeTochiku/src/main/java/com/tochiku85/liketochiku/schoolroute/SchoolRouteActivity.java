package com.tochiku85.liketochiku.schoolroute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.contents.LikeTochikuContents;
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.news.NewsContents;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.schoolroute.SchoolRouteListAdapter;

public class SchoolRouteActivity extends ActionBarActivity implements LikeTochikuContents.LikeTochikuContentsCallback {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    static int nPageNo = 0 ;
    int requestCode ;
    private ViewFlipper mViewFlipper;
    private Animation.AnimationListener mAnimationListener;
    private Context mContext;

    private SchoolRouteListAdapter schoolRouteListAdapter ;
    private ListView lvItems;
    private SchoolRouteContents mContents ;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mContents.loadData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_route);
        mContext = this;

        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("通学路");

        mContents = new SchoolRouteContents(this) ;
        mContents.setCallback(this);
        mContents.loadData() ;

        schoolRouteListAdapter  = new SchoolRouteListAdapter(this, mContents) ;
        lvItems = (ListView) findViewById(R.id.schoolroute_listView);
        lvItems.setAdapter(schoolRouteListAdapter);
        schoolRouteListAdapter.notifyDataSetChanged();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(mContext, SchoolRouteVideoViewActivity.class);
                intent.putExtra("SCHOOLROUTE_VIDEO_ID",mContents.getVideoId(position)) ;
                startActivity(intent);
            }
        });
        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
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
        schoolRouteListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void callbackMethod(Object param) {
    }

}
