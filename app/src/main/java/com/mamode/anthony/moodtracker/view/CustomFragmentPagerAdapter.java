package com.mamode.anthony.moodtracker.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mamode.anthony.moodtracker.controller.SmileyFragment;
import com.mamode.anthony.moodtracker.model.MoodTypes;

/**
 * Created by Anthony on 14/01/2018.
 * <p>
 * The CustomFragmentPagerAdapter generates the fragments which will be used by
 * the VerticalViewPager. For doing so, it needs a FragmentManager, passed as
 * argument in is constructor.
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /*
     *Override the PagerAdapter method. Return the number of view available.
     *Each fragment is associated to a number (his position).
     */
    @Override
    public int getCount() {
        return MoodTypes.getCount();
    }

    /*
    *Override an abstract method from FragmentPagerAdapter. When the method is called,
    *we return the SmileyFragment according to his position.
     */
    @Override
    public Fragment getItem(int position) {
        if (MoodTypes.isValid(position)) {
            return SmileyFragment.newInstance(
                    MoodTypes.getName(position),
                    MoodTypes.getColor(position)
            );
        }
        return null;
    }
}
