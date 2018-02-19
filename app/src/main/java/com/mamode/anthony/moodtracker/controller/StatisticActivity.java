package com.mamode.anthony.moodtracker.controller;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mamode.anthony.moodtracker.R;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {
    private ArrayList<PieEntry> historicPie = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_layout);

        historicPie.add(new PieEntry(3f, "SAD", 0));
        historicPie.add(new PieEntry(5f, "DISAPPOINTED", 1));
        historicPie.add(new PieEntry(9f, "NORMAL", 2));
        historicPie.add(new PieEntry(12f, "HAPPY", 3));
        historicPie.add(new PieEntry(9f, "SUPER HAPPY", 4));


        PieDataSet pieDataSet = new PieDataSet(historicPie, "");
        pieDataSet.setColors(MOODS_COLORS);

        PieChart chart = new PieChart(this);
        setContentView(chart);
        PieData data = new PieData(pieDataSet);
        chart.setData(data);

        Description desc = new Description();
        desc.setText("Mood Statistics");
        desc.setTextSize(60f);
        desc.setPosition(650f, 150f);

        //Set legend size
        pieDataSet.setFormSize(20f);
        pieDataSet.setValueTextSize(20f);

        chart.setDescription(desc);
        chart.setEntryLabelColor(Color.parseColor("#000000"));
        //chart.setBackgroundColor(Color.parseColor("#ff8b8b8b"));
        chart.setDrawEntryLabels(false);
        chart.setUsePercentValues(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static final int[] MOODS_COLORS = {
            Color.parseColor("#ffde3c50"), Color.parseColor("#ff9b9b9b"), Color.parseColor("#a5468ad9"),
            Color.parseColor("#ffb8e986"), Color.parseColor("#fff9ec4f"),
    };
}
