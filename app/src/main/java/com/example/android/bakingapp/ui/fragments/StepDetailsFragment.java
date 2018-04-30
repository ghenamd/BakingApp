package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Step;
import com.example.android.bakingapp.databinding.StepDetailsFragmentBinding;
import com.example.android.bakingapp.utils.Constants;
import com.google.android.exoplayer2.C;
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

import static com.example.android.bakingapp.ui.fragments.RecipeDetailsFragment.STEP_FROM_RDF;
import static com.example.android.bakingapp.ui.fragments.RecipeDetailsFragment.recipe;
import static com.example.android.bakingapp.utils.Constants.NEXT_STEP;
import static com.example.android.bakingapp.utils.Constants.PREVIOUS_STEP;

public class StepDetailsFragment extends Fragment {
    private StepDetailsFragmentBinding mBinding;
    private SimpleExoPlayer mExoPlayer;
    private Context mContext;
    private Step step;
    private List<Step> stepList;
    private final String RESUME_WINDOW = "resume_window";
    private final String RESUME_POSITION = "resume_position";
    private final String RESUME_STEP = "resume_step";
    private final String RESUME_PLAY_WHEN_READY = "resume_play_when_ready";
    private final String STEPS = "Steps";
    private long mResumePosition;
    private int mResumeWindow;
    private boolean mPlayWhenReady;
    private Uri videoUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mResumePosition = savedInstanceState.getLong(RESUME_POSITION);
            mResumeWindow = savedInstanceState.getInt(RESUME_WINDOW);
            step = savedInstanceState.getParcelable(RESUME_STEP);
            mPlayWhenReady = savedInstanceState.getBoolean(RESUME_PLAY_WHEN_READY);
        }
        stepList = getActivity().getIntent().getParcelableArrayListExtra(STEPS);
        if (stepList == null) {
            stepList = recipe.getSteps();
        }

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(NEXT_STEP)) {
            step = bundle.getParcelable(NEXT_STEP);
        } else if (bundle != null && bundle.containsKey(PREVIOUS_STEP)) {
            step = bundle.getParcelable(PREVIOUS_STEP);
        } else if (bundle != null && bundle.containsKey(STEP_FROM_RDF)) {
            step = bundle.getParcelable(STEP_FROM_RDF);
        } else {
            step = getActivity().getIntent().getParcelableExtra(Constants.PARCEL_STEP);
        }
        if (step == null) {
            step = stepList.get(0);
        }
        mContext = getActivity().getBaseContext();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.step_details_fragment, container, false);
        boolean isTablet = getActivity().getResources().getBoolean(R.bool.isTablet);
        boolean isLandscape = getActivity().getResources().getBoolean(R.bool.isLandscape);
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            videoUri = Uri.parse(step.getVideoURL());
            initializePlayer(videoUri);
        } else if (!TextUtils.isEmpty(step.getThumbnailURL())){
            Picasso.with(getActivity()).
                    load(step.getThumbnailURL()).
                    error(R.drawable.no_video).
                    fit().
                    into(mBinding.noVideoImage);
            mBinding.noVideoImage.setVisibility(View.VISIBLE);
            mBinding.exoPlayer.setVisibility(View.INVISIBLE);
        } else {
            Picasso.with(getActivity()).
                    load(R.drawable.no_video).
                    fit().
                    into(mBinding.noVideoImage);
            mBinding.noVideoImage.setVisibility(View.VISIBLE);
            mBinding.exoPlayer.setVisibility(View.INVISIBLE);
        }
        String description = step.getDescription();
        mBinding.stepDescriptionTextView.setText(description);
        nextStep();
        previousStep();
        if (!isTablet && isLandscape){exoPlayerToLandscape(); }
        if (isTablet){mBinding.previousButton.setVisibility(View.INVISIBLE);mBinding.nextButton.setVisibility(View.INVISIBLE);}
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
            boolean resumePosition = mResumeWindow != C.INDEX_UNSET;
            if (resumePosition) {
               mExoPlayer.seekTo(mResumeWindow, mResumePosition);
            }
            mExoPlayer.prepare(mediaSource, false, false);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save ExoPlayer position and playerWindow;
        outState.putInt(RESUME_WINDOW, mResumeWindow);
        outState.putLong(RESUME_POSITION, mResumePosition);
        outState.putParcelable(RESUME_STEP,step);
        outState.putBoolean(RESUME_PLAY_WHEN_READY,mPlayWhenReady);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoUri != null) {
            initializePlayer(videoUri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mResumeWindow = mExoPlayer.getCurrentWindowIndex();
            mResumePosition = mExoPlayer.getContentPosition();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
        }
        releasePlayer();
    }

    private void nextStep() {
        mBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releasePlayer();
                StepDetailsFragment fragment = new StepDetailsFragment();
                if (step.getId() + 1 >= stepList.size()) {
                    Toast.makeText(mContext, R.string.last_step, Toast.LENGTH_SHORT).show();
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
                releasePlayer();
                StepDetailsFragment fragment = new StepDetailsFragment();
                if (step.getId() <= 0) {
                    Toast.makeText(mContext, R.string.first_step, Toast.LENGTH_SHORT).show();
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

    private void exoPlayerToLandscape() {
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