package com.example.nubilityanimation.FragmentImplementation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserVideoActivity extends AppCompatActivity {

    private boolean flag=false;
    private VideoView mVideoView;
    private MediaController mMediaController;
    private ProgressBar mProgressBar;

    private RatingBar mRatingBar;
    private RecyclerView mRecyclerView;
    private TextView number;
    private DatabaseReference mReference;
    private String id,ratingnumber,videoURLS,pictureURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_video);
        init();

        mVideoView.setMediaController(mMediaController);
        mVideoView.setZOrderOnTop(true);
        mVideoView.setVideoURI(Uri.parse(videoURLS));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.pause();
                mVideoView.seekTo(mVideoView.getCurrentPosition()+20);
                mProgressBar.setVisibility(View.GONE);
            }
        });
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingnumber = String.valueOf(rating);
                number.setText(String.valueOf(rating));
            }
        });



    }

    private void init() {
        Bundle intent= getIntent().getExtras();
        id = intent.getString("user_video");
        videoURLS=intent.getString("vidURL");
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERREVIEW);
        mRecyclerView= findViewById(R.id.user_video_recyclarView);
        mRatingBar=findViewById(R.id.rating_id);
        mVideoView=findViewById(R.id.userVideoView);
        mProgressBar=findViewById(R.id.video_start_progressbar);
        mMediaController = new MediaController(this);
        number=findViewById(R.id.video_rating_number);
        pictureURL=intent.getString("picURL");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mProgressBar.setVisibility(View.VISIBLE);
    }
}