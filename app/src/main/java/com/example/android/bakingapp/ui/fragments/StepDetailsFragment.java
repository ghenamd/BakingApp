package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Step;
import com.example.android.bakingapp.databinding.StepDetailsFragmentBinding;
import com.example.android.bakingapp.utils.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

public class StepDetailsFragment extends Fragment {
    private StepDetailsFragmentBinding mBinding;
    private SimpleExoPlayer mExoPlayer;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity().getBaseContext();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.step_details_fragment, container, false);
        Step step = getActivity().getIntent().getParcelableExtra(Constants.PARCEL_STEP);
        String videoURL = step.getVideoURL();
        if (videoURL.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBinding.exoPlayer.setForeground(mContext.getDrawable(R.drawable.no_video));
            }
        } else {
            Uri videoUri = Uri.parse(videoURL);
            initializePlayer(videoUri);
        }

        ifDeviceIsLandscape();
        return mBinding.getRoot();
    }

    private void initializePlayer(Uri uri) {
        if (mExoPlayer == null) {
            mBinding.exoPlayer.requestFocus();
            TrackSelection.Factory videoTrackSelector = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelector);
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mBinding.exoPlayer.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(mContext, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(mContext, userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }

    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }


    private void ifDeviceIsLandscape() {
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBinding.nextButton.setVisibility(View.INVISIBLE);
            mBinding.previousButton.setVisibility(View.INVISIBLE);
            if(((AppCompatActivity)getActivity()).getSupportActionBar()!= null){
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();
            }
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mBinding.exoPlayer.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mBinding.exoPlayer.setLayoutParams(params);
        }
    }
}