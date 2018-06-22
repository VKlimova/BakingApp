package com.amargodigits.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

/*
StepFragment is a fragment with one step of recipe
 */
public class StepFragment extends Fragment {
    public static TextView stepTV, stepVideo, stepThumb;
    public SimpleExoPlayerView mPlayerView;
    public ImageView thumbImage;
    public Context mContext;
    Toolbar mToolbar;

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

        if ((stepDescr != null) && (!stepDescr.isEmpty())) stepTV.setText(stepDescr);
        else stepTV.setText("Description is empty");

        try {
            if ((stepVideoUrl != null) && (!stepVideoUrl.isEmpty())) {
                if (stepVideoUrl.contains(".mp4")) {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelection.Factory videoTrackSelectionFactory =
                            new AdaptiveTrackSelection.Factory(bandwidthMeter);
                    TrackSelector trackSelector =
                            new DefaultTrackSelector(videoTrackSelectionFactory);
                    SimpleExoPlayer exoPlayer =
                            ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                    mPlayerView.setPlayer(exoPlayer);
                    exoPlayer.setPlayWhenReady(true);
                    String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                    Uri mediaUri = Uri.parse(stepVideoUrl);
                    MediaSource videoSource = new ExtractorMediaSource(mediaUri,
                            new DefaultDataSourceFactory(getContext(), userAgent),
                            new DefaultExtractorsFactory(),
                            null, null);
                    exoPlayer.prepare(videoSource);
                    mPlayerView.setVisibility(View.VISIBLE);
                } else {
                    stepVideo.setText("No video for this step");
                    mPlayerView.setVisibility(View.GONE);
                }
            } else {
                stepVideo.setText("No video for this step");
                mPlayerView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "StepFragment Exception " + e.toString());
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
            Log.i(LOG_TAG, "StepFragment Exception " + e.toString());
        }
    }
}