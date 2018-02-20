package com.mamode.anthony.moodtracker.model;

//For each mood we have a final int
public class MoodTypes {
    public static final int Sad = 0;
    public static final int Disappointed = 1;
    public static final int Normal = 2;
    public static final int Happy = 3;
    public static final int SuperHappy = 4;

    //Keep count of the number of mood available
    public static int getCount() {
        return 5;
    }


    //Return the name according to is fragment position
    public static String getName(int moodType) {
        switch (moodType) {
            case MoodTypes.Sad: return "sad";
            case MoodTypes.Disappointed: return "disappointed";
            case MoodTypes.Normal: return "normal";
            case MoodTypes.Happy: return "happy";
            case MoodTypes.SuperHappy: return "super_happy";
        }
        return "";
    }

    //Return the color name according to is fragment position
    public static String getColor(int moodType) {
        switch (moodType) {
            case MoodTypes.Sad: return "faded_red";
            case MoodTypes.Disappointed: return "warm_grey";
            case MoodTypes.Normal: return "cornflower_blue_65";
            case MoodTypes.Happy: return "light_sage";
            case MoodTypes.SuperHappy: return "banana_yellow";
        }
        return "black";
    }

    public static String getParseColor(int moodType) {
        switch (moodType) {
            case MoodTypes.Sad: return "#ffde3c50";
            case MoodTypes.Disappointed: return "#ff9b9b9b";
            case MoodTypes.Normal: return "#a5468ad9";
            case MoodTypes.Happy: return "#ffb8e986";
            case MoodTypes.SuperHappy: return "#fff9ec4f";
        }
        return "black";
    }

    //Checked our mood position to avoid bug
    public static boolean isValid(int moodTypePosition)
    {
        return ((moodTypePosition >= 0) && (moodTypePosition < getCount()));
    }
}
