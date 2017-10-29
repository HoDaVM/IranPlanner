package com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList;


import entity.ResultLodgingHotel;
import entity.ResultLodgingList;

/**
 * Created by Hoda
 */
public abstract class ReservationHotelListContract {
    public interface View {
        void showHotelReserveList(ResultLodgingHotel resultLodgingHotel);

        void showError(String message);

        void showComplete();


        void dismissProgress();

        void showProgress();
        void showLodgingList(ResultLodgingList resultLodgingList,String filter);
//        void showLodgingListFilter(ResultLodgingList resultLodgingList);

    }

    public abstract void getHotelReserve(String action,
                                         String idHotel,
                                         String limit,
                                         String offset,
                                         String cid,
                                         String andID);


    public abstract void getHotelFilter(String action,
                                        String city,
                                        String limit,
                                        String offset,
                                        String type,
                                        String order,
                                        String rate0,
                                        String rate1,
                                        String rate2,
                                        String rate3,
                                        String rate4,
                                        String rate5,
                                        String typeHotel,
                                        String typeLocalhost,
                                        String typeTraditional,
                                        String typeApartment,
                                        String cid,
                                        String andId);
    public abstract void getHotelFilterONDemandLoading(String action,
                                        String city,
                                        String limit,
                                        String offset,
                                        String type,
                                        String order,
                                        String rate0,
                                        String rate1,
                                        String rate2,
                                        String rate3,
                                        String rate4,
                                        String rate5,
                                        String typeHotel,
                                        String typeLocalhost,
                                        String typeTraditional,
                                        String typeApartment,
                                        String cid,
                                        String andId);

}
