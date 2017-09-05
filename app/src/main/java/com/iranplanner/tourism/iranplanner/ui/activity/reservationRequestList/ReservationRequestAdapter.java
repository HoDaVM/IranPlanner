package com.iranplanner.tourism.iranplanner.ui.activity.reservationRequestList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;

import java.util.ArrayList;
import java.util.List;

import entity.ResultReservationReqList;

/**
 * Created by h.vahidimehr on 05/09/2017.
 */

public class ReservationRequestAdapter extends RecyclerView.Adapter<ReservationRequestAdapter.Holder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ResultReservationReqList> reservationReqLists;

    public ReservationRequestAdapter(Context context, List<ResultReservationReqList> reservationReqLists) {
        this.context = context;
        this.reservationReqLists = reservationReqLists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.content_purchase_hotel_list, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ResultReservationReqList current = reservationReqLists.get(position);
        holder.setData(current);
    }

    @Override
    public int getItemCount() {
        return reservationReqLists.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ResultReservationReqList current;
        private TextView tvRequestCode, tvTimeDuration, tvTypeRoom, tvCityName, tvAttractionName;

        public Holder(View itemView) {
            super(itemView);
            tvRequestCode = (TextView) itemView.findViewById(R.id.txtRequestCode);
            tvTimeDuration = (TextView) itemView.findViewById(R.id.textTimeDuration);
            tvTypeRoom = (TextView) itemView.findViewById(R.id.txtTypeRoome);
            tvCityName = (TextView) itemView.findViewById(R.id.txtCityName);
            tvAttractionName = (TextView) itemView.findViewById(R.id.attraction_name);
        }

        public void setData(ResultReservationReqList current) {
            this.current = current;
            tvAttractionName.setText(current.getRequest().getReqLodgingTitle());
        }
    }
}
