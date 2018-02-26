package com.mamode.anthony.moodtracker.model;

/**
 * A mood is defined by its moodType (int), which determines how happy is the mood.
 * When a mood is instantiated, its with a date (dd MM yyyy) and a note (which could be a empty string)
 **/
public class Mood {
    private String mDate;
    private String mNote;
    private int mMoodType;

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

    public String getDateMood(){
        return mDate;
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
