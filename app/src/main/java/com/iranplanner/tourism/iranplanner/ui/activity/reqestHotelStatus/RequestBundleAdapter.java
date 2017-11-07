package com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import entity.ResultReqBundle;
import entity.ResultReqCount;
import tools.Util;

/**
 * Created by h.vahidimehr on 29/08/2017.
 */

public class RequestBundleAdapter extends RecyclerView.Adapter<RequestBundleAdapter.Holder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ResultReqBundle> resultReqBundleList;

    public RequestBundleAdapter(Context context, List<ResultReqBundle> resultReqBundleList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.resultReqBundleList = resultReqBundleList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.content_status_bundle, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setData(resultReqBundleList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultReqBundleList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageHotel;
        private TextView hotelNameTv, hotelRoomNumberTv, txtHotelDateReq;
        private Button btnRemove, btnComplete;
        private ProgressBar imageLoading;

        public Holder(View itemView) {
            super(itemView);
            imageHotel = (ImageView) itemView.findViewById(R.id.imageHotel);
            hotelNameTv = (TextView) itemView.findViewById(R.id.hotelNameTv);
            hotelRoomNumberTv = (TextView) itemView.findViewById(R.id.hotelRoomNumberTv);
            txtHotelDateReq = (TextView) itemView.findViewById(R.id.txtHotelDateReq);
            imageLoading = (ProgressBar) itemView.findViewById(R.id.imageLoading);

            btnRemove = (Button) itemView.findViewById(R.id.requestStatusRowRemoveBtn);
            btnComplete = (Button) itemView.findViewById(R.id.btnComplete);

            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile.ttf");
            hotelRoomNumberTv.setTypeface(tf);
            hotelNameTv.setTypeface(tf);
            txtHotelDateReq.setTypeface(tf);
            btnComplete.setTypeface(tf);
            btnRemove.setTypeface(tf);
        }

        public void setData(ResultReqBundle current) {
            hotelNameTv.setText(current.getBundleRequest().getBundleLodgingTitle());
            String roomNumber = "تعداد اتاق : " + current.getBundleRequest().getBundleDateCount();
            hotelRoomNumberTv.setText(Util.persianNumbers(roomNumber));
            String dateReq = "تاریخ درخواست : " + Util.persianNumbers(Utils.getSimpleDateMilli(Long.valueOf(current.getBundleRequest().getBundleDateFrom())));
            txtHotelDateReq.setText(dateReq);
            Util.setImageView(current.getBundleRequest().getLodgingImgUrl(), context, imageHotel, imageLoading);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.requestStatusRowPaymentBtn:
                    Toast.makeText(context, "Pay for SomeThing", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


}
