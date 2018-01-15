package com.iranplanner.tourism.iranplanner.ui.fragment.showAttractionList;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import entity.ResultItineraryAttraction;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
// in adapter baraye neshon dadane list az itinerary ha hast.
public class AttractionsListAdapter extends RecyclerView.Adapter<AttractionsListAdapter.ViewHolder> {
    List<ResultItineraryAttraction> attractions;
    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;
    LayoutInflater inflater;

    public AttractionsListAdapter(Activity a, DataTransferInterface dtInterface, List<ResultItineraryAttraction> ResultItineraryAttraction, Context context, int rowLayout) {
        // TODO Auto-generated constructor stub
        this.attractions = ResultItineraryAttraction;
        this.context = context;
        this.rowLayout = rowLayout;
        Activity activity = a;
        this.dtInterface = dtInterface;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public AttractionsListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.fragment_show_attraction_list_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttractionsListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itinerary_attraction_name.setText(attractions.get(i).getAttractionTitle());
        viewHolder.textCityName.setText(attractions.get(i).getCityTitle() + " , " + attractions.get(i).getProvinceTitle());

        int totalMinute = Integer.parseInt(attractions.get(i).getAttarctionEstimatedTime());
        Util.convertMinuteToHour(totalMinute, viewHolder.textTimeDuration);


    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {
        private TextView itinerary_attraction_name;
        private TextView textCityName;
        private TextView textTimeDuration;
        private CircleImageView ImageAttraction;
        private Button navigateBtn;


        public ViewHolder(View view) {
            super(view);
            itinerary_attraction_name = (TextView) view.findViewById(R.id.itinerary_attraction_name);
            textCityName = (TextView) view.findViewById(R.id.textCityName);
            textTimeDuration = (TextView) view.findViewById(R.id.textTimeDuration);
            ImageAttraction = (CircleImageView) view.findViewById(R.id.ImageAttraction);
            navigateBtn= (Button) view.findViewById(R.id.navigateBtn);

        }


    }


}


