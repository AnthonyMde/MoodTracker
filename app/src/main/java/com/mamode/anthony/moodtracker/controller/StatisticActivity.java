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
import com.mamode.anthony.moodtracker.model.DataHolder;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {
    private ArrayList<PieEntry> mHistoricPie = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_layout);

        fillHistoricPie();

        PieDataSet pieDataSet = new PieDataSet(mHistoricPie, "");
        pieDataSet.setColors(MOODS_COLORS);

        final PieChart chart = new PieChart(this);
        setContentView(chart);
        PieData data = new PieData(pieDataSet);
        chart.setData(data);

        Description desc = new Description();
        desc.setText("Statistics of 30 last moods");
        desc.setTextSize(60f);
        desc.setPosition(1200f, 150f);

        //Set legend size
        pieDataSet.setFormSize(20f);
        pieDataSet.setValueTextSize(20f);

        chart.setDescription(desc);
        chart.setEntryLabelColor(Color.parseColor("#000000"));
        chart.setDrawEntryLabels(false);
        chart.setUsePercentValues(true);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterText("%");
        chart.setCenterTextSize(60f);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static final int[] MOODS_COLORS = {
            Color.parseColor("#ffde3c50"), Color.parseColor("#ff9b9b9b"), Color.parseColor("#a5468ad9"),
            Color.parseColor("#ffb8e986"), Color.parseColor("#fff9ec4f"),
    };

    /*private void fillPercentageTab(){
        int totalCount = 0;
        for (int i = 0; i < 5; i++){
            totalCount = totalCount + DataHolder.sStatisticCounterTab[i];
        }
        for (int i = 0; i<5; i++){
            mPercentageTab[i] = (DataHolder.sStatisticCounterTab[i] * 100) / totalCount;
        }
    }*/

    private void fillHistoricPie() {
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[0], "SAD", 0));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[1], "DISAPPOINTED", 1));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[2], "NORMAL", 2));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[3], "HAPPY", 3));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[4], "SUPER HAPPY", 4));
    }
}
