package com.example.kalarilab.Activities;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.example.kalarilab.Fragments.FragmentAdapter;
import com.example.kalarilab.R;
import com.example.kalarilab.SessionManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {


    public static  ViewPager viewPager;


    private BottomNavigationView bottomNavigationView;
    private ColorStateList navigationViewColorStateList;
    private FragmentAdapter fragmentAdapter;
    private SessionManagement sessionManagement ;
    private final static String TAG = "MainActivityDebug";

    // FOR NAVIGATION VIEW ITEM ICON COLOR
    int[][] states = new int[][]{
            new int[]{-android.R.attr.state_checked},  // unchecked
            new int[]{android.R.attr.state_checked},   // checked
            new int[]{}                                // default
    };
    // Fill in color corresponding to state defined in state
    int[] colors = new int[]{
            Color.parseColor("#aaa8a8"),
            Color.parseColor("#ce262f"),
            Color.parseColor("#aaa8a8"),
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHooks();//Initiates variables and objects
        bindings();// preform necessary bindings
        receiveIntentFragment();//recieves the fragment intended and runs it


    }





    private void initHooks() {



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationViewColorStateList = new ColorStateList(states, colors);
        viewPager = findViewById(R.id.viewPager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);
        sessionManagement  = new SessionManagement(this);
    }
    private void bindings() {
        bottomNavigationView.setItemIconTintList(navigationViewColorStateList);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // A listener that preforms action on a certain change
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateNavigationBarState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(fragmentAdapter);
        bottomNavigationView.setOnItemSelectedListener(item -> { // sets the fragments to their corresponding item
            switch (item.getItemId()){
                case R.id.home_page:

                    viewPager.setCurrentItem(0);

                    break;
                case R.id.profile_page:
                    viewPager.setCurrentItem(3);

                    break;
                case R.id.premium_page:

                    viewPager.setCurrentItem(2);
                    break;

                case R.id.levels_page:

                    viewPager.setCurrentItem(1);

            }

            return true;
        });


    }






    private void receiveIntentFragment(){
       // receives info about which fragment to run
//
//        String intentFragment = sessionManagement.returnLatestFragment();
//            switch (intentFragment) {
//                case "PROFILE_FRAGMENT":
//                    viewPager.setCurrentItem(3);
//                    break;
//                case "LEVELS_FRAGMENT":
//                    viewPager.setCurrentItem(1);
//                    break;
//                default:
//                    viewPager.setCurrentItem(0);
//                    break;
//
//        }
    }





    public void updateNavigationBarState(int position){
        //updates which icon is currently selected on the bottom bar
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(position);
        item.setChecked(true);


    }

}