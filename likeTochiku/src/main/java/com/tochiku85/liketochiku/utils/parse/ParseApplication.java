package com.tochiku85.liketochiku.utils.parse;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.PushService;
import com.tochiku85.liketochiku.BuildConfig;
import com.tochiku85.liketochiku.kitakyushu.KitakyushuContents;
import com.tochiku85.liketochiku.main.CountdownContents;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.main.MessageContents;
import com.tochiku85.liketochiku.news.NewsContents;
import com.tochiku85.liketochiku.quiz.QuizContents;
import com.tochiku85.liketochiku.tochikuji.TochikujiContents;

import android.app.Application;
import android.os.Message;
import android.util.Log;

public class ParseApplication extends Application {

    // Global variables
    private QuizContents mQuizContents ;
    private TochikujiContents mTochikujiContents ;
    private MessageContents mMessageContents ;
    private CountdownContents mCountdownContents ;
    private KitakyushuContents mKitakyushuContents ;
    private int mAdIndex ;

	@Override
	public void onCreate() {
		super.onCreate();

        ParseObject.registerSubclass(Photo.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(com.tochiku85.liketochiku.utils.parse.Activity.class);
        Parse.initialize(this, BuildConfig.PARSE_API_ID, BuildConfig.PARSE_API_KEY);

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        // Home
        mMessageContents = new MessageContents(this) ;
        mCountdownContents = new CountdownContents(this) ;

        // Quiz
        mQuizContents = new QuizContents(this) ;

        // Ad
        mKitakyushuContents = new KitakyushuContents(this) ;
        mAdIndex = 0 ;
	}

    public QuizContents getQuizContents() { return mQuizContents; }
    public MessageContents getMessageContents() { return mMessageContents; }
    public TochikujiContents getTochikujiContents() { return mTochikujiContents; }
    public CountdownContents getCountdownContents() { return mCountdownContents; }
    public KitakyushuContents getKitakyushuContents() { return mKitakyushuContents; }
    public int  getAdIndex() { return mAdIndex ; }
    public void setAdIndex(int index) { mAdIndex = index ;}
}