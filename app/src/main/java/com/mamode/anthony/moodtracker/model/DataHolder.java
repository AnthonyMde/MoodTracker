package com.mamode.anthony.moodtracker.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Anthony on 08/02/2018.
 */


public class DataHolder {
    public static ArrayList<Mood> sMoodArrayList = new ArrayList<>();
    public static ArrayList<Mood> sCurrentDayMood = new ArrayList<>();
    public static final String KEY_PREF_ARRAY = "KEY_PREF_ARRAY";
    public static final String KEY_PREF_CURRENTMOOD = "KEY_PREF_CURRENTMOOD";


    public static Iterable<Mood> getMoods() {
        return sMoodArrayList;
    }

    public static Mood getMood(int index) {
        if ((index >= 0) && (index < sMoodArrayList.size())) {
            return sMoodArrayList.get(index);
        }
        return null;
    }

    public static int getMoodCount() {
        return sMoodArrayList.size();
    }

    public static void clearMoods(Activity activity) {
        sMoodArrayList.clear();
        saveData(activity);
    }

    public static void addMood(Activity activity, Mood mood) {
        //If there is no current mood, we simply add it to the currentDayMood
        if (sCurrentDayMood.isEmpty()){
            sCurrentDayMood.add(mood);
            //If there is one, we replace it
            }else{
                /** TO DELETE THIS LINE**/
                if (!sCurrentDayMood.get(0).getDate().equals(getTimeMood()))
                sCurrentDayMood.remove(0);
                sCurrentDayMood.add(mood);
            }
            /** DELETE THIS LINE **/
            if (mood.getDate().equals("13-02-2018")){
                clearOldMood();
                sMoodArrayList.add(mood);
            }
        saveData(activity);
        Log.i("currentMood", sCurrentDayMood.get(0).toString());
    }

    //Save our moods' ArrayList and CurrentMood into our SharedPreferences
    private static void saveData(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String arrayMood = gson.toJson(sMoodArrayList);
        String currentMood = gson.toJson(sCurrentDayMood);
        editor.putString(KEY_PREF_ARRAY, arrayMood);
        editor.putString(KEY_PREF_CURRENTMOOD, currentMood);
        editor.apply();
    }

    //Deserialize our Moods' ArrayList
    public static void loadData(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Gson gson = new Gson();
        String arrayMood = sharedPreferences.getString(KEY_PREF_ARRAY, null);
        String currentMood = sharedPreferences.getString(KEY_PREF_CURRENTMOOD, null);
        Type type = new TypeToken<ArrayList<Mood>>() {
        }.getType();
        if (arrayMood != null) {
            sMoodArrayList = gson.fromJson(arrayMood, type);
        }
        if (currentMood != null){
            sCurrentDayMood = gson.fromJson(currentMood, type);
        }

        //We check if there is an old mood in currentMood to stock it to our historic
        if (!sCurrentDayMood.isEmpty() && (!sCurrentDayMood.get(0).getDate().equals(getTimeMood()))){
            clearOldMood();
            sMoodArrayList.add(sCurrentDayMood.get(0));
            sCurrentDayMood.remove(0);
            saveData(activity);
        }
    }

    public static void clearOldMood(){
        if (sMoodArrayList.size() == 7)
        sMoodArrayList.remove(0);
    }

    public static int getDaysDifference(Calendar dateToCompare,Calendar currentDay)
    {
        if(dateToCompare==null||currentDay==null)
            return 0;

        Log.v("DateDiff = ", "diff = " + (currentDay.getTimeInMillis() - dateToCompare.getTimeInMillis()) / (1000 * 60 * 60 * 24));
        return (int)( (currentDay.getTimeInMillis() - dateToCompare.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    public static String getTimeMood() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        return dateFormat.format(calendar.getTime());
    }

    /*public static long convertTime(String moodDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        Date date = null;
        try {
            date = sdf.parse(moodDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            return date.getTime();
        } else {
            return -1;
        }
    }*/
}
