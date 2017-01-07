package com.tochiku85.liketochiku.quiz;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.memories.MemoriesSliderActivity;
import com.tochiku85.liketochiku.news.NewsActivity;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.tochikuji.TochikujiActivity;
import com.tochiku85.liketochiku.umanatsu.UmanatsuActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class QuizMainActivity extends ActionBarActivity implements OnClickListener, LikeTochikuContents.LikeTochikuContentsCallback {

    Button button1, button2, button3;
    int requestCode;
    int table[] ;
    int totalQuestionCount;
    ProgressDialog mProgressDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ParseApplication parseApplication ;
    private QuizContents mQuizContents ;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mQuizContents.loadData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        final android.app.ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("クイズ");

        button1 = (Button) findViewById(R.id.button_quiz_start);
        button1.setOnClickListener(this);

        ParseApplication parseApplication = (ParseApplication)this.getApplication();
        mQuizContents = parseApplication.getQuizContents() ;
        mQuizContents.setCallback(this);

        // SwipeRefreshLayoutの設定
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mProgressDialog = ProgressDialog.show(this, "", "更新しています...", true);
        mQuizContents.loadData() ;

        totalQuestionCount = 3 ;
        table = new int[totalQuestionCount] ;
//        setNewQuestionList() ;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_quiz_start:
                setNewQuestionList() ;

                Intent intent = new Intent(this, QuizQuestionActivity.class);
                intent.putExtra("QUIZLIST",table) ;
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

    @Override
    public void callbackMethod() {
        Log.d("Quiz:callbackMethod", "called");
        mProgressDialog.dismiss() ;
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void callbackMethod(Object param) {
    }

    public void setNewQuestionList() {
        Random rnd = new Random();

        int totalQuizCount =  mQuizContents.getObjectCount() ;
        if (totalQuestionCount <  totalQuizCount) {
            for (int i = 0;i < totalQuestionCount;) {
                int rand = rnd.nextInt(totalQuizCount) ;
                boolean already_exist = false ;
                for (int j = 0;j < i;j++) {
                    if (table[j] == rand) {
                        already_exist = true ;
                        break ;
                    }
                }
                if (already_exist == false) {
                    table[i] = rand ;
                    i++ ;
                }
            }
        } else {
            for (int i = 0;i < totalQuestionCount;) {
                table[i] = 0 ;
            }
        }
        for (int i = 0;i < totalQuestionCount;i++) {
            Log.d("Q",Integer.toString(table[i])) ;
        }
    }
}
