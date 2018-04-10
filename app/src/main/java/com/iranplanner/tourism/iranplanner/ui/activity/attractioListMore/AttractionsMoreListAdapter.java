package com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.util.List;

import entity.ResultAttractionList;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
public class AttractionsMoreListAdapter extends RecyclerView.Adapter<AttractionsMoreListAdapter.ViewHolder> {
    List<ResultAttractionList> attractions;
    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;
    LayoutInflater inflater;

    public AttractionsMoreListAdapter(Activity a, DataTransferInterface dtInterface, List<ResultAttractionList> resultAttractionList, Context context, int rowLayout) {
        // TODO Auto-generated constructor stub
        this.attractions = resultAttractionList;
        this.context = context;
        this.rowLayout = rowLayout;
        Activity activity = a;
        this.dtInterface = dtInterface;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public AttractionsMoreListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.content_attraction_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttractionsMoreListAdapter.ViewHolder viewHolder, int i) {
        try {
            viewHolder.attraction_name.setText(attractions.get(i).getResulAttraction().getAttractionTitle());
            viewHolder.textCityName.setText(attractions.get(i).getResulAttraction().getCityTitle() + " , " + attractions.get(i).getResulAttraction().getProvinceTitle());
            viewHolder.textType.setText(attractions.get(i).getResulAttraction().getAttractionItineraryTypeTitle());
            viewHolder.supplierLayoutBtn.setVisibility(View.GONE);

            int totalMinute = Integer.parseInt(attractions.get(i).getResulAttraction().getAttractionEstimatedTime());
            Util.convertMinuteToHour(totalMinute, viewHolder.textTimeDuration);

            if (attractions.get(i).getResulAttraction().getAttractionImgUrl() != null) {
                Util.setImageView(attractions.get(i).getResulAttraction().getAttractionImgUrl(), context, viewHolder.ImageAttraction, viewHolder.imageLoading);
            }
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        try {
            return attractions.size();
        }catch (Exception e){
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {
        private TextView attraction_name;
        private TextView textCityName;
        private TextView textTimeDuration;
        private TextView textType;
        private ImageView ImageAttraction;
        private Button navigateBtn;
        private RelativeLayout supplierLayoutBtn;
        private ProgressBar imageLoading;


        public ViewHolder(View view) {
            super(view);
            attraction_name = (TextView) view.findViewById(R.id.attraction_name);
            textCityName = (TextView) view.findViewById(R.id.textCityName);
            textTimeDuration = (TextView) view.findViewById(R.id.textTimeDuration);
            textType = (TextView) view.findViewById(R.id.textType);
            ImageAttraction = (ImageView) view.findViewById(R.id.ImageAttraction);
            navigateBtn= (Button) view.findViewById(R.id.navigateBtn);
            supplierLayoutBtn= (RelativeLayout) view.findViewById(R.id.supplierLayoutBtn);
            imageLoading= (ProgressBar) view.findViewById(R.id.imageLoading);
        }


    }


}


