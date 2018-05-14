package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;

import java.util.List;

import tools.Util;


/**
 * Created by Hoda on 10/01/2017.
 */
public class ShowSelectDayDynamicListAdapter extends RecyclerView.Adapter<ShowSelectDayDynamicListAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<String> days;

    public ShowSelectDayDynamicListAdapter(Activity a, List<String> days, Context context) {
        this.days = days;
        this.context = context;

        Activity activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ShowSelectDayDynamicListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.abs_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShowSelectDayDynamicListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txtTitle.setText(Util.persianNumbers(days.get(i)));

    }


    @Override
    public int getItemCount() {

        return days.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txtTitle;


        public ViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);
        }
    }

    public void setItems(List<String> days) {
        this.days = days;
    }
}


