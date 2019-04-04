package com.example.adndi___project2.recipe_utilities;


import android.content.Context;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

/**
 * Classe com os metodos que irão trabalhar com o Exoplayer e MediaSesion
 */
public class ExoplayerUtilities {

    private static final String TAG = ExoplayerUtilities.class.getSimpleName();


    /**
     * Inicializa o Mediassession
     *
     * @param context Contexto Atual
     * @param session o CallBack do MediaSession
     */
    public static MediaSessionCompat initializeMediaSession(Context context, MediaSessionCompat.Callback session) {

        // Create a MediaSessionCompat.
        MediaSessionCompat mediaSession = new MediaSessionCompat(context, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mediaSession.setCallback(session);

        return mediaSession;
    }

    /**
     * Inicializa o ExoPlayer.
     *
     * @param videoUrl o URL do video
     * @param context o contexto Atual
     * @param exoPlayer a Instancia do Exoplayer a ser iniciada
     * @param exoPlayerView view aonde será colocado o Exoplayer
     * @param listener  EventListener de controle do video
     */
    public static void initializePlayer(String videoUrl, Context context, SimpleExoPlayerView exoPlayerView,
                                        SimpleExoPlayer exoPlayer, ExoPlayer.EventListener listener) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);

            Uri videoURI = Uri.parse(videoUrl);

            exoPlayer.addListener(listener);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(false);
        }
    }


    /**
     * Libera ExoPlayer.
     */
    public static void releasePlayer(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    /**
     * Pausa ExoPlayer.
     */
    public static void pausePlayer(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            exoPlayer.getCurrentPosition();
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.release();
        }
    }
}
