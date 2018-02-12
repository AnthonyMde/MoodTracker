package com.mamode.anthony.moodtracker.model;

/**
 * Created by Anthony on 05/02/2018.
 */

public class Mood {
    private String mDate;
    private String mNote;
    private int mMoodType;

    @Override
    public String toString() {
        return "Mood{" +
                "mDate='" + mDate + '\'' +
                ", mNote='" + mNote + '\'' +
                ", mMoodType=" + mMoodType +
                '}';
    }

    public Mood(int moodType, String date, String note) {
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

    public String getDate(){
        return mDate;
    }

    public void setDate( String moodDate){
        mDate = moodDate;
    }
}
