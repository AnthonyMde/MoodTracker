package com.mamode.anthony.moodtracker.model;

import java.util.Calendar;

/**
 * Created by Anthony on 05/02/2018.
 */

public class Mood {
    private Calendar mDate;
    private String mNote;
    private int mMoodType;

    public Mood(int moodType, Calendar date, String note) {
        mMoodType = moodType;
        mDate = date;
        mNote = note;
    }

    public String getNote() {
        return mNote;
    }

    public int getMoodType() {
        return mMoodType;
    }

    public Calendar getDate(){
        return mDate;
    }

    public void setDate(Calendar moodDate){
        mDate = moodDate;
    }

    @Override
    public String toString() {
        return "Mood{" +
                "mDate='" + mDate + '\'' +
                ", mNote='" + mNote + '\'' +
                ", mMoodType=" + mMoodType +
                '}';
    }
}
