package com.tochiku85.liketochiku.main;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;
import com.tochiku85.liketochiku.kitakyushu.KitakyushuContents;
import com.tochiku85.liketochiku.login.LoginActivity;
import com.tochiku85.liketochiku.memories.MemoriesSliderActivity;
import com.tochiku85.liketochiku.news.NewsActivity;
import com.tochiku85.liketochiku.news.NewsContents;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.quiz.QuizMainActivity;
import com.tochiku85.liketochiku.tochikuji.TochikujiActivity;
import com.tochiku85.liketochiku.umanatsu.UmanatsuActivity;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.umanatsu.UmanatsuSlideActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity implements OnClickListener,LikeTochikuContents.LikeTochikuContentsCallback {

	Button button1, button2, button3, button4, button5, button6 ;
	int requestCode ;
    private Context mCtx;
    private int counter = 0 ;

    private long mCountdownDate ;
    private ParseApplication parseApplication ;

    private MessageContents mMessageContents ;
    private KitakyushuContents mAdContents ;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CountdownContents mCountdownContents ;
    private String mCountdownKey ;

    private int mDebugModeCounter ;
    private boolean bDebugMode ;

    private int mAdIndex ;
    private String mAdMessage ;
    private TextView mAdView ;
    private String mAdURL ;
    private int mAdMessageStartIndex ;
    private boolean bAdPublishing ;
    private Handler mAdHandler ;
    private String mAdTitle ;

    private ProgressDialog mProgressDialog ;

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mMessageContents.loadData();
            mCountdownContents.setAdditionalQuery("title",mCountdownKey);
            mCountdownContents.loadData() ;
        }
    };

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("ホーム");

        mCtx = this ;
        TextView textView = (TextView)findViewById(R.id.text_main_ad) ;
        textView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(mCtx, ReunionWebsiteActivity.class) ;
                intent.putExtra("ACTIONBAR_TITLE",mAdTitle) ;
                intent.putExtra("WEBVIEW_URL",mAdURL) ;
                startActivity(intent) ;
                return true;
            }
        });

        getCountdownValue() ;

        mProgressDialog = ProgressDialog.show(this, "", "更新しています...", true);

        parseApplication = (ParseApplication)this.getApplication();
        mMessageContents = parseApplication.getMessageContents() ;
        mMessageContents.setCallback(this,Integer.valueOf(9));

        mCountdownContents = parseApplication.getCountdownContents() ;
        mCountdownContents.setCallback(this,Integer.valueOf(8));

        mAdContents = parseApplication.getKitakyushuContents() ;
        mAdContents.setCallback(this, Integer.valueOf(7));

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mSwipeRefreshLayout.setRefreshing(true);

        mCountdownContents.setAdditionalQuery("title", mCountdownKey);
        mCountdownContents.loadData() ;

        mMessageContents.loadData() ;
        mAdContents.loadData() ;

        bDebugMode = false ;
        mDebugModeCounter = 10 ;

        mAdView = (TextView)findViewById(R.id.text_main_ad) ;
        mAdIndex = parseApplication.getAdIndex() ;
        mAdHandler = new Handler() ;
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

   @Override
	public void onClick(View v) {
   }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
        Intent intent ;
        switch (item.getItemId()) {
            case R.id.action_home:
                if (bDebugMode == false) {
                    if (0 < mDebugModeCounter) {
                        mDebugModeCounter-- ;
                    } else {
                        bDebugMode = true ;
                        mCountdownDate = 103 ;
                    }
                } else {
                    if (0 <= mCountdownDate) {
                        mCountdownDate--;
                        setCountdownKey();
                    } else {
                        bDebugMode = false ;
                        getCountdownValue();
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    mCountdownContents.setAdditionalQuery("title",mCountdownKey);
                    mCountdownContents.loadData() ;
                }
                break ;

            case R.id.action_photo_relay:
                intent = new Intent(this, PhotosActivity.class) ;
                startActivityForResult(intent, requestCode);
                break;

            case R.id.action_settings:
            case R.id.action_others:
                intent = new Intent(this, FunctionListActivity.class) ;
                startActivityForResult(intent, requestCode);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true ;
	}

    @Override
    public void callbackMethod() {
        /*
        TextView messageText = (TextView)findViewById(R.id.text_main_messages) ;
        messageText.setText(mMessageContents.getMessage(0));
        mSwipeRefreshLayout.setRefreshing(false);
        */
    }

    @Override
    public void callbackMethod(Object param) {
        Log.d("Main", "callback") ;

        if(param instanceof Integer) {
            Integer i = (Integer)param ;
            Log.d("Integer",i.toString()) ;
            switch (i.intValue()) {
                case 9 :
                    TextView messageText = (TextView)findViewById(R.id.text_main_messages) ;
                    messageText.setText(mMessageContents.getMessage(0));
                    mProgressDialog.dismiss();
                    break ;
                case 8:
                    ImageView imageView = (ImageView)findViewById(R.id.image_countdown);
                    Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/" + mCountdownContents.getImagePath(0));
                    imageView.setImageBitmap(bitmap);
                    mProgressDialog.dismiss();
                    break ;

                case 7:
                    mProgressDialog.dismiss();
                    startAd();
                    break ;

                default :
                    break;
            }
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onClickMessageContents(View view) {
        ImageView imageView = (ImageView)findViewById(R.id.image_countdown);
        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/" + mCountdownContents.getImagePath(0));
        imageView.setImageBitmap(bitmap);

        Intent intent = new Intent(this, HomeMessageActivity.class) ;
        startActivity(intent) ;
    }

    public void  getCountdownValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dateTo = null;

        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        final Date dateFrom = new Date(System.currentTimeMillis());
        df.format(dateFrom) ;

        try {
            dateTo = sdf.parse("2015/11/07");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTimeTo = dateTo.getTime();
        long dateTimeFrom = dateFrom.getTime();

        long dayDiff = ( dateTimeTo - dateTimeFrom  ) / (1000 * 60 * 60 * 24 ) + 1 ;

        mCountdownDate = dayDiff ;

        setCountdownKey();
    }

    private void setCountdownKey() {
        if (0 <= mCountdownDate && mCountdownDate <= 100) {
            mCountdownKey = Long.toString(mCountdownDate) ;
        } else {
            if (mCountdownDate < 0) {
                mCountdownKey = "-1" ;
            } else {
                mCountdownKey = "101";
            }
        }
    }

    /* ---------------- */
    private void startAd() {

        if (bAdPublishing == true) {
            Log.d ("Ad", "already publishing..");
            return ;
        }
        int numberOfAd = mAdContents.getObjectCount() ;

        if (numberOfAd <= 0) {
            Log.d("Ad","No ad found.") ;
            return ;
        }
        if (numberOfAd <= mAdIndex) {
            mAdIndex = 0 ;
        }
        mAdURL = mAdContents.getLink(mAdIndex) ;
        mAdTitle = mAdContents.getTitle(mAdIndex) ;

        // "title -- caption"
        String title   = mAdContents.getTitle(mAdIndex) ;
        String caption = mAdContents.getCaption(mAdIndex) ;
        mAdMessage = title + " -- " + caption ;

        mAdIndex++ ;
        if (numberOfAd <= mAdIndex) {
            mAdIndex = 0 ;
        }
        parseApplication.setAdIndex(mAdIndex) ;

        mAdMessageStartIndex = 0 ;
        mAdView.setText(mAdMessage) ;
        bAdPublishing = true ;

        mAdHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onStartFlip();
            }
        }, 3000);
    }

    private void stopAd() {
        bAdPublishing = false ;
        mAdMessageStartIndex = 0 ;
        mAdHandler.removeCallbacksAndMessages(null);
    }

    private void onStartFlip() {
        final int DELAY = 500 ;

        mAdHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdView.setText(mAdMessage.substring(mAdMessageStartIndex)) ;
                mAdMessageStartIndex++;
                if (mAdMessageStartIndex < mAdMessage.length()) {
                    mAdHandler.postDelayed(this, DELAY);
                } else {
                    mAdView.setText("") ;
                }
            }
        }, DELAY);
    }
}