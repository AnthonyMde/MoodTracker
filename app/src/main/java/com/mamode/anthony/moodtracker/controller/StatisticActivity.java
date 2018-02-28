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

/**
 * Set the chart pie layout. Use an external library to display and
 * custom the chart pie.
 **/

public class StatisticActivity extends AppCompatActivity {
    private ArrayList<PieEntry> mHistoricPie = new ArrayList<>();
    private PieDataSet mPieDataSet;
    private PieChart mPieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_layout);

        //Create a category for each mood and load their statistics
        fillHistoricPie();

        //DataSet manages the percentage category (there is only this category of variables for the pie chart)
        //Take the statistics values and color for each category
        mPieDataSet = new PieDataSet(mHistoricPie, "");
        mPieDataSet.setColors(MOODS_COLORS);

        //Create and display the object PieChart who's taking all parameters store in the DataSet
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
        mPieChart.setDescription(desc);

        //Set legend size and color
        mPieDataSet.setFormSize(30f);
        mPieChart.setEntryLabelColor(Color.parseColor("#000000"));

        mPieChart.setDrawEntryLabels(false); //Hide Label of values
        mPieChart.setUsePercentValues(true); //Transform values in percentage
        mPieChart.setDragDecelerationFrictionCoef(0.95f); //Decelerate  the chart pie rotation

        //Custom text
        mPieDataSet.setValueTextSize(20f);
        mPieChart.setCenterText("%");
        mPieChart.setCenterTextSize(60f);
    }

    //Add a category for the pie with its associated percentage
    private void fillHistoricPie() {
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[0], "TRISTE", 0));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[1], "DECU", 1));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[2], "NORMAL", 2));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[3], "JOYEUX", 3));
        mHistoricPie.add(new PieEntry(DataHolder.sStatisticCounterTab[4], "HEUREUX", 4));
    }

    //Store colors for our statistics category
    public static final int[] MOODS_COLORS = {
            Color.parseColor("#ffde3c50"), Color.parseColor("#ff9b9b9b"), Color.parseColor("#a5468ad9"),
            Color.parseColor("#ffb8e986"), Color.parseColor("#fff9ec4f"),
    };
}
