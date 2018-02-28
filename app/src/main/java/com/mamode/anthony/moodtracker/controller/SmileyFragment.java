package com.mamode.anthony.moodtracker.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mamode.anthony.moodtracker.R;


public class SmileyFragment extends Fragment {

    private static final String MOOD = "mood";
    private static final String BACKGROUND = "background";
    private String mMood;
    private String mBackground;

    //Create a new instance of SmileyFragment and set mood and background color
    public static SmileyFragment newInstance(String mood, String background) {
        SmileyFragment smileyFragment = new SmileyFragment();

        //Create a Bundle to stock mood and background (we reuse them in onCreate and onCreateView)
        Bundle args = new Bundle();

        //putString() method add a value with a key to the bundle mapping (key, value)
        args.putString(MOOD, mood);
        args.putString(BACKGROUND, background);

        /*
        Supply the constructor args for this fragment. They will be retained across fragment
        destroy and creation.
         */
        smileyFragment.setArguments(args);
        return smileyFragment;
    }

    //Retrieve mood and background color from its arguments
    //We retrieve the args thanks to the key MOOD and BACKGROUND
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMood = getArguments().getString(MOOD);
        mBackground = getArguments().getString(BACKGROUND);
    }

    /*
     * Override Fragment's onCreateView method.
     * The onCreateView() method is called when it's time to the fragment to create is UI for the
     * first time.
     * The (@param inflater) is used to inflate smiley_fragment view, which will be used as layout
     * for our fragment.
     * The (@param container) is the fragment's view attached layout (activity_main)
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView;
        ImageView mImageView;

        mView = inflater.inflate(R.layout.smiley_fragment, container, false);
        mView.setBackgroundColor(getResources().getColor(getResources().getIdentifier(mBackground, "color", getActivity().getPackageName())));

        //mImageView corresponding to the fragment image
        mImageView = mView.findViewById(R.id.main_page_fragment_image_view);
        mImageView.setImageResource(getResources().getIdentifier("smiley_" + mMood, "drawable", getActivity().getPackageName()));
        return mView;
    }
}
