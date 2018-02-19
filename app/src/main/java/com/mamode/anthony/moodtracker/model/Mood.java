package com.mamode.anthony.moodtracker.model;

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
