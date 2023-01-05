package com.example.kalarilab.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kalarilab.AwardedPostures;
import com.example.kalarilab.Models.AdminPanelModel;
import com.example.kalarilab.ProgressTrackingSystem;
import com.example.kalarilab.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PostViewer extends AppCompatActivity implements View.OnClickListener , ProgressTrackingSystem.ProgressTrackingSystemCallBack {
    private AdminPanelModel adminPanelModel;
    private Toolbar toolbar;
    private StyledPlayerView styledPlayerView;
    private ExoPlayer exoPlayer;
    private MediaItem mediaItem;
    private int position;
    private static final String TAG = "PostViewerDebug";
    private EditText awardedPoints;
    private Button mSendAwardedPointsBtn, mSendAwardedPosturesBtn, mDeletePostBtn;
    private ProgressTrackingSystem progressTrackingSystem;
    private Spinner posturesSpinner;
    private List<String> postures = new ArrayList<>();
    private List<String> unAwardedPosture = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_viewer);
            initHooks();
            bindings();
            runVid();
            getPostures();
        Slidr.attach(this);

    }

    private void getUnAwardedPostures() {
        Thread dataBaseThread = new Thread(new Runnable() {

            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference("Postures").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            if(!postures.contains(ds.getKey()) ){
                                if(!Objects.equals(ds.getKey(), "length")) {

                                    unAwardedPosture.add(ds.getKey());
                                }
                            }
                        }
                     passPosturesToAdapter();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        try {
            dataBaseThread.start();

        } catch (Exception e) {

        }

    }
    private void passPosturesToAdapter() {
        PostViewer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PostViewer.this,android.R.layout.simple_spinner_item,unAwardedPosture);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                posturesSpinner.setAdapter(spinnerArrayAdapter);
            }
        });


    }
    private void getPostures() {

        AwardedPostures awardedPostures = new AwardedPostures();
        Log.d(TAG, adminPanelModel.getUrersIds().toString());
        progressTrackingSystem.getAwardedPostures(adminPanelModel.getUrersIds().get(position),awardedPostures);

        Thread dataBaseThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!awardedPostures.Imported()){
                    synchronized (awardedPostures){
                        try {

                            Log.d("Waiting_thread", "waiting");

                            awardedPostures.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                postures = new ArrayList<String>(Arrays.asList(awardedPostures.getAwardedPostures().split(",")));
                getUnAwardedPostures();

            }
        });

        try {
            dataBaseThread.start();

        } catch (Exception e) {

        }


    }

    private void bindings() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //swiping the top of the page to refresh the page
            @Override
            public void onRefresh() {
                refreshCurrActivity();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mSendAwardedPointsBtn.setOnClickListener(this);
        mSendAwardedPosturesBtn.setOnClickListener(this);
        mDeletePostBtn.setOnClickListener(this);


    }


    private void initHooks() {
       adminPanelModel = (AdminPanelModel) getIntent().getSerializableExtra("adminPanelModel");
       position = getIntent().getIntExtra("position", 0);
        toolbar = findViewById(R.id.topAppBar);
        styledPlayerView = findViewById(R.id.playerView);

        exoPlayer = new ExoPlayer.Builder(this).build();

        styledPlayerView.setPlayer(exoPlayer);
        awardedPoints = findViewById(R.id.number_of_points);
        mSendAwardedPointsBtn = findViewById(R.id.send_awarded_points);
        progressTrackingSystem = new ProgressTrackingSystem(this, this);
        posturesSpinner = findViewById(R.id.posturesSpinner);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        mDeletePostBtn = findViewById(R.id.deletePost);
        mSendAwardedPosturesBtn = findViewById(R.id.send_awarded_postures);
    }
    private void runVid() {
        try {

            Log.d(TAG, adminPanelModel.getUrersIds().get(position ));
            mediaItem = MediaItem.fromUri(Uri.parse( adminPanelModel.getUris().get(position)));

            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("uriDebug", "Error : " + e.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_awarded_points:
                sendPoints();
                break;
            case R.id.send_awarded_postures:
                sendPosture();
                break;
            case R.id.deletePost:
                deletePost();
                break;
        }

    }

    private void deletePost() {
        String vid_path = adminPanelModel.getUrersIds().get(position)+"/"+adminPanelModel.getLevels().get(position)+"/"+adminPanelModel.getChallenges().get(position);
        StorageReference videoRef = storage.getReference().child(vid_path);
        videoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                String targetEntry = adminPanelModel.getUrersIds().get(position)+adminPanelModel.getLevels().get(position)+adminPanelModel.getChallenges().get(position);
                FirebaseDatabase.getInstance().getReference("Posts").child(targetEntry).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostViewer.this, "Failed to delete vide, please contact the developer", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostViewer.this, "Failed to delete vide, please contact the developer", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendPosture() {
        progressTrackingSystem.sendPosture(posturesSpinner.getSelectedItem().toString(), adminPanelModel.getUrersIds().get(position));
    }

    private void sendPoints() {
        if (Integer.parseInt(awardedPoints.getText().toString()) < 30 && Integer.parseInt(awardedPoints.getText().toString()) != 0) {
            progressTrackingSystem.addAwardedPoints(Integer.parseInt(awardedPoints.getText().toString()), adminPanelModel.getUrersIds().get(position));
        }else Toast.makeText(this, "Number of points is higher than the maximum", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void awardedPointsSuccessfully() {
        Toast.makeText(this, "Points awarded Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void awardedPosturesSuccessfully() {
        refreshCurrActivity();
        Toast.makeText(this, "Posture awarded Successfully!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void failedAwardingPoints() {
        Toast.makeText(this, "Failed awarding Points!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void failedAwardingPostures() {
        Toast.makeText(this, "Failed awarding Postures!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateHomeFragment() {

    }


    private void refreshCurrActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}