package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Step;
import com.amargodigits.bakingapp.utils.NetworkUtils;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

public class StepActivity extends AppCompatActivity {


    public static TextView stepTV, stepVideo, stepThumb;
    public Context mContext;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mContext = getApplicationContext();
        stepTV = (TextView) findViewById(R.id.step_text_view);
        stepVideo = (TextView) findViewById(R.id.step_video_view);
        stepThumb = (TextView) findViewById(R.id.step_thumb);
        Intent intent = getIntent();
        String stepId = intent.getStringExtra("stepId");
        String stepName = intent.getStringExtra("stepName");
        String recName = intent.getStringExtra("recName");
        String stepThumbUrl = intent.getStringExtra("stepThumbUrl");
        String stepVideoUrl = intent.getStringExtra("stepVideoUrl");

        mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(recName);
        stepTV.setText(stepName);
        stepVideo.setText(stepVideoUrl);
        stepThumb.setText(stepThumbUrl);
        Log.i(LOG_TAG, "StepActivity, stepId : " + stepId + " stepName = " + stepName);

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();

        // Measures bandwidth during playback. Can be null if not required.
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create the player
        SimpleExoPlayer exoPlayer =
                ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
// Bind the player to the view.
        SimpleExoPlayerView mPlayerView;
        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.playerView);
        mPlayerView.setPlayer(exoPlayer);
exoPlayer.setPlayWhenReady(true);
// Produces DataSource instances through which media data is loaded.

        String userAgent = Util.getUserAgent(this, "BakingApp");
        Uri mediaUri= Uri.parse(stepVideoUrl);
        MediaSource videoSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                this, userAgent), new DefaultExtractorsFactory(), null, null);

// This is the MediaSource representing the media to be played.

// Prepare the player with the source.
        exoPlayer.prepare(videoSource);
    }


}
