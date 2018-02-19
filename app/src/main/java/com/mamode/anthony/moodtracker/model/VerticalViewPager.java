package com.mamode.anthony.moodtracker.model;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Anthony on 14/01/2018.
 * VerticalViewPager is an inheriting ViewPager class allowing to scroll up and down instead of
 * right and left.
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

    //The ViewPager.PageTransformer is invoked whenever the page is scrolled
    private static final class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            //To counteract the screen slide on X with a negative X translation
            view.setTranslationX(-1*view.getWidth()*position);
            //To add a Y translation
            view.setTranslationY(view.getHeight()*position);
        }
    }
}
