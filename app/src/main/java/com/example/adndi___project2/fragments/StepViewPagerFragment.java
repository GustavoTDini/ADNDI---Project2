package com.example.adndi___project2.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adndi___project2.R;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeSteps;
import com.example.adndi___project2.recipe_utilities.DataUtilities;
import com.example.adndi___project2.recipe_utilities.ExoplayerUtilities;
import com.example.adndi___project2.view_model.GetStepsViewModel;
import com.example.adndi___project2.view_model.GetStepsViewModelFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import timber.log.Timber;

public class StepViewPagerFragment extends Fragment implements ExoPlayer.EventListener {

    private int mRecipeId = 0;

    private int mStepOrder;

    private TextView mDescriptionTV;

    private SimpleExoPlayerView mPlayerIV;
    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            ExoplayerUtilities.releasePlayer(mExoPlayer);
            mExoPlayer.stop();
        }
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (mExoPlayer != null) {
                ExoplayerUtilities.releasePlayer(mExoPlayer);
                mExoPlayer.stop();
            }
            if (mMediaSession != null) {
                mMediaSession.setActive(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            ExoplayerUtilities.releasePlayer(mExoPlayer);
            mExoPlayer = null;
        }
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
            mMediaSession = null;
        }
    }

    public StepViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_details_content, container, false);

        if (getArguments() != null && getArguments().containsKey( DataUtilities.ID_INTENT_EXTRA )) {
            mRecipeId = getArguments().getInt( DataUtilities.ID_INTENT_EXTRA );
        }

        if (getArguments() != null && getArguments().containsKey( DataUtilities.PAGER_ORDER_EXTRA )) {
            mStepOrder = getArguments().getInt( DataUtilities.PAGER_ORDER_EXTRA );
        }


        mDescriptionTV = rootView.findViewById(R.id.tv_step_description);
        mPlayerIV = rootView.findViewById(R.id.ep_step_player);

        mMediaSession = ExoplayerUtilities.initializeMediaSession(getContext(), new MySessionCallback());
        mMediaSession.setActive(true);


        stepsViewModel();

        return rootView;
    }

    private void stepsViewModel() {
        RecipeDatabase mDb = RecipeDatabase.getInstance(getContext());

        GetStepsViewModelFactory factory = new GetStepsViewModelFactory(mDb, mRecipeId, mStepOrder);
        final GetStepsViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetStepsViewModel.class);

        viewModel.getStepsOrder().observe(this, new Observer<RecipeSteps>() {
            @Override
            public void onChanged(@Nullable RecipeSteps recipeStep) {
                populateViews(recipeStep);

            }
        });
    }

    private void populateViews(@Nullable RecipeSteps recipeStep) {
        Timber.d("StepViewPager: viewModel -> %s", recipeStep);

        mDescriptionTV.setText(recipeStep.getStepDescription());

        String stepVideoUrl = null;
        if (!TextUtils.isEmpty(recipeStep.getStepVideoUrl())) {
            stepVideoUrl = recipeStep.getStepVideoUrl();
        } else if (!TextUtils.isEmpty(recipeStep.getStepThumbnailUrl())) {
            stepVideoUrl = recipeStep.getStepThumbnailUrl();
        }
        if (stepVideoUrl == null) {
            mPlayerIV.setVisibility(View.GONE);
        } else {
            ExoplayerUtilities.initializePlayer(stepVideoUrl, getContext(), mPlayerIV, mExoPlayer, this);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
