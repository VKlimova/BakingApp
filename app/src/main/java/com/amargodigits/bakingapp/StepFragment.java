package com.amargodigits.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
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

import com.squareup.picasso.Picasso;

/*
StepFragment is a fragment to display one step of recipe
 */
public class StepFragment extends Fragment {
    public static TextView stepTV, stepVideo;
    public SimpleExoPlayerView mPlayerView;
    public ImageView thumbImage;
    public Context mContext;
    final String CUR_POSITION = "CUR_POSITION";
    final String PLAYER_PLAY = "PLAYER_PLAY";
    final String LAST_VIDEO_URL = "LAST_VIDEO_URL";
    public long curPosition = 0;
    public String lastVideoUrl = "";
    public String thisVideoUrl;
    public boolean playerPlay = true;
    public SimpleExoPlayer exoPlayer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "StepFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        mContext = getContext();
        stepTV = (TextView) rootView.findViewById(R.id.step_text_view);
        stepVideo = (TextView) rootView.findViewById(R.id.step_video_view);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        thumbImage = (ImageView) rootView.findViewById(R.id.step_thumb);
        return rootView;
    }

    public void setStepDetails(String stepDescr, String stepVideoUrl, String stepThumbUrl) {
        thisVideoUrl = stepVideoUrl;
        if ((stepDescr != null) && (!stepDescr.isEmpty())) stepTV.setText(stepDescr);
        else stepTV.setText(getString(R.string.descr_empty));
        try {
            if ((stepVideoUrl != null) && (!stepVideoUrl.isEmpty())) {
                if (stepVideoUrl.contains(".mp4")) {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelection.Factory videoTrackSelectionFactory =
                            new AdaptiveTrackSelection.Factory(bandwidthMeter);
                    TrackSelector trackSelector =
                            new DefaultTrackSelector(videoTrackSelectionFactory);
                    exoPlayer =
                            ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                    mPlayerView.setPlayer(exoPlayer);
                    exoPlayer.setPlayWhenReady(playerPlay);

//    Example taken from:  https://stackoverflow.com/questions/47731779/detect-pause-resume-in-exoplayer
                    exoPlayer.addListener(new Player.DefaultEventListener() {
                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            if (playWhenReady && playbackState == Player.STATE_READY) {
                                // media actually playing
                                playerPlay = true;
                            } else if (playWhenReady) {
                                // might be idle (plays after prepare()),
                                // buffering (plays when data available)
                                // or ended (plays when seek away from end)
                                playerPlay = true;
                            } else {
                                // player paused in any state
                                playerPlay = false;
                            }
                        }
                    });
                    String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                    Uri mediaUri = Uri.parse(stepVideoUrl);
                    MediaSource videoSource = new ExtractorMediaSource(mediaUri,
                            new DefaultDataSourceFactory(getContext(), userAgent),
                            new DefaultExtractorsFactory(),
                            null, null);
                    exoPlayer.prepare(videoSource);
                    mPlayerView.setVisibility(View.VISIBLE);
                } else {
                    stepVideo.setText(getString(R.string.no_video));
                    mPlayerView.setVisibility(View.GONE);
                }
            } else {
                stepVideo.setText(getString(R.string.no_video));
                mPlayerView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "StepFragment stepVideoUrl Exception " + e.toString());
        }

        try {
            if ((stepThumbUrl != null) && (!stepThumbUrl.isEmpty())) {
                if (stepThumbUrl.length() > 1) {
                    Picasso.with(getContext()).load(stepThumbUrl)
                            .placeholder(android.R.drawable.stat_sys_download)
                            .error(android.R.drawable.ic_menu_report_image)
                            .into(thumbImage);
                    thumbImage.setVisibility(View.VISIBLE);
                } else thumbImage.setVisibility(View.GONE);
            } else thumbImage.setVisibility(View.GONE);
        } catch (Exception e) {
            thumbImage.setVisibility(View.GONE);
            Log.i(LOG_TAG, "StepFragment thumbImage Exception " + e.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (exoPlayer != null) {
            curPosition = exoPlayer.getCurrentPosition();
        }
        savedInstanceState.putLong(CUR_POSITION, curPosition);
        savedInstanceState.putBoolean(PLAYER_PLAY, playerPlay);
        savedInstanceState.putString(LAST_VIDEO_URL, lastVideoUrl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            curPosition = savedInstanceState.getLong(CUR_POSITION);
            playerPlay = savedInstanceState.getBoolean(PLAYER_PLAY);
            lastVideoUrl = savedInstanceState.getString(LAST_VIDEO_URL);
        } catch (Exception e) {
            Log.i(LOG_TAG, "StepFragment onActivityCreated Exception " + e.toString());
        }
//        // compare current video and previous video. If we've switched to the other video, start playback from 0
        if (lastVideoUrl != thisVideoUrl) {
            curPosition = 0;
        }
        lastVideoUrl = thisVideoUrl;
        try {
            savedInstanceState.putString(LAST_VIDEO_URL, lastVideoUrl);
            if (exoPlayer != null) {
                exoPlayer.setPlayWhenReady(playerPlay);
                exoPlayer.seekTo(curPosition);
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Step-Fragment onActivityCreated Exception " + e.toString());
        }
    }

    public void initPlayer(String stepVideoUrl)
    {

        if (exoPlayer != null) {
            return;
        }
        try {
            if ((stepVideoUrl != null) && (!stepVideoUrl.isEmpty())) {
                if (stepVideoUrl.contains(".mp4")) {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelection.Factory videoTrackSelectionFactory =
                            new AdaptiveTrackSelection.Factory(bandwidthMeter);
                    TrackSelector trackSelector =
                            new DefaultTrackSelector(videoTrackSelectionFactory);
                    exoPlayer =
                            ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                    mPlayerView.setPlayer(exoPlayer);
                    exoPlayer.setPlayWhenReady(playerPlay);

//    Example taken from:  https://stackoverflow.com/questions/47731779/detect-pause-resume-in-exoplayer
                    exoPlayer.addListener(new Player.DefaultEventListener() {
                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            if (playWhenReady && playbackState == Player.STATE_READY) {
                                // media actually playing
                                playerPlay = true;
                            } else if (playWhenReady) {
                                // might be idle (plays after prepare()),
                                // buffering (plays when data available)
                                // or ended (plays when seek away from end)
                                playerPlay = true;
                            } else {
                                // player paused in any state
                                playerPlay = false;
                            }
                        }
                    });
                    String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                    Uri mediaUri = Uri.parse(stepVideoUrl);
                    MediaSource videoSource = new ExtractorMediaSource(mediaUri,
                            new DefaultDataSourceFactory(getContext(), userAgent),
                            new DefaultExtractorsFactory(),
                            null, null);
                    exoPlayer.prepare(videoSource);
                    mPlayerView.setVisibility(View.VISIBLE);
                } else {
                    stepVideo.setText(getString(R.string.no_video));
                    mPlayerView.setVisibility(View.GONE);
                }
            } else {
                stepVideo.setText(getString(R.string.no_video));
                mPlayerView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "StepFragment stepVideoUrl Exception " + e.toString());
        }


        try {
        } catch (Exception e) {
            Log.i(LOG_TAG, "StepFragment onActivityCreated Exception " + e.toString());
        }
//        // compare current video and previous video. If we've switched to the other video, start playback from 0
        if (lastVideoUrl != thisVideoUrl) {
            curPosition = 0;
        }
        lastVideoUrl = thisVideoUrl;
        try {
//            savedInstanceState.putString(LAST_VIDEO_URL, lastVideoUrl);
            if (exoPlayer != null) {
                exoPlayer.setPlayWhenReady(playerPlay);
                exoPlayer.seekTo(curPosition);
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Step-Fragment onActivityCreated Exception " + e.toString());
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initPlayer(thisVideoUrl);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initPlayer(thisVideoUrl);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            curPosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}