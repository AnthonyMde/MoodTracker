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
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class DataHolder {
    public static ArrayList<Mood> sHistoricArrayList = new ArrayList<>();
    public static ArrayList<Mood> sCurrentDayMood = new ArrayList<>();
    private static final String KEY_PREF_ARRAY = "KEY_PREF_ARRAY";
    private static final String KEY_PREF_CURRENT_MOOD = "KEY_PREF_CURRENT_MOOD";


    //Call when the user add a mood
    public static void addMood(Activity activity, Mood mood) {

        //If a mood is already store
        if (sCurrentDayMood.size() == 1) {

            //The mood date is the current day : we just replace the mood by the new one
            if (sCurrentDayMood.get(0).getDateMood().equals(getCurrentTime())) {
                sCurrentDayMood.clear();
                sCurrentDayMood.add(mood);
            }

            //The mood date is passed : we store it to the historic or delete it if it's too old
            if (getDaysDifference(sCurrentDayMood.get(0).getDateMood(), getCurrentTime()) <= 7) {
                clearOldMoods(activity);
                sHistoricArrayList.add(sCurrentDayMood.get(0));
                sCurrentDayMood.clear();
                sCurrentDayMood.add(mood);
            } else {
                sCurrentDayMood.clear();
                sCurrentDayMood.add(mood);
            }
        }

        //If there is no current mood, we simply add it to the currentDayMood array
        if (sCurrentDayMood.size() == 0) {
            sCurrentDayMood.add(mood);
        }

        saveData(activity);
    }

    //Save our mood's ArrayList and CurrentMood into our SharedPreferences
    private static void saveData(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String arrayMood = gson.toJson(sHistoricArrayList);
        String currentMood = gson.toJson(sCurrentDayMood);
        editor.putString(KEY_PREF_ARRAY, arrayMood);
        editor.putString(KEY_PREF_CURRENT_MOOD, currentMood);
        editor.apply();
        Log.i("ArrayMood check stock :", arrayMood);
        Log.i("CurrentMood is store :", currentMood);
    }

    //Deserialize our Mood's ArrayList
    public static void loadData(Activity activity) {
        Log.i("loadData call", "true");
        SharedPreferences sharedPreferences = activity.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Gson gson = new Gson();
        String arrayMood = sharedPreferences.getString(KEY_PREF_ARRAY, null);
        String currentMood = sharedPreferences.getString(KEY_PREF_CURRENT_MOOD, null);
        Type type = new TypeToken<ArrayList<Mood>>() {
        }.getType();

        if (arrayMood != null) {
            sHistoricArrayList = gson.fromJson(arrayMood, type);
        }
        if (currentMood != null) {
            sCurrentDayMood = gson.fromJson(currentMood, type);
        }

        clearOldMoods(activity);
    }

    //Suppress too old moods
    private static void clearOldMoods(Activity activity) {
        //Suppress too old moods of historic
        for (int i = 0; i < sHistoricArrayList.size(); i++) {
            Mood mood = sHistoricArrayList.get(i);
            if (getDaysDifference(mood.getDateMood(), getCurrentTime()) > 7) {
                sHistoricArrayList.remove(i);
            }
        }
        //Suppress old mood from the current day array
        if (sCurrentDayMood.size() == 1) {
            if (!sCurrentDayMood.get(0).getDateMood().equals(getCurrentTime())) {
                sHistoricArrayList.add(sCurrentDayMood.get(0));
                sCurrentDayMood.clear();
            }
        }

        saveData(activity);
    }

    public static Mood getCurrentMood() throws NullPointerException {
        return sCurrentDayMood.get(0);
    }

    public static long getDaysDifference(String currentDay, String dateToCompare) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy", Locale.FRANCE);

        long days = 0;
        try {
            Date date1 = myFormat.parse(dateToCompare);
            Date date2 = myFormat.parse(currentDay);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Log.i("Days: ", String.valueOf(days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static String getCurrentTime() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy", Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    //For testing : Clear our moods Arrays (current day array + historic array)
    public static void clearMoods(Activity activity) {
        sHistoricArrayList.clear();
        sCurrentDayMood.clear();
        saveData(activity);
    }
}
