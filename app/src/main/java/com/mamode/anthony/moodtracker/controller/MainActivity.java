package com.mamode.anthony.moodtracker.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mamode.anthony.moodtracker.R;
import com.mamode.anthony.moodtracker.model.DataHolder;
import com.mamode.anthony.moodtracker.model.Mood;
import com.mamode.anthony.moodtracker.model.MoodTypes;
import com.mamode.anthony.moodtracker.model.VerticalViewPager;
import com.mamode.anthony.moodtracker.view.ViewPagerAdapter;


/**
 * MainActivity extends FragmentActivity instead of Activity in order to use fragments.
 */

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private EditText mEditTextNote;
    private ImageButton mHistoryButton;
    private ImageButton mAddNoteButton;
    private ImageButton mPieButton;

    /**
     * When the application is launched
     * Bundle allows to keep the application state information when the app is stopped
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DataHolder.clearMoods(this);
        //DataHolder.demoMoodHistoric(this);
        /*DataHolder.sStatisticCounterTab[0] = 2;
        DataHolder.sStatisticCounterTab[1] = 5;
        DataHolder.sStatisticCounterTab[2] = 4;
        DataHolder.sStatisticCounterTab[3] = 9;
        DataHolder.sStatisticCounterTab[4] = 5;*/

        DataHolder.loadData(this);
        setContentView(R.layout.activity_main);
        Log.i("CurrentMoodSize :", DataHolder.sCurrentDayMood.toString());

        //Wire widgets
        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mAddNoteButton = findViewById(R.id.activity_main_note_add_btn);
        mHistoryButton = findViewById(R.id.activity_main_history_btn);
        mPieButton = findViewById(R.id.activity_main_pie_btn);

         /*Set the com.example.anthony.moodtrackerbeta.ViewPagerAdapter to show fragments.
        The ViewPagerAdapter constructor needs a FragmentManager object as argument.
        To provide it, we use the method "getSupportFragmentManager()".
        This FragmentManager object is necessary to keep in memory each fragment and to manage them.
        */
        mVerticalViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Set the pager to the current mood fragment
        //Or : set Happy by default
        if (!DataHolder.sCurrentDayMood.isEmpty()) {
            mVerticalViewPager.setCurrentItem(DataHolder.sCurrentDayMood.get(0).getMoodType());
        } else {
            mVerticalViewPager.setCurrentItem(MoodTypes.Happy);
        }

        //Set listener to our smiley
        mPieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pieIntent = new Intent(MainActivity.this, StatisticActivity.class);
                startActivity(pieIntent);
                System.out.println("DO SOMETHING !!!!!");
            }
        });

        //Set listener and action to our mainActivity buttons
        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNoteDialogue();
            }
        });
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });

        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMoodWhenLeave();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveMoodWhenLeave();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveMoodWhenLeave();
        System.out.println("MainActivity::onDestroy()");
    }


    public void addNoteDialogue() {
        //Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        builder.setView(dialogView);

        //Wire widget
        mEditTextNote = dialogView.findViewById(R.id.alert_dialog_edit_text);

        //Set positive button action : save data
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String note = mEditTextNote.getText().toString();
                DataHolder.addMood(MainActivity.this, new Mood(mVerticalViewPager.getCurrentItem(), DataHolder.getCurrentTime(), note));

                Toast.makeText(MainActivity.this, "Your note has been saved", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Nothing happen, just close the AlertDialog
            }
        });
        builder.show();
    }

    private void saveMoodWhenLeave(){
        if (DataHolder.sCurrentDayMood.size() == 1) {
            if (DataHolder.getCurrentMood().getMoodType() != mVerticalViewPager.getCurrentItem()) {
                DataHolder.sCurrentDayMood.clear();
                DataHolder.addMood(this, new Mood(mVerticalViewPager.getCurrentItem(), DataHolder.getCurrentTime(), ""));
                Log.i("Current mood just saved", DataHolder.getCurrentMood().toString());
            }
        }
        if (DataHolder.sCurrentDayMood.size() == 0) {
            DataHolder.addMood(this, new Mood(mVerticalViewPager.getCurrentItem(), DataHolder.getCurrentTime(), ""));
        }
    }
}
