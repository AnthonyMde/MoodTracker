package com.mamode.anthony.moodtracker.view;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mamode.anthony.moodtracker.R;
import com.mamode.anthony.moodtracker.controller.HistoryActivity;
import com.mamode.anthony.moodtracker.controller.MainActivity;
import com.mamode.anthony.moodtracker.model.DataHolder;
import com.mamode.anthony.moodtracker.model.Mood;
import com.mamode.anthony.moodtracker.model.MoodTypes;

import java.util.ArrayList;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder> {
    private ImageButton mHistory_comment_btn;
    private TextView mHistory_date_text;
    private TextView mToastTextView;

    @Override
    public HistoryRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_recycler_view, parent, false);


        //Wire widget
        mHistory_comment_btn = view.findViewById(R.id.history_row_image_button);
        mHistory_date_text = view.findViewById(R.id.row_history_date_text);

        //Allows our seven row_recycler_view to fit perfectly our recycler view, without any scrolling
        int height = parent.getMeasuredHeight() / 7;
        int width = parent.getMeasuredWidth();

        view.setLayoutParams(new RecyclerView.LayoutParams(width, height));

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryRecyclerAdapter.MyViewHolder holder, int position) {


        if (position < DataHolder.sMoodArrayList.size()) {
            final Mood currentItem = DataHolder.sMoodArrayList.get(position);
            int moodId = currentItem.getMoodType();
           // if (!currentItem.getDate().equals(DataHolder.getTime())){
            holder.itemView.setBackgroundColor(Color.parseColor(MoodTypes.getParseColor(moodId)));
            mHistory_comment_btn.setBackgroundColor(Color.parseColor(MoodTypes.getParseColor(moodId)));
            mHistory_date_text.setText(String.valueOf(currentItem.getDate()));
            mHistory_date_text.setGravity(Gravity.LEFT);
            mHistory_date_text.setX(40);
            mHistory_date_text.setY(20);



            if (!currentItem.getNote().equals("")) {
                mHistory_comment_btn.setVisibility(View.VISIBLE);
                mHistory_comment_btn.setOnClickListener(new View.OnClickListener() {
                    //Custom Toast Message
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(v.getContext());
                        View customToastView = inflater.inflate(R.layout.custom_toast, (ViewGroup) v.findViewById(R.id.custom_toast_container));

                        mToastTextView = customToastView.findViewById(R.id.custom_toast_txt);
                        mToastTextView.setText(currentItem.getNote());

                        Toast toast = new Toast(v.getContext());
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(customToastView);
                        toast.show();
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
