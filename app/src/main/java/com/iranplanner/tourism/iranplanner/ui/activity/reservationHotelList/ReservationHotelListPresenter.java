package com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList;


import javax.inject.Inject;

import entity.ResultLodgingHotel;
import entity.ResultLodgingList;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hoda on 11-May-16.
 */
public class ReservationHotelListPresenter extends ReservationHotelListContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public ReservationHotelListPresenter(Retrofit retrofit, View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void getHotelReserve(String action, String idHotel, String limit, String offset, String cid, String andID) {
        mView.showProgress();
        retrofit.create(ReservationHotelListService.class).getHotelReserve(action, idHotel, limit, offset, cid, andID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultLodgingHotel>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.dismissProgress();
                    }

                    @Override
                    public void onNext(ResultLodgingHotel resultLodgingHotel) {
                        mView.showHotelReserveList(resultLodgingHotel);
                    }
                });
    }

    @Override
    public void getHotelFilter(String action, String city, String limit, String offset, String type, String order, String rate0, String rate1, String rate2, String rate3, String rate4, String rate5, String typeHotel, String typeLocalhost, String typeTraditional, String typeApartment, String cid, String andId) {
        mView.showProgress();
        retrofit.create(ReservationHotelListService.class).getHotelFilter( action,  city,  limit,  offset,
                 type,  order,  rate0,  rate1,  rate2,  rate3,  rate4,  rate5,
                 typeHotel,  typeLocalhost,
                 typeTraditional,  typeApartment,  cid,  andId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultLodgingList>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.dismissProgress();
                    }

                    @Override
                    public void onNext(ResultLodgingList resultLodgingList) {
                        mView.showLodgingList(resultLodgingList,"filter");
                    }
                });
    }

    @Override
    public void getHotelFilterONDemandLoading(String action, String city, String limit, String offset, String type, String order, String rate0, String rate1, String rate2, String rate3, String rate4, String rate5, String typeHotel, String typeLocalhost, String typeTraditional, String typeApartment, String cid, String andId) {
        mView.showProgress();
        retrofit.create(ReservationHotelListService.class).getHotelFilter( action,  city,  limit,  offset,
                type,  order,  rate0,  rate1,  rate2,  rate3,  rate4,  rate5,
                typeHotel,  typeLocalhost,
                typeTraditional,  typeApartment,  cid,  andId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultLodgingList>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.dismissProgress();
                    }

                    @Override
                    public void onNext(ResultLodgingList resultLodgingList) {
                        mView.showLodgingList(resultLodgingList,"OnDemandLoading");
                    }
                });
    }


    public interface ReservationHotelListService {

        @GET("api-lodging.php")
        Observable<ResultLodgingHotel> getHotelReserve(@Query("action") String action,
                                                       @Query("id") String idHotel,
                                                       @Query("limit") String limit,
                                                       @Query("offset") String offset,
                                                       @Query("cid") String cid,
                                                       @Query("andId") String andId);

        // https://api.parsdid.com/iranplanner/app/api-lodging.php?action=list&city=342&limit=20&offset=0&cid=Y&type=147&order=maxprice&rate5=1&rate4=1&typetraditional=1&typehotel=1
        @GET("api-lodging.php")
        Observable<ResultLodgingList> getHotelFilter(@Query("action") String action,
                                                     @Query("city") String city,
                                                     @Query("limit") String limit,
                                                     @Query("offset") String offset,
                                                     @Query("type") String type,
                                                     @Query("order") String order,
                                                     @Query("rate0") String rate0,
                                                     @Query("rate1") String rate1,
                                                     @Query("rate2") String rate2,
                                                     @Query("rate3") String rate3,
                                                     @Query("rate4") String rate4,
                                                     @Query("rate5") String rate5,
                                                     @Query("typehotel") String typeHotel,
                                                     @Query("typelocalhost") String typeLocalhost,
                                                     @Query("typetraditional") String typeTraditional,
                                                     @Query("typeapartment") String typeApartment,
                                                     @Query("cid") String cid,
                                                     @Query("andId") String andId);
    }
}
