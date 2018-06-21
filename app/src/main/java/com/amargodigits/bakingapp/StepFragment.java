package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

public class StepFragment extends Fragment {


    public static TextView stepTV, stepVideo, stepThumb;
    public Context mContext;
    Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView =
                inflater.inflate(R.layout.fragment_step_detail, container, false);
        mContext = getContext();
        stepTV = (TextView) rootView.findViewById(R.id.step_text_view);
        stepVideo = (TextView) rootView.findViewById(R.id.step_video_view);
        stepThumb = (TextView) rootView.findViewById(R.id.step_thumb);
        SimpleExoPlayerView mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        Intent intent = getActivity().getIntent();
        String stepId = intent.getStringExtra("stepId");
        String stepName = intent.getStringExtra("stepName");
        String stepDescr = intent.getStringExtra("stepDescr");
        String recName = intent.getStringExtra("recName");
        String stepThumbUrl = intent.getStringExtra("stepThumbUrl");
        String stepVideoUrl = intent.getStringExtra("stepVideoUrl");

        mToolbar = (Toolbar) rootView.findViewById(R.id.menu_toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(recName);
        stepTV.setText(stepDescr);
        stepVideo.setText(stepVideoUrl);
        stepThumb.setText(stepThumbUrl);
        Log.i(LOG_TAG, "StepFragment, stepId : " + stepId + " stepName = " + stepName);
try {
    if (stepVideoUrl.contains(".mp4")) {
        // 1. Create a default TrackSelector
        //  Handler mainHandler = new Handler();
        // Measures bandwidth during playback. Can be null if not required.
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        SimpleExoPlayer exoPlayer =
                ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
        // Initialize the player view.

        mPlayerView.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(true);
        // Produces DataSource instances through which media data is loaded.

        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        Uri mediaUri = Uri.parse(stepVideoUrl);
        MediaSource videoSource = new ExtractorMediaSource(mediaUri,
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(),
                null, null);

        // This is the MediaSource representing the media to be played.

        // Prepare the player with the source.
        exoPlayer.prepare(videoSource);
    } else {
        stepVideo.setText("No video for this step");
        mPlayerView.setVisibility(View.GONE);
    }
} catch (Exception e){
    Log.i(LOG_TAG, "StepFragment Exception " + e.toString());
}
        Log.i(LOG_TAG, "StepFragment, main thread before return");
        return rootView;
    }
}
