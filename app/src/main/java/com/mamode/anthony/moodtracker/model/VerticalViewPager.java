package com.mamode.anthony.moodtracker.model;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Anthony on 14/01/2018.
 * VerticalViewPager is an inheriting ViewPager class allowing to scroll up and down instead of
 * right and left between fragments
 */

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public int getCurrentItem() {
        return super.getCurrentItem();
    }


     /*Set a PageTransformer that will be called whenever the scroll position is changed
     ReverseDrawingOrder : true if the PageTransformer needs views to be drawn from last to first
     LayerType : code 0 = none*/
    private void init() {
        setPageTransformer(false, new VerticalPageTransformer(), 0);
    }

    //Method to intercept touch event
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final boolean interceptTouch = super.onInterceptTouchEvent(switchXY(event));
        switchXY(event);
        return interceptTouch;
    }

    //Method to handle touch screen motion events
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final boolean handleTouch = super.onTouchEvent(switchXY(event));
        switchXY(event);
        return handleTouch;
    }

    //Method to switch X and Y axis
    private MotionEvent switchXY(MotionEvent event) {
        final float x = getWidth()*event.getY() / getHeight();
        final float y = getHeight()*event.getX() /getWidth();
        event.setLocation(x,y);
        return event;
    }

    /**
     * VerticalPageTransformer displays fragments from top to bottom (instead right to left)
     * thanks to its abstract method transformPage
     **/
    private static final class VerticalPageTransformer implements ViewPager.PageTransformer {
        //Provide the actual view and its relative position to the screen as arguments
        @Override
        public void transformPage(View view, float position) {
            //Counteract the screen slide on X with a negative X translation
            view.setTranslationX(-1*view.getWidth()*position);
            //Add a Y translation for the view (fragment)
            view.setTranslationY(view.getHeight()*position);
        }
    }
}
