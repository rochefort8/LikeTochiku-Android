package com.tochiku85.liketochiku.photorelay;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.R;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.tochiku85.liketochiku.memories.MemoriesSliderActivity;
import com.tochiku85.liketochiku.news.NewsActivity;
import com.tochiku85.liketochiku.quiz.QuizMainActivity;
import com.tochiku85.liketochiku.tochikuji.TochikujiActivity;
import com.tochiku85.liketochiku.umanatsu.UmanatsuActivity;

import java.util.List;
import java.util.Date;

public class PhotosActivity extends ActionBarActivity implements View.OnClickListener {
	public static final String CLIENT_ID = "8e50b9f199d84972a23c9740f24c10d1";
    ImageButton button1, Button2 ;
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
	private ListView lvPhotos;
    private int requestCode ;
	private SwipeRefreshLayout swipeContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);

        final ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("画用紙リレー");

        button1 = (ImageButton)findViewById(R.id.button_capture) ;
        button1.setOnClickListener(this);

        photos = new ArrayList<InstagramPhoto>();
		aPhotos = new InstagramPhotosAdapter(this, photos);
		lvPhotos = (ListView) findViewById(R.id.lvPhotos);
		lvPhotos.setAdapter(aPhotos);

		fetchPopularPhotos();
		
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            	fetchPopularPhotos();
            } 
        });
	}

	private void fetchPopularPhotos() {
        final String className = "Photo" ;

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(className);
        query.orderByDescending("updatedAt") ;
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> beers, ParseException e) {
                if (e == null) {
                    Log.d("Photo:", beers.size() + "Beers found on the server, downloading...");
                    photos.clear();
                    aPhotos.notifyDataSetChanged();

                    for (int i = 0; i < beers.size(); i++) {
                        ParseObject object = beers.get(i);
                        String objectId = object.getObjectId().toString();

                        // URL for Photo
                        ParseFile image = (ParseFile) object.get("image");
                        String imageUrl = image.getUrl() ;

                        // Caption
                        String caption = object.getString("caption") ;

                        //
                        Date updateAt = object.getUpdatedAt() ;

                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = "OGIHARA" ;
                        photo.profilePictureUrl = null ;
                        photo.caption = caption ;

                        photo.imageUrl = imageUrl ;
                        photo.imageHeight =640;
                        photo.imageWidth = 480;
                        photo.likesCount = 1234 ;
                        photo.createdTime = updateAt ;

                        int numComments = 2 ;

                        InstagramComment comment;
                        JSONObject commentJSON;

                        if (numComments > 0) {
                            photo.comments = new ArrayList<InstagramComment>();

                            if (numComments > 1) {
                                comment = new InstagramComment();
                                comment.username = "Somebody";
                                comment.text = "So nice!" ;
                                photo.comments.add(comment);
                            }

                            comment = new InstagramComment();
                            comment.username = "Noname";
                            comment.text = "Wonderful!" ;
                            photo.comments.add(comment);
                        }

                        photos.add(photo);
                    }
                    aPhotos.notifyDataSetChanged();
                } else {
                    Log.d("Downloader", "Error: " + e.getMessage());
                }
                swipeContainer.setRefreshing(false);
            }
        });
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
        Intent intent ;

        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class) ;
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
    public void onClick(View v) {
        Intent intent ;
        int requestCode = 0 ;

        switch (v.getId()) {
            case R.id.button_capture:
                intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, requestCode);
                break;
        }
    }

}
