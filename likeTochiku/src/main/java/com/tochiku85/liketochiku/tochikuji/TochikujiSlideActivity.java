package com.tochiku85.liketochiku.tochikuji;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.tochiku85.liketochiku.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class TochikujiSlideActivity extends AppCompatActivity {


	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	@SuppressWarnings("deprecation")
	private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
	int requestCode ;
	int nPage = 0 ;
	private ViewFlipper mViewFlipper;
	private AnimationListener mAnimationListener;
	private Context mContext;
    private ViewFlipper oViewFliper;
	private Timer mTimer ;
	private TimerTask mTimerTask ;
	private int mCount = 0 ;
	private int id[] = {
			R.id.tochikuji_slide0,
			R.id.tochikuji_slide1,
			R.id.tochikuji_slide2,
			R.id.tochikuji_slide3,
	} ;
	private int id_index = 0;
	private ArrayList<String> imageList ;
	private ArrayList<Integer> playList ;
	private ArrayList<Bitmap> bitmapList ;
	private int id2image[] ;
	private int mTotalCount = 0 ;
	private MediaPlayer mp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tochikuji_slide);

		final android.app.ActionBar actionBar = getActionBar();
		getSupportActionBar().setTitle("画面をタップしてね！");

		mp = MediaPlayer.create(this,R.raw.tochikuji);
		mp.start();

		Intent intent = getIntent() ;
		imageList = intent.getStringArrayListExtra("IMAGELIST") ;
		playList = intent.getIntegerArrayListExtra("PLAYLIST") ;
		bitmapList = new ArrayList<Bitmap>() ;
		id2image = new int [id.length] ;
				
		for (int i = 0;i < playList.size();i++) {
			Log.d("S",Integer.toString(playList.get(i))) ;
		}
		
		for (int i = 0;i < imageList.size();i++) {
			Log.d("T",imageList.get(i)) ;
		}
		
		for (int i = 0;i < imageList.size();i++) {
			Bitmap bmp=BitmapFactory.decodeFile(getFilesDir() + "/" + imageList.get(i)) ;
			bitmapList.add(bmp) ;
		}	
		
		
        // ViewFlipper�̐���
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.tochikuji_view_flipper);
		mViewFlipper.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				detector.onTouchEvent(event);
			
				return true;
			}
		});

		
		for (int i = 0; i < id.length;i++) {
			int play_index = playList.get(i) ;
			Bitmap bmp = bitmapList.get(play_index) ;
			ImageView oImage = (ImageView)findViewById(id[i]);
			oImage.setImageBitmap(bmp);		
			id2image[i] = play_index ;
		}	

		mTimer = new Timer() ;
	    mTimerTask = new TimerTask() {
	    	Handler handler = new Handler() ;
	    	
	        public void run() {
	        	 handler.post( new Runnable() {
	
	        	    @Override
	        	    public void run() {
	        	    	
	        			ImageView oImage = (ImageView)findViewById(id[id_index]);
	        	        // ImageView�ɉ摜��ݒ�
	        		
	        			int play_index = playList.get(mCount) ;
	        			id2image[id_index] = play_index ;
//	        			Bitmap bmp=BitmapFactory.decodeFile(imageList.get(play_index)) ;
	        			Bitmap bmp = bitmapList.get(play_index) ;	
	        			// Set the Bitmap into the
	        			// ImageView
	        			oImage.setImageBitmap(bmp);		
        	    	
	        	    	mViewFlipper.showNext();
	        	    	id_index++ ;
	        	    	if (id.length <= id_index) {
	        	    		id_index = 0 ;
	        	    	}
	        	    }
	        	   });
	        	mCount++ ;
    	    	mTotalCount++ ;
    	    	if (100 <= mTotalCount) {
    	    		mTimer.cancel() ;
    	    	}
	        	if (imageList.size() <= mCount) {
	        		mCount = 0 ;
	        	}
	        }
	    };
		mTimer.schedule(mTimerTask,200,200) ;
	
	}

	public void onSingleTapUp(View v) {
		Log.d("BBB","BBB") ;
	}

	class SwipeGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.v("Gesture", "onDoubleTap");
			mTimer.cancel() ;

			Log.d("AAA",imageList.get(id2image[id_index])) ;
			try {
				Thread.sleep(1000) ;
			} catch (InterruptedException ee) {
			}
			mp.stop();
			Intent intent = new Intent(TochikujiSlideActivity.this,TochikujiResultActivity.class) ;
			intent.putExtra("IMAGE",imageList.get(id2image[id_index])) ;
			startActivityForResult(intent, requestCode);

			return super.onSingleTapUp(e);
		}
	}
}
