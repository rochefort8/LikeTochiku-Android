package com.tochiku85.liketochiku.schoolroute;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.tochiku85.liketochiku.R;

public class SchoolRouteVideoViewActivity extends YouTubeFailureRecoveryActivity implements
    YouTubePlayer.OnInitializedListener {

    public static final String DEVELOPER_KEY = "AIzaSyCzOvF935XQ8kjcA61IuIsMbeg2ZmgpGpw";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String mVideoId ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_school_route_video_view);

        mVideoId = getIntent().getStringExtra("SCHOOLROUTE_VIDEO_ID") ;

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.schoolroute_video_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(mVideoId);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.schoolroute_video_view);
    }
}

