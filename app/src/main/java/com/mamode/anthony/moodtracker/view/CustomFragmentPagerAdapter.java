package com.mamode.anthony.moodtracker.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mamode.anthony.moodtracker.controller.SmileyFragment;
import com.mamode.anthony.moodtracker.model.MoodTypes;

/**
 * The CustomFragmentPagerAdapter generates the fragments which will be used by
 * the VerticalViewPager. For doing so, it needs a FragmentManager, passed as
 * argument in is constructor.
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /*
     *Return the number of view available.
     *Each fragment is associated to an int (his position).
     */
    @Override
    public int getCount() {
        return MoodTypes.getCount();
    }


    //Return the SmileyFragment according to his position.
    @Override
    public Fragment getItem(int position) {
        if (MoodTypes.isValid(position)) {
            return SmileyFragment.newInstance(
                    MoodTypes.getName(position), //Each position correspond to a MoodType
                    MoodTypes.getColor(position)
            );
        }
        return null;
    }
}
