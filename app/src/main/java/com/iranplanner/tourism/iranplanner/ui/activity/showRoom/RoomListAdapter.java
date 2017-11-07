package com.iranplanner.tourism.iranplanner.ui.activity.showRoom;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.LodgingRoomBed;
import entity.LodgingRoomFacility;
import entity.ResultRoom;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {
    private DataTransferInterface dtInterface;
    private LayoutInflater inflater;
    private List<entity.ResultRoom> ResultRoom;
    private Activity activity;


    public RoomListAdapter(Activity activity, DataTransferInterface dtInterface, List<ResultRoom> ResultRoom) {
        this.ResultRoom = ResultRoom;
        this.dtInterface = dtInterface;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public RoomListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.activity_reservation_room_detail, viewGroup, false);
        return new RoomListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.roomType.setText(ResultRoom.get(position).getRoomTitle());
        if (ResultRoom.get(position).getRoomCapacityExtra() != null && !ResultRoom.get(position).getRoomCapacityExtra().equals("0")) {
            viewHolder.txtCapacity.setText(Util.persianNumbers(ResultRoom.get(position).getRoomCapacityAdult()) + " نفر +" + Util.persianNumbers(ResultRoom.get(position).getRoomCapacityExtra()) + " نفر اضافه ");
        } else {
            viewHolder.txtCapacity.setText(Util.persianNumbers(ResultRoom.get(position).getRoomCapacityAdult()) + " نفر ");
        }
        viewHolder.BreakfastHolder.setVisibility(View.VISIBLE);
        List<LodgingRoomFacility> LodgingRoomFacility = ResultRoom.get(position).getLodgingRoomFacility();
        for (entity.LodgingRoomFacility lodgingRoomFacility : LodgingRoomFacility) {
            if (lodgingRoomFacility.getRoomFacilityId().equals("3754")) {
                viewHolder.BreakfastHolder.setVisibility(View.VISIBLE);
            }
            if (lodgingRoomFacility.getRoomFacilityId().equals("3755")) {
                viewHolder.WifiHolder.setVisibility(View.VISIBLE);
            }
        }
        if (ResultRoom.get(position).getLodgingRoomBed().size() > 0) {
            List<LodgingRoomBed> LodgingRoomBeds = ResultRoom.get(position).getLodgingRoomBed();
            int index = 0;
            for (LodgingRoomBed lodgingRoomBed : LodgingRoomBeds) {
                if (index == 0) {
                    viewHolder.capacityRoomHolderDetail1.setVisibility(View.VISIBLE);
                    viewHolder.txtCapacityRoomDetail1.setText(lodgingRoomBed.getRoomBedName() + " " + Util.persianNumbers(lodgingRoomBed.getRoomBedCount()) + " عدد");
                }
                if (index == 1) {
                    viewHolder.capacityRoomHolderDetail2.setVisibility(View.VISIBLE);
                    viewHolder.txtCapacityRoomDetail2.setText(lodgingRoomBed.getRoomBedName() + " " + Util.persianNumbers(lodgingRoomBed.getRoomBedCount()) + " عدد");
                }
                if (index == 2) {
                    viewHolder.capacityRoomHolderDetail3.setVisibility(View.VISIBLE);
                    viewHolder.txtCapacityRoomDetail3.setText(lodgingRoomBed.getRoomBedName() + " " + Util.persianNumbers(lodgingRoomBed.getRoomBedCount()) + " عدد");
                }
                if (index == 3) {
                    viewHolder.capacityRoomHolderDetail4.setVisibility(View.VISIBLE);
                    viewHolder.txtCapacityRoomDetail4.setText(lodgingRoomBed.getRoomBedName() + " " + Util.persianNumbers(lodgingRoomBed.getRoomBedCount()) + " عدد");
                }
                index++;
            }
        }

        //create data for price and price after sale applied
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        int roomPrice = Integer.valueOf(ResultRoom.get(position).getRoomPriceDifference());
        String roomPriceString = formatter.format(roomPrice);
        String roomPriceStringFinal = "تخفیف: " + roomPriceString + "تومان ";
        viewHolder.txtPriceRoom.setText(Util.persianNumbers(roomPriceStringFinal));

        int roomPricePromotion = Integer.parseInt(ResultRoom.get(position).getRoom_price_final());
        String roomPricePromotionString = formatter.format(roomPricePromotion);
        String roomPricePromotionStringFinal = "قابل پرداخت: " + roomPricePromotionString + "تومان ";
        viewHolder.txtnewPrice.setText(ResultRoom.get(position).getRoomPricePromotion() != null ? Util.persianNumbers(roomPricePromotionStringFinal) + "تومان" : "");

        viewHolder.txtShowPercentPercentage.setText(ResultRoom.get(position).getRoomPriceDifferencePercent() != null ? "تخفیف تا %" + Util.persianNumbers(ResultRoom.get(position).getRoomPriceDifferencePercent()) : "");

        int roomPriceTitle = Integer.valueOf(ResultRoom.get(position).getRoomPrice());
        String roomPriceTitleString = formatter.format(roomPriceTitle);
        String roomPriceTitleStringFinal = "قیمت به ازای هرشب :" + roomPriceTitleString + "تومان ";
        viewHolder.tvPriceTitle.setText(Util.persianNumbers(roomPriceTitleStringFinal));

        Glide.with(activity).load(ResultRoom.get(position).getImageUrl()).placeholder(R.drawable.ic_logotype_white).into(viewHolder.ivRoomImg);
    }

    @Override
    public int getItemCount() {
        return ResultRoom.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.roomType)
        TextView roomType;
        @BindView(R.id.txtCapacity)
        TextView txtCapacity;
        @BindView(R.id.txtShowPercentPercentage)
        TextView txtShowPercentPercentage;
        @BindView(R.id.txtCapacityRoomDetail1)
        TextView txtCapacityRoomDetail1;
        @BindView(R.id.txtCapacityRoomDetail2)
        TextView txtCapacityRoomDetail2;
        @BindView(R.id.txtCapacityRoomDetail3)
        TextView txtCapacityRoomDetail3;
        @BindView(R.id.txtCapacityRoomDetail4)
        TextView txtCapacityRoomDetail4;
        @BindView(R.id.txtnewPrice)
        TextView txtnewPrice;
        @BindView(R.id.txtPriceRoom)
        TextView txtPriceRoom;
        @BindView(R.id.capacityRoomHolderDetail1)
        RelativeLayout capacityRoomHolderDetail1;
        @BindView(R.id.capacityRoomHolderDetail2)
        RelativeLayout capacityRoomHolderDetail2;
        @BindView(R.id.capacityRoomHolderDetail3)
        RelativeLayout capacityRoomHolderDetail3;
        @BindView(R.id.capacityRoomHolderDetail4)
        RelativeLayout capacityRoomHolderDetail4;
        @BindView(R.id.WifiHolder)
        RelativeLayout WifiHolder;
        @BindView(R.id.BreakfastHolder)
        RelativeLayout BreakfastHolder;
        @BindView(R.id.chooseHolder)
        RelativeLayout chooseHolder;
        @BindView(R.id.txtNumberChoose)
        TextView txtNumberChoose;
        @BindView(R.id.txtNumberRoom)
        TextView tvNumberRoom;
        @BindView(R.id.priceTitle)
        TextView tvPriceTitle;
        @BindView(R.id.imgHotelList)
        ImageView ivRoomImg;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            chooseHolder.setOnClickListener(this);
            tvNumberRoom.setText(Util.persianNumbers("0"));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.chooseHolder:

                    break;
            }
        }
    }
}


