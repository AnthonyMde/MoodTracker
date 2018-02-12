package com.mamode.anthony.moodtracker.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mamode.anthony.moodtracker.R;
import com.mamode.anthony.moodtracker.model.DataHolder;
import com.mamode.anthony.moodtracker.model.Mood;
import com.mamode.anthony.moodtracker.model.VerticalViewPager;
import com.mamode.anthony.moodtracker.view.ViewPagerAdapter;

import java.util.Calendar;


/**
 * MainActivity extends FragmentActivity instead of Activity in order to use fragments.
 */

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private ImageButton mImageButtonAddNote;
    private EditText mEditTextNote;
    private ImageButton mHistoryButton;

    /**
     * When the application is launched
     * Bundle allows to keep the application state information when the app is stopped
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataHolder.loadData(this);
        setContentView(R.layout.activity_main);
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2018, 1, 14);
        DataHolder.getDaysDifference(calendar1, calendar2);

        //Wire widgets
        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mImageButtonAddNote = findViewById(R.id.activity_main_note_add_btn);
        mHistoryButton = findViewById(R.id.activity_main_history_btn);
        mImageButtonAddNote.setOnClickListener(new View.OnClickListener() {
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

    }


    @Override
    protected void onResume() {
        super.onResume();
        /*Set the com.example.anthony.moodtrackerbeta.ViewPagerAdapter to show fragments.
        The ViewPagerAdapter constructor need a FragmentManager object as argument.
        To provide it, we use the method "getSupportFragmentManager()".
        This FragmentManager object is necessary to keep in memory each fragment and to manage them.
        */
        mVerticalViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        //Set the pager to the current mood fragment
        mVerticalViewPager.setCurrentItem(3);

        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                DataHolder.addMood(MainActivity.this, new Mood(mVerticalViewPager.getCurrentItem(), "10-02-18", note));

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


}
