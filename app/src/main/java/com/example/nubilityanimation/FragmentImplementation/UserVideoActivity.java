package com.example.nubilityanimation.FragmentImplementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.drm.DrmStore;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Adapter.UserVideoAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
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
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UserVideoActivity extends AppCompatActivity implements RecyclarViewInterface {

    private boolean play_pause=false;
    private boolean fulscreen=false;
    private ProgressBar mProgressBar;
    private SimpleExoPlayer mSimpleExoPlayer;
    private PlayerView mPlayerView ;
    private ImageView ful_screen,forword,backword,playpause;
    private RatingBar mRatingBar;
    private RecyclerView mRecyclerView;
    private TextView number;
    private UserVideoAdapter mUserVideoAdapter;
    private List<UserVideoThumbnail> mThumbnails;
    private DatabaseReference mReference;
    private String id,ratingnumber,videoURLS,pictureURL;

    @Override
    protected void onPause() {
        super.onPause();
        mSimpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
        mSimpleExoPlayer.setPlayWhenReady(true);
        playpause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.play_btn));
        play_pause=false;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_video);
        init();
        LoadControl loadControl= new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        mSimpleExoPlayer= ExoPlayerFactory.newSimpleInstance(getApplicationContext(),trackSelector,loadControl);
        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),"user");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURLS),defaultDataSourceFactory,extractorsFactory,null,null);
        mPlayerView.setPlayer(mSimpleExoPlayer);
        mPlayerView.setKeepScreenOn(true);
        mSimpleExoPlayer.prepare(mediaSource);
        mSimpleExoPlayer.setPlayWhenReady(true);
        mSimpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                          if (playbackState==Player.STATE_BUFFERING)
                          {
                              mProgressBar.setVisibility(View.VISIBLE);
                          }
                          else if (playbackState==Player.STATE_READY)
                          {
                              mProgressBar.setVisibility(View.GONE);
                          }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play_pause==false)
                {
                    playpause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.pause_btn_click));
                    mSimpleExoPlayer.setPlayWhenReady(false);
                    play_pause=true;
                }
                else
                {
                    playpause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.play_btn));
                    mSimpleExoPlayer.setPlayWhenReady(true);
                    play_pause=false;
                }
            }
        });
        forword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSimpleExoPlayer.seekTo(mSimpleExoPlayer.getCurrentPosition() + 3000);
            }
        });

        backword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSimpleExoPlayer.seekTo(mSimpleExoPlayer.getCurrentPosition()-3000);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, float rating, boolean fromUser) {
                ratingnumber = String.valueOf(rating);
                number.setText(String.valueOf(rating));
                savingUserRating();

            }
        });

        ful_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fulscreen==false)
                {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if (getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

                    fulscreen = true;
                }
                else
                {

                    fulscreen = false;
                }

            }
        });
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserVideoThumbnail userVideoThumbnail = snapshot.getValue(UserVideoThumbnail.class);
                mThumbnails.add(userVideoThumbnail);
                mRecyclerView.setAdapter(new UserVideoAdapter(mThumbnails,UserVideoActivity.this,UserVideoActivity.this));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot.child("nofOFreview").getValue().toString();
                mRatingBar.setRating(Float.valueOf(n).floatValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    private void savingUserRating() {
        HashMap<String , Object> update = new HashMap<>();
        update.put("nofOFreview",ratingnumber);
        mReference.child(id).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful())
              {
              }
            }
        });
    }

    private void init() {
        Bundle intent= getIntent().getExtras();
        id = intent.getString("user_video");
        videoURLS=intent.getString("vidURL");
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERVIDEO);
        mRecyclerView= findViewById(R.id.user_video_recyclarView);
        mRatingBar=findViewById(R.id.rating_id);
        mThumbnails=new ArrayList<>();
        mPlayerView=findViewById(R.id.userVideoView);
        mProgressBar=findViewById(R.id.video_start_progressbar);
        number=findViewById(R.id.video_rating_number);
        pictureURL=intent.getString("picURL");
        forword=findViewById(R.id.media_controller_clockwise);
        backword=findViewById(R.id.media_controller_anticlockwise);
        playpause=findViewById(R.id.media_controller_play_pause);
        ful_screen = mPlayerView.findViewById(R.id.media_player_full_screen);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSimpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickListner(int position) {
        UserVideoThumbnail userVideoThumbnail = mThumbnails.get(position);
        String id = userVideoThumbnail.getThumbnailid();
        String url = userVideoThumbnail.getVideoURL();
        String img = userVideoThumbnail.getPictureURL();
        Intent intent = new Intent(getApplicationContext(), UserVideoActivity.class);
        intent.putExtra("user_video",id);
        intent.putExtra("vidURL",url);
        intent.putExtra("picURL",img);
             startActivity(intent);
    }
}