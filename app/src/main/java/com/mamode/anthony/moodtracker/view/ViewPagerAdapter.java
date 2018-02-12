package com.mamode.anthony.moodtracker.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mamode.anthony.moodtracker.model.MoodTypes;
import com.mamode.anthony.moodtracker.model.DataHolder;

/**
 * Created by Anthony on 14/01/2018.
 *
 * The ViewPagerAdapter generates the fragments which will be used by
 * the VerticalViewPager. For doing so, it needs a FragmentManager, passed as
 * argument in is constructor.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    /*
     *Override the PagerAdapter method. Return the number of view available.
     *Each fragment is associated to a number (his position).
     */
    public int getCount() {
        return MoodTypes.getCount();
    }

    @Override
    /*
    *Override an abstract method from FragmentPagerAdapter. When the method is called,
    *we return the SmileyFragment according to his position.
     */
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
