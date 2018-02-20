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
    public static int[] sStatisticCounterTab = new int[5];
    private static final String KEY_PREF_ARRAY = "KEY_PREF_ARRAY";
    private static final String KEY_PREF_CURRENT_MOOD = "KEY_PREF_CURRENT_MOOD";
    private static final String KEY_PREF_STATISTIC_TAB = "KEY_PREF_STATISTIC_TAB";


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
                sStatisticCounterTab[sCurrentDayMood.get(0).getMoodType()]++; //Increase counterTab to set percentage into our pie chart

                sCurrentDayMood.clear();
                sCurrentDayMood.add(mood);
            } else {
                sStatisticCounterTab[sCurrentDayMood.get(0).getMoodType()]++; //Increase counterTab to set percentage into our pie chart
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
        String statisticTab = gson.toJson(sStatisticCounterTab);
        editor.putString(KEY_PREF_ARRAY, arrayMood);
        editor.putString(KEY_PREF_CURRENT_MOOD, currentMood);
        editor.putString(KEY_PREF_STATISTIC_TAB, statisticTab);
        editor.apply();
        Log.i("ArrayMood check stock :", arrayMood);
        Log.i("CurrentMood is store :", currentMood);
        Log.i("Statistic Tab values : ", statisticTab);
    }

    //Deserialize our Mood's ArrayList
    public static void loadData(Activity activity) {
        Log.i("loadData call", "true");
        SharedPreferences sharedPreferences = activity.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Gson gson = new Gson();
        String arrayMood = sharedPreferences.getString(KEY_PREF_ARRAY, null);
        String currentMood = sharedPreferences.getString(KEY_PREF_CURRENT_MOOD, null);
        String statisticTab = sharedPreferences.getString(KEY_PREF_STATISTIC_TAB, null);
        Type type = new TypeToken<ArrayList<Mood>>() {
        }.getType();
        Type type2 = new TypeToken<int[]>(){
        }.getType();

        if (arrayMood != null) {
            sHistoricArrayList = gson.fromJson(arrayMood, type);
        }
        if (currentMood != null) {
            sCurrentDayMood = gson.fromJson(currentMood, type);
        }
        if (statisticTab != null) {
            sStatisticCounterTab = gson.fromJson(statisticTab, type2);
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

    //For demo
    public static void demoMoodHistoric(Activity activity){
        sHistoricArrayList.add(new Mood(3,"12 02 2018", ""));
        sHistoricArrayList.add(new Mood(0,"13 02 2018", "Une très très mauvaise journée..."));
        sHistoricArrayList.add(new Mood(1,"14 02 2018", ""));
        sHistoricArrayList.add(new Mood(3,"15 02 2018", "Le soleil est enfin de retour :)"));
        sHistoricArrayList.add(new Mood(4,"16 02 2018", ""));
        sHistoricArrayList.add(new Mood(2,"17 02 2018", ""));
        sHistoricArrayList.add(new Mood(4,"18 02 2018", "Altered Carbon, trop bonne série !"));
        saveData(activity);
    }
}
