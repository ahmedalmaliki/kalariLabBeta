package com.example.kalarilab.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.kalarilab.Models.AuthModel;
import com.example.kalarilab.R;
import com.example.kalarilab.SessionManagement;
import com.example.kalarilab.ViewModels.AuthViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link skinToneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class skinToneFragment extends Fragment implements View.OnClickListener {
    ImageButton st1Btn, st2Btn, st3Btn, st4Btn, st5Btn;
    String gender;
    ImageView base;
    SessionManagement sessionManagement;
    List<ImageButton> imageButtons;
    private AuthViewModel authViewModel;
    private AuthModel authModel1 = new AuthModel();
    private final static String TAG = "SkinFragmentDebug";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public skinToneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment skinToneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static skinToneFragment newInstance(String param1, String param2) {
        skinToneFragment fragment = new skinToneFragment();
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
       View view =  inflater.inflate(R.layout.fragment_skin_tone, container,  false);
        initHooks(view);
      // observeData();
        bindings();
        return view;
    }







    private void bindings() {
        st1Btn.setOnClickListener(this);
        st2Btn.setOnClickListener(this);
        st3Btn.setOnClickListener(this);
        st4Btn.setOnClickListener(this);
        st5Btn.setOnClickListener(this);
    }
//    private void observeData() {
//        authViewModel.getmAuthModel().observe(getActivity(), new Observer<AuthModel>() {
//            @Override
//            public void onChanged(AuthModel authModel) {
//                authModel1 = authModel;
//                gender = authModel.getGender();
//
//                changeIconsAccordingToGender();
//
//            }
//        });
//    }
    private void initHooks(View view) {
        authViewModel = new AuthViewModel();
        authViewModel.setActivity(getActivity());
        try {
            authViewModel.init();

        }catch (Exception e){
            android.util.Log.d(TAG, e.getMessage());
        }
        st1Btn = (ImageButton) view.findViewById(R.id.st1);
        st2Btn = (ImageButton) view.findViewById(R.id.st2);
        st3Btn = (ImageButton) view.findViewById(R.id.st3);
        st4Btn = (ImageButton) view.findViewById(R.id.st4);
        st5Btn = (ImageButton) view.findViewById(R.id.st5);
        base =  getActivity().findViewById(R.id.base);
        sessionManagement = new SessionManagement(getActivity());
        imageButtons = Arrays.asList(st1Btn, st2Btn, st3Btn,st4Btn, st5Btn);
        setGender();
        changeIconsAccordingToGender();
    }

    private void changeIconsAccordingToGender() {

        if(Objects.equals(gender, "M") || Objects.equals(gender,"O")){
            st1Btn.setImageResource(R.drawable.square_small_black_skin);
            st2Btn.setImageResource(R.drawable.square_small_white_skin);
            st3Btn.setImageResource(R.drawable.square_small_light_skin);
            st4Btn.setImageResource(R.drawable.square_small_brown_skin);
            st5Btn.setImageResource(R.drawable.square_small_darker_brown_skin);

        }
        else {
            st1Btn.setImageResource(R.drawable.circle_small_black_skin);
            st2Btn.setImageResource(R.drawable.circle_small_white_skin);
            st3Btn.setImageResource(R.drawable.circle_small_light_skin);
            st4Btn.setImageResource(R.drawable.circle_small_brown_skin);
            st5Btn.setImageResource(R.drawable.circle_small_darker_brown_skin);
        }
    }
    private void setGender() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            gender = bundle.getString("gender", "M");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.st1:
                pickSt1();
                break;
            case R.id.st2:
                pickSt2();
                break;
            case R.id.st3:
                pickSt3();

                break;
            case R.id.st4:
                pickSt4();
                break;
            case R.id.st5:
                pickSt5();
                break;


        }

    }

    private void pickSt5() {

        setBorders(st5Btn);
        sessionManagement.save_skin_tone("db");
        if(gender.equals("F")){
            sessionManagement.save_skin_tone_drawable( R.drawable.sdbf);
            base.setImageResource(R.drawable.sdbf);
        }else {
            switch (sessionManagement.return_clothes()) {
                case "c1":
                    sessionManagement.save_skin_tone_drawable(R.drawable.sdbmc1);

                    base.setImageResource(R.drawable.sdbmc1);
                    break;
                case "c2":
                    sessionManagement.save_skin_tone_drawable(R.drawable.sdbmc2);

                    base.setImageResource(R.drawable.sdbmc2);
                    break;
            }


        }

    }



    private void pickSt4() {
        sessionManagement.save_skin_tone("br");
        setBorders(st4Btn);
        if(gender.equals("F")){
            sessionManagement.save_skin_tone_drawable( R.drawable.sbrf);
            base.setImageResource(R.drawable.sbrf);
        }else {


            switch (sessionManagement.return_clothes()) {
                case "c1":
                    sessionManagement.save_skin_tone_drawable(R.drawable.sbrmc1);

                    base.setImageResource(R.drawable.sbrmc1);
                    break;
                case "c2":
                    sessionManagement.save_skin_tone_drawable(R.drawable.sbrmc2);

                    base.setImageResource(R.drawable.sbrmc2);
                    break;
            }

        }

    }



    private void pickSt3() {
        setBorders(st3Btn);
        sessionManagement.save_skin_tone("l");
        if(gender.equals("F")){
            sessionManagement.save_skin_tone_drawable(R.drawable.slf);
            base.setImageResource(R.drawable.slf);
        }else {
            switch (sessionManagement.return_clothes()) {
                case "c1":
                    sessionManagement.save_skin_tone_drawable(R.drawable.slmc1);

                    base.setImageResource(R.drawable.slmc1);
                    break;
                case "c2":
                    sessionManagement.save_skin_tone_drawable(R.drawable.slmc2);

                    base.setImageResource(R.drawable.slmc2);
                    break;
            }

        }

    }


    private void pickSt2() {
        setBorders(st2Btn);
        sessionManagement.save_skin_tone("w");
        if(gender.equals("F")){
            sessionManagement.save_skin_tone_drawable(R.drawable.swf);
            base.setImageResource(R.drawable.swf);

        }else {
            switch (sessionManagement.return_clothes()) {
                case "c1":
                    sessionManagement.save_skin_tone_drawable(R.drawable.swmc1);

                    base.setImageResource(R.drawable.swmc1);
                    break;
                case "c2":
                    sessionManagement.save_skin_tone_drawable(R.drawable.swmc2);

                    base.setImageResource(R.drawable.swmc2);
                    break;
            }
        }


    }



    private void pickSt1() {
        setBorders(st1Btn);
        sessionManagement.save_skin_tone("b");
        if(gender.equals("F")){
            sessionManagement.save_skin_tone_drawable(R.drawable.sbf);

            base.setImageResource(R.drawable.sbf);
        }else {
            switch (sessionManagement.return_clothes()) {
                case "c1":
                    sessionManagement.save_skin_tone_drawable(R.drawable.sbmc1);

                    base.setImageResource(R.drawable.sbmc1);
                    break;
                case "c2":
                    sessionManagement.save_skin_tone_drawable(R.drawable.sbmc2);

                    base.setImageResource(R.drawable.sbmc2);
                    break;
            }
        }

    }




    private void setBorders(ImageButton btn){
        for(ImageButton button : imageButtons){
            if (button == btn){
                button.setBackground(getResources().getDrawable(R.drawable.image_button_border_color));
            }else{
                button.setBackground(getResources().getDrawable(R.drawable.image_button_border));
            }
        }
    }



}