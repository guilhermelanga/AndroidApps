package com.example.youtubebylanga.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.youtubebylanga.R;
import com.example.youtubebylanga.helper.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity
implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerVideo;
    private String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerVideo = findViewById(R.id.playerVideo);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            idVideo = bundle.getString("idVideo");
            playerVideo.initialize(YoutubeConfig.CHAVE_YOUTUBE_API, this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setFullscreen(true);
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.loadVideo(idVideo);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}