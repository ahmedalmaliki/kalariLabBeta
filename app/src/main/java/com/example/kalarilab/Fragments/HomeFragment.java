package com.example.kalarilab.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.kalarilab.AwardedPostures;
import com.example.kalarilab.Activities.PosturesActivity;
import com.example.kalarilab.LessonReached;
import com.example.kalarilab.LevelReached;
import com.example.kalarilab.Models.AuthModel;
import com.example.kalarilab.ProgressTrackingSystem;
import com.example.kalarilab.R;
import com.example.kalarilab.SessionManagement;
import com.example.kalarilab.Streak;
import com.example.kalarilab.TotalPoints;
import com.example.kalarilab.ViewModels.AuthViewModel;
import com.example.kalarilab.WeeklyPoints;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import ru.nikartm.support.ImageBadgeView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener , ProgressTrackingSystem.ProgressTrackingSystemCallBack {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressTrackingSystem progressTrackingSystem;
    private Streak streak;
    private final static String TAG = "HomeFragmentDebug";
    private TextView lessonsProgressText, levelsProgressText, totalPointsTextView, weeklyPointsTextView;
    private CircularProgressIndicator circularProgressIndicatorLessons, circularProgressIndicatorPoints;
    private CardView posturesCard;

    private Activity activity;

    private SessionManagement sessionManagement;
    private ImageBadgeView imageBadgeView;
    private AwardedPostures awardedPostures;
    private  List<String> postures  = new ArrayList<>();
    private AuthViewModel authViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        postponeEnterTransition();
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initHooks(view);
        observeData();
        bindings();
        showPostureImage(sessionManagement.returnLatestAwardedPostureUri());
        startPostponedEnterTransition();
        setLatestAwardedPosture();




        return view;
    }

    private void observeData() {
        authViewModel.getmAuthModel().observe(getViewLifecycleOwner(), new Observer<AuthModel>() {
            @Override
            public void onChanged(AuthModel authModel) {
                Log.d(TAG, String.valueOf(authModel.getPoints()));
                changeLevelReachedText(String.valueOf(authModel.getLevelReached()));
                changeLessonReachedText(String.valueOf(authModel.getLessonReached()));
                changeTotalPointsText(String.valueOf(authModel.getPoints()));
                changeWeeklyPointsText(String.valueOf(authModel.getWeeklyPoints()));
                circularProgressIndicatorLessons.setMax(getNumOfLessons());
                circularProgressIndicatorLessons.setProgress(20); //<-- these two steps are necessary because of a bug in the
                circularProgressIndicatorLessons.setProgress(0); //<-- circularProgress trackers themselves
                circularProgressIndicatorLessons.setProgress(authModel.getLessonReached());
            }
        });
    }


    private void bindings() {





        posturesCard.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //swiping the top of the page to refresh the page
            @Override
            public void onRefresh() {
                refreshPage();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    private void changeWeeklyPointsText(String points) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weeklyPointsTextView.setText(points);
            }
        });
    }



    private void changeTotalPointsText(String points) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                totalPointsTextView.setText("Total points: " + points);
            }
        });
    }

    private void initHooks(View view) {
        authViewModel = new AuthViewModel();
        authViewModel.setActivity(getActivity());
        try {
            authViewModel.init();

        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        progressTrackingSystem = new ProgressTrackingSystem(this,getActivity());
        streak = new Streak(progressTrackingSystem, getActivity());
        lessonsProgressText = (TextView) view.findViewById(R.id.classesProgressText);
        levelsProgressText = (TextView) view.findViewById(R.id.levelsProgressText);
        totalPointsTextView = (TextView) view.findViewById(R.id.totalPoints);
        weeklyPointsTextView = (TextView) view.findViewById(R.id.weeklyPoints);

        circularProgressIndicatorLessons =(CircularProgressIndicator) view.findViewById(R.id.progressCircleClasses);
        circularProgressIndicatorPoints = (CircularProgressIndicator) view.findViewById(R.id.progressCirclePoints);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        posturesCard = (CardView) view.findViewById(R.id.posturesCard);

        activity = this.getActivity();
        sessionManagement = new SessionManagement(getActivity());

        imageBadgeView = (ImageBadgeView) view.findViewById(R.id.ibv_icon2);
    }

    private void refreshPage() {
        //refreshing the current page
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getParentFragmentManager().beginTransaction().detach(this).commitNow();
            getParentFragmentManager().beginTransaction().attach(this).commitNow();
        } else {
            getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.posturesCard:
                imageBadgeView.setBadgeValue(0);
                sessionManagement.saveNumOfAwardedPostures(sessionManagement.returnNumOfAwardedPostures() + numOfNewlyAddedPostures());
                goToPosturesActivity();

                break;


        }

    }





    private void changeLevelReachedText(String level) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                levelsProgressText.setText("Level "+ level);
            }
        });

    }



    private void changeLessonReachedText(String lesson) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lessonsProgressText.setText(lesson+"/"+ getNumOfLessons());
            }
        });

    }

    private int getNumOfLessons() {
        switch (sessionManagement.returnLevel_Reached()){
            case 1:
                return 15;
        }
        return 15;
    }

    private void goToPosturesActivity() {
        Intent intent = new Intent(getActivity(), PosturesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }

    @Override
    public void awardedPointsSuccessfully() {

    }

    @Override
    public void awardedPosturesSuccessfully() {

    }

    @Override
    public void failedAwardingPoints() {

    }

    @Override
    public void failedAwardingPostures() {

    }

    @Override
    public void updateHomeFragment() {
        int badgeValue = imageBadgeView.getBadgeValue();
        if (sessionManagement.returnNumOfAwardedPostures() < postures.size()){
            imageBadgeView.setBadgeValue(badgeValue + numOfNewlyAddedPostures());

        }
        if(awardedPostures.getAwardedPostures().length() != 0) {
            postures = new ArrayList<String>(Arrays.asList(awardedPostures.getAwardedPostures().split(",")));
            replaceImmatureWithMature();
            Log.d(TAG, postures.toString());
        }
        getImage(postures.get(postures.size() - 1));


    }
    public void showPostureImage(String uri){
        Log.d(TAG+"l", uri);

        if(!Objects.equals(uri, "")) {
            Log.d(TAG, uri);
            Glide
                    .with(getActivity())
                    .load(uri) // pass the image url
                    .into(imageBadgeView);
//
        }else imageBadgeView.setImageResource(R.drawable.kalaripayattu);

    }


    private void getImage(String keyTag) {

        Thread dataBaseThread = new Thread(new Runnable() {
            @Override
            public void run() {


                    FirebaseDatabase.getInstance().getReference("Postures").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    if(Objects.equals(ds.getKey(), keyTag)){
                                        sessionManagement.saveLatestAwardedPostureUri(ds.getValue().toString());
                                        Log.d(TAG+"l", ds.getValue().toString());
                                        Glide
                                                .with(getActivity())
                                                .load(ds.getValue()) // pass the image url
                                                .into(imageBadgeView);
                                    }
                                }
                            }catch (Exception e){
                                Log.d(TAG, "No Postures");
                            }

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
            Log.d(TAG, e.getMessage());
        }


    }


    private int numOfNewlyAddedPostures() {

        return postures.size() - sessionManagement.returnNumOfAwardedPostures()  ;
    }



    private void setLatestAwardedPosture() {
        awardedPostures = new AwardedPostures();
        progressTrackingSystem.getAwardedPostures(awardedPostures);
    }
    private void replaceImmatureWithMature() {

        for (Iterator<String> iterator = postures.iterator(); iterator.hasNext(); ) {
            String value = iterator.next();
            if (value.contains("Imature")) {
                if(postures.contains(value.replace("Imature ", ""))){
                    iterator.remove();

                }
            }
        }
    }


}


