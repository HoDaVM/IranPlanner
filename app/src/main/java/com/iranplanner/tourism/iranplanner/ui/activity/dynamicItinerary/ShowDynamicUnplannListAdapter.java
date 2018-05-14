package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.iranplanner.tourism.iranplanner.R;
import java.util.List;
import entity.DayPosition;


/**
 * Created by Hoda on 10/01/2017.
 */
public class ShowDynamicUnplannListAdapter extends RecyclerView.Adapter<ShowDynamicUnplannListAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<entity.DayPosition> DayPosition;


    public ShowDynamicUnplannListAdapter(Activity a, List<DayPosition> DayPosition, Context context) {
        this.DayPosition = DayPosition;
        this.context = context;

        Activity activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ShowDynamicUnplannListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.recycler_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShowDynamicUnplannListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txtHeader.setText(DayPosition.get(i).getTitle());
    }


    @Override
    public int getItemCount() {
        return DayPosition.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        private TextView txtText, txtHeader;
        private ProgressBar imageLoading;
        private ImageButton imgDelete;


        public ViewHolder(View view) {
            super(view);
            txtText =  view.findViewById(R.id.txtTitle);
            txtHeader =  view.findViewById(R.id.txtHeader);
            image = view.findViewById(R.id.image);
            imgDelete = view.findViewById(R.id.imgDelete);
        }
    }


}


