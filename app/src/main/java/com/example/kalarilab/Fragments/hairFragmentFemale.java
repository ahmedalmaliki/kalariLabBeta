package com.example.kalarilab.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.kalarilab.R;
import com.example.kalarilab.SessionManagement;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link hairFragmentFemale#newInstance} factory method to
 * create an instance of this fragment.
 */
public class hairFragmentFemale extends Fragment implements View.OnClickListener {


    int currentHair;
    SessionManagement sessionManagement;




    ImageButton fh1, fh2, fh3, fh4, fh5, fh6, fh7, fh8, fh9, fh10, fh11, fh12,
            fh13, fh14, fh15, fh16, fh17, fh18,fh19, fh20, fh21, fh22, fh23;
    ImageView hair;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public hairFragmentFemale() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hairFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static hairFragmentFemale newInstance(String param1, String param2) {
        hairFragmentFemale fragment = new hairFragmentFemale();
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
        View view =  inflater.inflate(R.layout.fragment_hair_female, container, false);
        initHooks(view);
        getDefaultHair();
        bindings();
        return view;
    }

    private void getDefaultHair() {
        hair.setImageResource(sessionManagement.return_hair());
    }

    private void bindings() {
        fh1.setOnClickListener(this);
        fh2.setOnClickListener(this);
        fh3.setOnClickListener(this);
        fh4.setOnClickListener(this);
        fh5.setOnClickListener(this);
        fh6.setOnClickListener(this);
        fh7.setOnClickListener(this);
        fh8.setOnClickListener(this);
        fh9.setOnClickListener(this);
        fh10.setOnClickListener(this);
        fh11.setOnClickListener(this);
        fh12.setOnClickListener(this);
        fh13.setOnClickListener(this);
        fh14.setOnClickListener(this);
        fh15.setOnClickListener(this);
        fh16.setOnClickListener(this);
        fh17.setOnClickListener(this);
        fh18.setOnClickListener(this);
        fh19.setOnClickListener(this);
        fh20.setOnClickListener(this);
        fh21.setOnClickListener(this);
        fh22.setOnClickListener(this);
        fh23.setOnClickListener(this);
        sessionManagement = new SessionManagement(getActivity());


    }
    private void initHooks(View view) {
        // listView = (ListView) view.findViewById(R.id.hairList);
//        avatarListAdapter = new AvatarListAdapter(getContext(), R.layout.hair_list_layout, hairPictures);
        fh1 = (ImageButton) view.findViewById(R.id.st1);
        fh2 = (ImageButton) view.findViewById(R.id.st2);
        fh3 = (ImageButton) view.findViewById(R.id.st3);
        fh4 = (ImageButton) view.findViewById(R.id.st4);
        fh5 = (ImageButton) view.findViewById(R.id.st5);
        fh6 =(ImageButton) view.findViewById(R.id.fh6);
        fh7 =(ImageButton) view.findViewById(R.id.fh7);
        fh8 =(ImageButton) view.findViewById(R.id.fh8);
        fh9 =(ImageButton) view.findViewById(R.id.fh9);
        fh10 =(ImageButton) view.findViewById(R.id.fh10);
        fh11 =(ImageButton) view.findViewById(R.id.fh11);
        fh12 =(ImageButton) view.findViewById(R.id.fh12);
        fh13 =(ImageButton) view.findViewById(R.id.fh13);
        fh14 =(ImageButton) view.findViewById(R.id.fh14);
        fh15 =(ImageButton) view.findViewById(R.id.fh15);
        fh16 =(ImageButton) view.findViewById(R.id.fh16);
        fh17 =(ImageButton) view.findViewById(R.id.fh17);
        fh18 =(ImageButton) view.findViewById(R.id.fh18);
        fh19 =(ImageButton) view.findViewById(R.id.fh19);
        fh20 =(ImageButton) view.findViewById(R.id.fh20);
        fh21 =(ImageButton) view.findViewById(R.id.fh21);
        fh22 =(ImageButton) view.findViewById(R.id.fh22);
        fh23 =(ImageButton) view.findViewById(R.id.fh23);

        hair =  getActivity().findViewById(R.id.hair);
        sessionManagement = new SessionManagement(getActivity());

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.st1:
                pickfh1();
                currentHair = R.drawable.mh1;
                setCurrentHair(R.drawable.mh1);
                break;
            case R.id.st2:
                pickfh2();
                currentHair = R.drawable.mh2;
                setCurrentHair(R.drawable.mh2);
                break;
            case R.id.st3:
                pickfh3();
                currentHair = R.drawable.mh3;
                setCurrentHair(R.drawable.mh3);
                break;
            case R.id.st4:
                pickfh4();
                currentHair = R.drawable.mh4;
                setCurrentHair(R.drawable.mh4);
                break;
            case R.id.st5:
                pickfh5();
                currentHair = R.drawable.mh5;
                setCurrentHair(R.drawable.mh5);

                break;


        }

    }
    private void pickfh5() {
        setBorderForFH5();
        hair.setImageResource(R.drawable.mh4);
    }

    private void setBorderForFH5() {
        fh1.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh2.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh3.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh5.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh4.setBackground(getResources().getDrawable(R.drawable.image_button_border_color));
    }

    private void pickfh4() {
        setBorderForFH4();
        hair.setImageResource(R.drawable.mh7);
    }

    private void setBorderForFH4() {
        fh1.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh2.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh3.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh5.setBackground(getResources().getDrawable(R.drawable.image_button_border_color));
        fh4.setBackground(getResources().getDrawable(R.drawable.image_button_border));
    }

    private void pickfh3() {

        setBorderForFH3();
        hair.setImageResource(R.drawable.mh4);
    }

    private void setBorderForFH3() {
        fh1.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh2.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh3.setBackground(getResources().getDrawable(R.drawable.image_button_border_color));
        fh5.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh4.setBackground(getResources().getDrawable(R.drawable.image_button_border));
    }

    private void pickfh2() {
        setBorderForFH2();
        hair.setImageResource(R.drawable.mh5);
    }

    private void setBorderForFH2() {
        fh1.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh2.setBackground(getResources().getDrawable(R.drawable.image_button_border_color));
        fh3.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh5.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh4.setBackground(getResources().getDrawable(R.drawable.image_button_border));
    }

    private void pickfh1() {
        setBorderForFH1();
        hair.setImageResource(R.drawable.mh6);

    }

    private void setBorderForFH1() {
        fh1.setBackground(getResources().getDrawable(R.drawable.image_button_border_color));
        fh2.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh3.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh5.setBackground(getResources().getDrawable(R.drawable.image_button_border));
        fh4.setBackground(getResources().getDrawable(R.drawable.image_button_border));
    }
    public int getCurrentHair() {
        return currentHair;
    }

    public void setCurrentHair(int currentHair) {
        this.currentHair = currentHair;
    }

    @Override
    public void onStop() {
        super.onStop();
        sessionManagement.save_hair(getCurrentHair());
    }
}