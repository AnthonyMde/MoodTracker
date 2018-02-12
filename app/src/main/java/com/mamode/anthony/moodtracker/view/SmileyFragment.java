package com.mamode.anthony.moodtracker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mamode.anthony.moodtracker.R;

/**
 * Created by Anthony on 18/01/2018.
 */

public class SmileyFragment extends Fragment {

    private static final String MOOD = "mood";
    private static final String BACKGROUND = "background";
    public static final String POSITION = "position";

    private String mMood;
    private String mBackground;
    private int mPosition;

    private View mView;
    private ImageView mImageView;

    //Create a new instance of SmileyFragment, initialized to show the mood and the background color
    public static SmileyFragment newInstance(String mood, String background) {
        SmileyFragment smileyFragment = new SmileyFragment();

        //Create a Bundle to stock mood and background
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
        mPosition = getArguments().getInt(POSITION);
        mMood = getArguments().getString(MOOD);
        mBackground = getArguments().getString(BACKGROUND);
    }

    /**
     * Override Fragment's onCreateView method.
     * The onCreateView() method is called when it's time to the fragment to create is UI for the
     * first time.
     * The (@param inflater) is used to inflate smiley_fragment view, which will be used as layout
     * for our fragment.
     * The (@param container) is the fragment's view attached layout (activity_main)
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.smiley_fragment, container, false);
        mView.setBackgroundColor(getResources().getColor(getResources().getIdentifier(mBackground, "color", getActivity().getPackageName())));

        //mImageView corresponding to the fragment image
        mImageView = mView.findViewById(R.id.main_page_fragment_image_view);
        mImageView.setImageResource(getResources().getIdentifier("smiley_" + mMood, "drawable", getActivity().getPackageName()));
        return mView;
    }
}