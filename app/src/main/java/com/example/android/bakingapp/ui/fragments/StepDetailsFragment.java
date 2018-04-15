package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import static com.example.android.bakingapp.utils.Constants.NEXT_STEP;
import static com.example.android.bakingapp.utils.Constants.PLAYER_POSITION;
import static com.example.android.bakingapp.utils.Constants.PREVIOUS_STEP;

public class StepDetailsFragment extends Fragment {
    private StepDetailsFragmentBinding mBinding;
    private SimpleExoPlayer mExoPlayer;
    private Context mContext;
    private Step step;
    private long mExoPlayerCurrentPosition;
    private List<Step> stepList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(PLAYER_POSITION)) {
            mExoPlayerCurrentPosition = savedInstanceState.getLong(PLAYER_POSITION);
        }

        stepList = getActivity().getIntent().getParcelableArrayListExtra("Steps");
        mContext = getActivity().getBaseContext();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.step_details_fragment, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(NEXT_STEP)) {
            step = bundle.getParcelable(NEXT_STEP);
        } else if (bundle != null && bundle.containsKey(PREVIOUS_STEP)) {
            step = bundle.getParcelable(PREVIOUS_STEP);
        } else {
            step = getActivity().getIntent().getParcelableExtra(Constants.PARCEL_STEP);
        }
        if (!step.getThumbnailURL().isEmpty() || !step.getVideoURL().isEmpty()) {
            Uri videoUri = Uri.parse(step.getVideoURL());
            initializePlayer(videoUri);
        } else {
            Picasso.with(getActivity()).load(R.drawable.no_video).fit().into(mBinding.noVideoImage);
            mBinding.noVideoImage.setVisibility(View.VISIBLE);
        }
        String description = step.getDescription();
        mBinding.stepDescriptionTextView.setText(description);
        nextStep();
        previousStep();
        ifDeviceIsLandscape();
        return mBinding.getRoot();
    }

    private void initializePlayer(Uri uri) {
        if (mExoPlayer == null) {

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
            mExoPlayerCurrentPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION, mExoPlayerCurrentPosition);

    }


    private void ifDeviceIsLandscape() {
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBinding.nextButton.setVisibility(View.INVISIBLE);
            mBinding.previousButton.setVisibility(View.INVISIBLE);
            mBinding.stepDescriptionTextView.setVisibility(View.INVISIBLE);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();
            }
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mBinding.exoPlayer.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mBinding.exoPlayer.setLayoutParams(params);
        }
    }


    private void nextStep() {
        mBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepDetailsFragment fragment = new StepDetailsFragment();

                if (step.getId() + 1 >= stepList.size()) {
                    Toast.makeText(mContext, "This is the last step", Toast.LENGTH_SHORT).show();
                } else {
                    Step steps = stepList.get(step.getId() + 1);
                    Bundle args = new Bundle();
                    args.putParcelable(NEXT_STEP, steps);
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().
                            replace(R.id.step_details_container, fragment).
                            commit();
                }

            }
        });
    }

    private void previousStep() {
        mBinding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepDetailsFragment fragment = new StepDetailsFragment();
                if (step.getId()  <= 0) {
                    Toast.makeText(mContext, "This is the first step", Toast.LENGTH_SHORT).show();
                } else {
                    Step steps = stepList.get(step.getId() - 1);
                    Bundle args = new Bundle();
                    args.putParcelable(PREVIOUS_STEP, steps);
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().
                            replace(R.id.step_details_container, fragment).
                            commit();
                }
            }
        });
    }


}