package com.udvash.videofolder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.udvash.videofolder.R;

public class PlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
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

        uri = Uri.parse(getIntent().getStringExtra("uri"));

        playerView = findViewById(R.id.player_view);
        exoPlayer = new ExoPlayer.Builder(this).build();

        dataFactory = new DefaultDataSource.Factory(this);
        mediaSource = new ProgressiveMediaSource.Factory(dataFactory)
                .createMediaSource(MediaItem.fromUri(uri));

        exoPlayer.prepare();
        exoPlayer.setMediaSource(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        playerView.setPlayer(exoPlayer);
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