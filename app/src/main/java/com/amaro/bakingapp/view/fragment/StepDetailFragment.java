package com.amaro.bakingapp.view.fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;


public class StepDetailFragment extends Fragment {

    private static final String TAG = "StepDetailFragment";
    private static final String EXTRA_BUNDLE = "extra_step";
    private static final String USER_AGENT = "exoplayer-bakingapp";
    private static final String PLAYBACK_POSITION = "video_position";
    private static final String CURRENT_WINDOW = "current_window";

    private Step mStep;

    private PlayerView mPlayerView;
    private TextView mTitle;
    private TextView mContent;
    private CardView mCardView;

    private ExoPlayer mPlayer;
    private long mPlaybackPosition;
    private int mCurrentWindow;
    private boolean mPlayWhenReady;
    private ImageView mThumbnail;

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

        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, C.TIME_UNSET);
            mCurrentWindow = savedInstanceState.getInt(CURRENT_WINDOW, 0);
        }

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT || getResources().getBoolean(R.bool.is_sw600)) {
            setUpViews(view);
        }

        setUpVideoPlayer(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {

            mStep = args.getParcelable(EXTRA_BUNDLE);
            if(mTitle != null && mContent != null) {
                mTitle.setText(mStep.getShortDescription());
                mContent.setText(mStep.getDescription());
            }

            if(!mStep.getThumbnailURL().isEmpty() && mThumbnail != null && mStep.getVideoURL().isEmpty()) {
                mThumbnail.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(mStep.getThumbnailURL())
                        .into(mThumbnail);
            }
        }
    }

    private void setUpViews(View view) {
        mTitle = view.findViewById(R.id.title_step);
        mCardView = view.findViewById(R.id.cardView);
        mContent = view.findViewById(R.id.content_step);
        mThumbnail = view.findViewById(R.id.thumbnail);
    }

    private void setUpVideoPlayer(View view) {
        mPlayerView = view.findViewById(R.id.player);
    }

    private  void initializePlayer() {
        if(mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                                getContext(),
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mPlayerView.setPlayer(mPlayer);
        }

        if(!mStep.getVideoURL().isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            MediaSource mediaSource = buildMediaSource(Uri.parse(mStep.getVideoURL()));
            mPlayer.prepare(mediaSource, true, false);
            mPlayer.setPlayWhenReady(mPlayWhenReady);
            mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

            if(mThumbnail != null) {
                mThumbnail.setVisibility(View.GONE);
            }
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();

            mPlayer = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(USER_AGENT)).createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(PLAYBACK_POSITION, mPlaybackPosition);
        outState.putInt(CURRENT_WINDOW, mCurrentWindow);
        super.onSaveInstanceState(outState);
    }
}
