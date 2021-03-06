package com.mamode.anthony.moodtracker.view;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mamode.anthony.moodtracker.R;
import com.mamode.anthony.moodtracker.model.DataHolder;
import com.mamode.anthony.moodtracker.model.Mood;
import com.mamode.anthony.moodtracker.model.MoodTypes;

import java.util.ArrayList;

/**
 * RecyclerViewAdapter class is needed to display our historic ArrayList as a list into the
 * RecyclerView. It manages our data to display them in each RecyclerView's row and set
 * a default behavior when there is no data to display
 **/

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder> {
    private ImageButton mHistoryNoteBtn; //Show a note if there is any
    private TextView mHistoryDateText;
    private String[] weekDayTab = {"Il y a une semaine", "Il y a six jours", "Il y a cinq jours", "Il y a quatre jours", "Il y a trois jours",
            "Avant-hier", "Hier"};

    /**
     * The RecyclerView adapter needs a ViewHolder to display itemView in the RecyclerView
     * We need our proper ViewHolder because ViewHolder class can't be instantiate (abstract class)
     **/
    protected class MyViewHolder extends RecyclerView.ViewHolder {
        private MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    //Create our view holder : set default layout (without any data)
    @Override
    public HistoryRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_recycler_view, parent, false);

        //Wire widget
        mHistoryNoteBtn = view.findViewById(R.id.history_row_image_button);
        mHistoryDateText = view.findViewById(R.id.row_history_date_text);

        //Allows our default row_recycler_view to fit perfectly our recycler view (without any scrolling)
        int height = parent.getMeasuredHeight() / 7;
        int width = parent.getMeasuredWidth();

        view.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        return new MyViewHolder(view);
    }

    /*Called by RecyclerView to display the data at the specified position. This method
    update the contents of the ViewHolder itemView to reflect the item at the given position.*/
    @Override
    public void onBindViewHolder(HistoryRecyclerAdapter.MyViewHolder holder, int position) {

        //Set text by default
        String dateText = weekDayTab[position] + " (vide)";
        mHistoryDateText.setText(dateText);

        //Check if a mood is saved to display it
        final Mood currentMood = isThereAMoodToShow(position);
        if (currentMood != null) {
            int moodId = currentMood.getMoodType();

            //Set text, size and color
            mHistoryDateText.setText(weekDayTab[position]);
            setSizeRow(holder, currentMood);
            holder.itemView.setBackgroundColor(Color.parseColor(MoodTypes.getHexaColor(moodId)));
            mHistoryNoteBtn.setBackgroundColor(Color.parseColor(MoodTypes.getHexaColor(moodId)));

            //If a note is store : show note button and display note when button's pressed
            if (!currentMood.getNote().equals("")) {
                mHistoryNoteBtn.setVisibility(View.VISIBLE);
                mHistoryNoteBtn.setOnClickListener(new View.OnClickListener() {
                    //Custom Toast Message
                    @Override
                    public void onClick(View v) {
                        createToastMessage(v, currentMood);
                    }
                });
            }
        }
    }

    //Set the number of row
    @Override
    public int getItemCount() {
        return 7;
    }

    //Display mood note
    private void createToastMessage(View v, Mood currentItem) {
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        View customToastView = inflater.inflate(R.layout.custom_toast_history, (ViewGroup) v.findViewById(R.id.custom_toast_container));

        TextView toastTextView = customToastView.findViewById(R.id.custom_toast_txt);
        toastTextView.setText(currentItem.getNote());

        Toast toast = new Toast(v.getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToastView);
        toast.show();
    }

    //Make mood date matching row date
    private Mood isThereAMoodToShow(int position) {
        ArrayList<Mood> arrayMood = DataHolder.sHistoricArrayList;
        int i = 0;
        Mood moodForTheRow = null;
        int rowDate = getInvertRowPosition(position); //If Position = 0, rowDate = 7

        while (i < arrayMood.size() && moodForTheRow == null) {
            Mood currentMood = arrayMood.get(i);
            //Compare row date with mood date
            long j = (DataHolder.getDaysDifference(DataHolder.getCurrentTime(), currentMood.getDateMood()));
            if (j == (rowDate)) {
                moodForTheRow = currentMood;
            } else {
                i++;
            }
        }
        return moodForTheRow;
    }

    //Set row size according to the mood
    private void setSizeRow(MyViewHolder holder, Mood currentMood) {
        DisplayMetrics dm = holder.itemView.getResources().getDisplayMetrics();
        int widthRow = dm.widthPixels;
        int heightRow = dm.heightPixels;

        ConstraintLayout.LayoutParams params = new
                ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (currentMood.getMoodType() == 0) {
            params.width = widthRow * (currentMood.getMoodType() + 1) / 4;
        } else {
            params.width = widthRow * (currentMood.getMoodType() + 1) / 5;
        }
        params.height = (heightRow / 7) - (heightRow / 220);
        holder.itemView.setLayoutParams(params);
    }

    //Call to make item position and date text position matching
    //Day 7 == Position 0
    private int getInvertRowPosition(int position) {
        int[] modifiedPositionTab = {7, 6, 5, 4, 3, 2, 1};
        return modifiedPositionTab[position];
    }

}
