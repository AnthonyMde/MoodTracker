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
import java.util.Calendar;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder> {
    private ImageButton mHistory_comment_btn;
    private TextView mHistory_date_text;
    private String[] weekDayTab = {"Il y a une semaine", "Il y a six jours", "Il y a cinq jours", "Il y a quatre jours", "Il y a trois jours",
            "Avant-hier", "Hier"};

    @Override
    public HistoryRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_recycler_view, parent, false);


        //Wire widget
        mHistory_comment_btn = view.findViewById(R.id.history_row_image_button);
        mHistory_date_text = view.findViewById(R.id.row_history_date_text);

        //Allows our default row_recycler_view to fit perfectly our recycler view, without any scrolling
        int height = parent.getMeasuredHeight() / 7;
        int width = parent.getMeasuredWidth();

        view.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryRecyclerAdapter.MyViewHolder holder, int position) {

        //Set the default week text
        String dateText = weekDayTab[position] + " (vide)";
        mHistory_date_text.setText(dateText);

        //Check if there is a mood to show and stock our moodType in moodId
        final Mood currentMood = isThereAMoodToShow(position);
        if (currentMood != null) {
            int moodId = currentMood.getMoodType();


            //------------------Set the text--------------------------
            mHistory_date_text.setText(weekDayTab[position]);

            //------------------Set the size of the row according to the mood-----------------------
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

            //---------------Set the color background for the view and the button-------------------
            holder.itemView.setBackgroundColor(Color.parseColor(MoodTypes.getParseColor(moodId)));
            mHistory_comment_btn.setBackgroundColor(Color.parseColor(MoodTypes.getParseColor(moodId)));

            //---------------Show and then toast the comment if there is any------------------------
            if (!currentMood.getNote().equals("")) {
                mHistory_comment_btn.setVisibility(View.VISIBLE);
                mHistory_comment_btn.setOnClickListener(new View.OnClickListener() {
                    //Custom Toast Message
                    @Override
                    public void onClick(View v) {
                        createToastMessage(v, currentMood);
                    }
                });
            }
        }
    }


    private Mood isThereAMoodToShow(int position) {
        ArrayList<Mood> arrayMood = DataHolder.sHistoricArrayList;
        int i = 0;
        Mood moodForTheRow = null;
        int modifiedPosition = getInvertRowPosition(position);
        while (i < arrayMood.size() && moodForTheRow == null) {
            Mood currentMood = arrayMood.get(i);
            long j = (DataHolder.getDaysDifference(DataHolder.getCurrentTime(),currentMood.getDateMood()));
            if (j == (modifiedPosition)) {
                moodForTheRow = currentMood;
            } else {
                i++;
            }
        }
        return moodForTheRow;
    }

    @Override
    public int getItemCount() {
        return 7;
    }


    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void createToastMessage(View v, Mood currentItem) {
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        View customToastView = inflater.inflate(R.layout.custom_toast, (ViewGroup) v.findViewById(R.id.custom_toast_container));

        TextView toastTextView = customToastView.findViewById(R.id.custom_toast_txt);
        toastTextView.setText(currentItem.getNote());

        Toast toast = new Toast(v.getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToastView);
        toast.show();
    }

    private int getInvertRowPosition(int position) {
        int[] modifiedPositionTab = {7, 6, 5, 4, 3, 2, 1};
        return modifiedPositionTab[position];
    }

}
