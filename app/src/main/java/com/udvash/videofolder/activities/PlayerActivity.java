package com.udvash.videofolder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.udvash.videofolder.R;

public class PlayerActivity extends AppCompatActivity {

    private StyledPlayerView playerView;
    private ExoPlayer exoPlayer;
    private MediaSource mediaSource;
    private DataSource.Factory dataFactory;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_player);

        playerView = findViewById(R.id.player_view);
        exoPlayer = new ExoPlayer.Builder(this).build();
        DefaultExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory().setConstantBitrateSeekingEnabled(true);

//        //https://www.youtube.com/watch?v=kq5_Av-WN98
        //dataFactory = new DefaultDataSource.Factory(this);
        dataFactory = new DefaultHttpDataSource.Factory();
        MediaItem item = new MediaItem.Builder()
                .setUri(Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"))
                .setMimeType(MimeTypes.APPLICATION_MPD)
                .build();
        ProgressiveMediaSource mediaSource =
                new ProgressiveMediaSource.Factory(
                        dataFactory, extractorsFactory)
                        .createMediaSource(item);
        exoPlayer.prepare();
        exoPlayer.setMediaSource(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        playerView.setPlayer(exoPlayer);
        //playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer.isPlaying())
            exoPlayer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoPlayer.isPlaying())
            exoPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (exoPlayer.isPlaying()) {
            exoPlayer.release();
            exoPlayer.stop();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}