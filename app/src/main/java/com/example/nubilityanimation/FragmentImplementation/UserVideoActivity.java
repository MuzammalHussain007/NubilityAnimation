package com.example.nubilityanimation.FragmentImplementation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.UserCommentAdapter;
import com.example.nubilityanimation.Adapter.UserVideoAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.UserCommentVideo;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.Modal.User_Review;
import com.example.nubilityanimation.Modal.User_Watch_Later;
import com.example.nubilityanimation.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserVideoActivity extends AppCompatActivity implements RecyclarViewInterface {

    private boolean play_pause=false;
    private boolean fulscreen=false;
    private ProgressBar mProgressBar;
    private SimpleExoPlayer mSimpleExoPlayer;
    private PlayerView mPlayerView ;
    private RatingBar mRatingBar;
    private ImageView ful_screen,forword,backword,playpause;
    private RecyclerView mRecyclerView;
    private RecyclerView commentshowRecycler;
    private TextView number;
    private UserVideoAdapter mUserVideoAdapter;
    private List<UserVideoThumbnail> mThumbnails;
    private DatabaseReference mReference,userReviewVideos,saveUserComment,userReference , mReferenceFavourite ,mReferenceWatchLator;
    private String id,ratingnumber,videoURLS,pictureURL;
    private EditText send_comment;
    private ImageView imageView;
    private String username,review_id;
    private String commentid;
    private List<UserCommentVideo> mUserComments;
    private RelativeLayout relativeLayout;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_video,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.ic_share:
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserVideoActivity.this);
                String list[] ={"Watch Later","Favourite"};
                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                            {
                                mReferenceWatchLator.child(id).setValue(new User_Watch_Later(id,videoURLS,pictureURL)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful())
                                         {
                                             Toast.makeText(getApplicationContext(),"Video Shared Successfully",Toast.LENGTH_SHORT).show();
                                         }
                                    }
                                });


                                break;
                            }
                            case 1:
                            {
                                mReferenceFavourite.child(id).setValue(new User_Watch_Later(id,videoURLS,pictureURL)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(),"Video Shared Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
               break;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_video);
        init();

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingnumber=String.valueOf(v);
                saveDatabase(ratingnumber);
            }
        });
        userReviewVideos.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (snapshot.exists())
                   {
                       mRatingBar.setVisibility(View.GONE);
                   }
                   else
                   {
                       mRatingBar.setVisibility(View.VISIBLE);
                   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.child("firstname").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = send_comment.getText().toString();
                if (comment=="")
                {
                    Toast.makeText(getApplicationContext(),"You can not send comment",Toast.LENGTH_SHORT).show();
                }
                else
                {

                        String commentid= saveUserComment.push().getKey();
                        UserCommentVideo userCommentVideo = new UserCommentVideo(commentid,id,username,comment,"0");
                        saveUserComment.child(id).child(commentid).setValue(userCommentVideo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {

                                    send_comment.setText("");
                                }

                            }
                        });





                }
            }
        });
        saveUserComment.child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                     UserCommentVideo userCommentVideo = snapshot.getValue(UserCommentVideo.class);
                     mUserComments.add(userCommentVideo);
                commentshowRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                commentshowRecycler.setAdapter(new UserCommentAdapter(getApplicationContext(),mUserComments));
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Video Screen");
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
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mPlayerView.getLayoutParams();


                    params.width = 2400;
                    params.height = 1080;
                    mPlayerView.setLayoutParams(params);

                    fulscreen = true;
                }
                else
                {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if (getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mPlayerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    mPlayerView.setLayoutParams(params);

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


    }

    private void saveDatabase(final String ratingnumber) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View v= getLayoutInflater().inflate(R.layout.custom_alertdialog,null);
        TextView textView = v.findViewById(R.id.custom_textview_alert);
        Button yes = v.findViewById(R.id.custom_alert_yes);
        Button no = v.findViewById(R.id.custom_alert_no);
        alert.setView(v);
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review_id=userReviewVideos.push().getKey();
                userReviewVideos.child(id).child(FirebaseAuth.getInstance().getUid()).child(review_id).setValue(new User_Review(review_id,ratingnumber,id));
                alertDialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();


            }
        });

    }


    private void init() {
        Bundle intent= getIntent().getExtras();
        id = intent.getString("user_video");   // user video id
        videoURLS=intent.getString("vidURL");
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERVIDEO);
        mRecyclerView= findViewById(R.id.user_video_recyclarView);
        mThumbnails=new ArrayList<>();
        mRatingBar=findViewById(R.id.user_video_rating);
        mPlayerView=findViewById(R.id.userVideoView);
        mProgressBar=findViewById(R.id.video_start_progressbar);
        pictureURL=intent.getString("picURL");  // video thumbnail URL
        forword=findViewById(R.id.media_controller_clockwise);
        backword=findViewById(R.id.media_controller_anticlockwise);
        playpause=findViewById(R.id.media_controller_play_pause);
        ful_screen = mPlayerView.findViewById(R.id.media_player_full_screen);
        imageView=findViewById(R.id.send_message_icon);
        send_comment=findViewById(R.id.user_comment_edit_text);
        mUserComments= new ArrayList<>();
        userReviewVideos=FirebaseDatabase.getInstance().getReference(ConstantClass.USERREVIEWVIDEO);
        commentshowRecycler=findViewById(R.id.comment_view_recyclarView);
        relativeLayout = new RelativeLayout(this);
        userReference=FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
        mReferenceWatchLator=FirebaseDatabase.getInstance().getReference(ConstantClass.USERWATCHLATER);
        mReferenceFavourite=FirebaseDatabase.getInstance().getReference(ConstantClass.USERFAVOURITE);
        saveUserComment=FirebaseDatabase.getInstance().getReference(ConstantClass.SAVEUSERVIDEOCOMMENT);

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