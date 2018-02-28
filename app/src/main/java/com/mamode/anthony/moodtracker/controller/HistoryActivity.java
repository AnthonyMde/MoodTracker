package com.mamode.anthony.moodtracker.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mamode.anthony.moodtracker.R;
import com.mamode.anthony.moodtracker.model.DataHolder;
import com.mamode.anthony.moodtracker.view.HistoryRecyclerAdapter;

/**
 * Set our history layout and our RecyclerView
 **/

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView mHistoryRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        mHistoryRecyclerView = findViewById(R.id.history_recycler_view);
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHistoryRecyclerView.setAdapter(new HistoryRecyclerAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataHolder.loadData(this);
        mHistoryRecyclerView.setBackgroundColor(mHistoryRecyclerView.getResources().getColor(R.color.soft_grey));
    }
}
