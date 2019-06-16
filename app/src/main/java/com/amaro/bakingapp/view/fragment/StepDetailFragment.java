package com.amaro.bakingapp.view.fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.ButterKnife;


public class StepDetailFragment extends Fragment {

    private static final String TAG = "StepDetailFragment";
    private static final String EXTRA_BUNDLE = "extra_step";
    private static final String USER_AGENT = "exoplayer-bakingapp";

    private Step step;

    PlayerView mPlayerView;
    TextView mTitle;
    TextView mContent;
    CardView cardView;

    private ExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;


    public static StepDetailFragment newInstance(Step step) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_BUNDLE, step);

        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_detail_fragment, container, false);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT || getResources().getBoolean(R.bool.is_sw600)) {
            setUpViews(view);
        }

        setUpVideoPlayer(view);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {

            step = args.getParcelable(EXTRA_BUNDLE);
            if(mTitle != null && mContent != null) {
                mTitle.setText(step.getShortDescription());
                mContent.setText(step.getDescription());
            }
        }
    }

    private void setUpViews(View view) {
        mTitle = view.findViewById(R.id.title_step);
        cardView = view.findViewById(R.id.cardView);
        mContent = view.findViewById(R.id.content_step);
    }

    private void setUpVideoPlayer(View view) {
        mPlayerView = view.findViewById(R.id.player);
    }

    private  void initializePlayer() {
        if(player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                                getContext(),
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mPlayerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);

        }

        if(!step.getVideoURL().isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            MediaSource mediaSource = buildMediaSource(Uri.parse(step.getVideoURL()));
            player.prepare(mediaSource, true, false);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(USER_AGENT)).createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
