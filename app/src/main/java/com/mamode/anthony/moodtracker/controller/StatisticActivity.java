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
    private PieDataSet mPieDataSet;
    private PieChart mPieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_layout);

        fillHistoricPie();

        //DataSet manage the percentage category, there is only this category of variables for the pie chart
        mPieDataSet = new PieDataSet(mHistoricPie, "");
        mPieDataSet.setColors(MOODS_COLORS);

        //Creating the object PieChart who's taking all parameters store in the DataSet
        mPieChart = new PieChart(this);
        setContentView(mPieChart);
        PieData data = new PieData(mPieDataSet);
        mPieChart.setData(data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Custom the description
        Description desc = new Description();
        desc.setText("Statistiques de vos humeurs");
        desc.setTextSize(60f);
        desc.setPosition(1240f, 150f);

        //Set legend size
        mPieDataSet.setFormSize(20f);
        mPieDataSet.setValueTextSize(20f);

        mPieChart.setDescription(desc);
        mPieChart.setEntryLabelColor(Color.parseColor("#000000"));
        mPieChart.setDrawEntryLabels(false);
        mPieChart.setUsePercentValues(true);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setCenterText("%");
        mPieChart.setCenterTextSize(60f);
    }

    public static final int[] MOODS_COLORS = {
            Color.parseColor("#ffde3c50"), Color.parseColor("#ff9b9b9b"), Color.parseColor("#a5468ad9"),
            Color.parseColor("#ffb8e986"), Color.parseColor("#fff9ec4f"),
    };

    private void fillHistoricPie() {
        //Add a category for the pie with its associated percentage
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[0], "SAD", 0));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[1], "DISAPPOINTED", 1));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[2], "NORMAL", 2));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[3], "HAPPY", 3));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[4], "SUPER HAPPY", 4));
    }
}
