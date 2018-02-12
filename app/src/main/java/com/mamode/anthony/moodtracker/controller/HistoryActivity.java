package com.mamode.anthony.moodtracker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mamode.anthony.moodtracker.R;
import com.mamode.anthony.moodtracker.model.DataHolder;
import com.mamode.anthony.moodtracker.model.Mood;
import com.mamode.anthony.moodtracker.view.HistoryRecyclerAdapter;


public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        final RecyclerView historyRecyclerView = findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(new HistoryRecyclerAdapter());

        DataHolder.loadData(this);
        historyRecyclerView.setBackgroundColor(historyRecyclerView.getResources().getColor(R.color.grey));
    }

}
