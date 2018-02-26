package com.mamode.anthony.moodtracker.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
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
 * MainActivity is our controller : handle touchscreen to select Mood, add a note or access
 * to other activities
 */

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private EditText mEditTextNote;
    private ImageButton mHistoryButton;
    private ImageButton mAddNoteButton;
    private ImageButton mPieButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHolder.loadData(this);

        //DataHolder.clearMoods(this);
        //DataHolder.demoMoodHistoric(this);
        //DataHolder.demoMoodStatistic();
        //DataHolder.saveData(this);

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

        //Set listener and action to our mainActivity buttons
        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoodNote();
            }
        });
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });
        mPieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pieIntent = new Intent(MainActivity.this, StatisticActivity.class);
                startActivity(pieIntent);
            }
        });

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

    //Show AlertDialog to add note
    //Positive button : save the mood with its note
    public void addMoodNote() {

        //Create the AlertDialog without constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        View dialogView = getLayoutInflater().inflate(R.layout.add_note_alert_dialog, null);
        builder.setView(dialogView);
        mEditTextNote = dialogView.findViewById(R.id.alert_dialog_edit_text);

        //Save data
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String note = mEditTextNote.getText().toString();
                DataHolder.addMood(MainActivity.this, new Mood(mVerticalViewPager.getCurrentItem(), DataHolder.getCurrentTime(), note));
                Toast.makeText(MainActivity.this, "Your note has been saved", Toast.LENGTH_SHORT).show();
            }
        });
        //Close AlertDialog
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    //Save the last mood selected before leaving
    private void saveMoodWhenLeave() {
        if (DataHolder.sCurrentDayMood.size() == 1) {
            if (DataHolder.getCurrentMood().getMoodType() != mVerticalViewPager.getCurrentItem()) {
                DataHolder.sCurrentDayMood.clear();
                DataHolder.addMood(this, new Mood(mVerticalViewPager.getCurrentItem(), DataHolder.getCurrentTime(), ""));
            }
        }
        if (DataHolder.sCurrentDayMood.size() == 0) {
            DataHolder.addMood(this, new Mood(mVerticalViewPager.getCurrentItem(), DataHolder.getCurrentTime(), ""));
        }
    }
}
