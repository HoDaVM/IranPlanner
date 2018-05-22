package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import java.util.List;
import entity.ResultItnListUser;
import tools.Util;


/**
 * Created by Hoda on 10/01/2017.
 */
public class ShowDynamicListAdapter extends RecyclerView.Adapter<ShowDynamicListAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<ResultItnListUser> resultItineraryListUser;

    public ShowDynamicListAdapter(Activity a, List<ResultItnListUser> resultItineraryListUser, Context context) {
        this.resultItineraryListUser = resultItineraryListUser;
        this.context = context;

        Activity activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ShowDynamicListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShowDynamicListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txtTitle.setText(resultItineraryListUser.get(i).getItnTitle());
        if (resultItineraryListUser.get(i).getItnInside().equals("1")) {
            viewHolder.checkBox.setChecked(true);
            viewHolder.checkBox.setClickable(false);
        }
        if (resultItineraryListUser.get(i).getImageUrl() != null)
            Util.setImageView(resultItineraryListUser.get(i).getImageUrl(), context, viewHolder.img, null);
    }


    @Override
    public int getItemCount() {
        return resultItineraryListUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;

        private TextView txtTitle;
        private CheckBox checkBox;


        public ViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);
            checkBox = view.findViewById(R.id.checkBoxExist);
            img = view.findViewById(R.id.img);

        }
    }

}


