package com.example.kalarilab.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.kalarilab.Models.AuthModel;
import com.example.kalarilab.R;
import com.example.kalarilab.SessionManagement;
import com.example.kalarilab.ViewModels.AuthViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClothesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClothesFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageButton c1Btn, c2Btn;
    private String gender;
    private SessionManagement sessionManagement;
    private List<ImageButton> imageButtons;
    private ImageView base;
    private String pickedClothes;
    private AuthViewModel authViewModel;
    private AuthModel authModel1 = new AuthModel();
    private final static String TAG = "ClothesFragmentDebug";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClothesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment clothingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClothesFragment newInstance(String param1, String param2) {
        ClothesFragment fragment = new ClothesFragment();
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
        View view = inflater.inflate(R.layout.fragment_clothes, container, false);
        initHooks(view);
        observeData();
        bindings();
        setGender();
        return view;
    }


    private void observeData() {
        authViewModel.getmAuthModel().observe(getActivity(), new Observer<AuthModel>() {
            @Override
            public void onChanged(AuthModel authModel) {
                authModel1 = authModel;
            }
        });
    }

    private void bindings() {
        c1Btn.setOnClickListener(this);
        c2Btn.setOnClickListener(this);
    }

    private void initHooks(View view) {
        sessionManagement = new SessionManagement(getActivity());
        c1Btn = (ImageButton) view.findViewById(R.id.c1);
        c2Btn = (ImageButton) view.findViewById(R.id.c2);
        base =  getActivity().findViewById(R.id.base);
        imageButtons = Arrays.asList(c1Btn, c2Btn);
        authViewModel = new AuthViewModel();
        authViewModel.setActivity(getActivity());
        try {
            authViewModel.init();

        }catch (Exception e){
            android.util.Log.d(TAG, e.getMessage());
        }
    }

    private void setGender() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            gender = bundle.getString("gender", authModel1.getGender());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.c1:
                setBorders(c1Btn);
                sessionManagement.save_clothes("c1");
                pickedClothes = "c1";
                pickSkinTone();
                break;
            case R.id.c2:
                setBorders(c2Btn);
                sessionManagement.save_clothes("c2");
                pickedClothes = "c2";
                pickSkinTone();
                break;
        }
    }

    private void pickSkinTone() {
        switch (sessionManagement.return_skin_tone()){
            case "b":
                if(!gender.equals("F")){
                    if(Objects.equals(pickedClothes, "c1")){
                        base.setImageResource(R.drawable.sbmc1);
                        sessionManagement.save_skin_tone_drawable(R.drawable.sbmc1);
                                
                    }
                    else {
                        base.setImageResource(R.drawable.sbmc2);
                        sessionManagement.save_skin_tone_drawable(R.drawable.sbmc2);

                    }
                }
            break;
            case "w":
                if(!gender.equals("F")){
                    if(Objects.equals(pickedClothes, "c1")){
                        base.setImageResource(R.drawable.swmc1);
                        sessionManagement.save_skin_tone_drawable(R.drawable.swmc1);
                    }
                    else {
                        base.setImageResource(R.drawable.swmc2);
                        sessionManagement.save_skin_tone_drawable(R.drawable.swmc2);

                    }
                }
            break;
            case "db":
                if(!gender.equals("F")){
                    if(Objects.equals(pickedClothes, "c1")){
                        base.setImageResource(R.drawable.sdbmc1);
                        sessionManagement.save_skin_tone_drawable(R.drawable.sdbmc1);
                    }
                    else {
                        base.setImageResource(R.drawable.sdbmc2);
                        sessionManagement.save_skin_tone_drawable(R.drawable.sdbmc2);

                    }
                }
                break;
            case "br":
                if(!gender.equals("F")){
                    if(Objects.equals(pickedClothes, "c1")){
                        base.setImageResource(R.drawable.sbrmc1);
                        sessionManagement.save_skin_tone_drawable(R.drawable.sbrmc1);
                    }
                    else {
                        base.setImageResource(R.drawable.sbrmc2);
                        sessionManagement.save_skin_tone_drawable(R.drawable.sbrmc2);

                    }
                }
                break;
            case "l":
                if(!gender.equals("F")){
                    if(Objects.equals(pickedClothes, "c1")){
                        base.setImageResource(R.drawable.slmc1);
                        sessionManagement.save_skin_tone_drawable(R.drawable.slmc1);
                    }
                    else {
                        base.setImageResource(R.drawable.slmc2);
                        sessionManagement.save_skin_tone_drawable(R.drawable.slmc2);

                    }
                }
                break;
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