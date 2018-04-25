package com.iranplanner.tourism.iranplanner.ui.fragment.myaccount;


import javax.inject.Inject;

import entity.ContactUs;
import entity.GetInfoReqSend;
import entity.GetInfoResult;
import entity.ResultReservationReqStatus;
import entity.SendParamContactUs;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hoda on 11-May-16.
 */
public class SettingPresenter extends SettingContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public SettingPresenter(Retrofit retrofit, View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }


    @Override
    public void getUserInfoPostResult(GetInfoReqSend getInfoReqSend, String cid, String androidId) {
        mView.showProgress();
        retrofit.create(GetInfoService.class).getInfoUserPostResul(getInfoReqSend, cid, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<GetInfoResult>() {

                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgress();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(GetInfoResult userInfo) {
                        mView.showInfoUserResult(userInfo);
                    }
                });
    }

    @Override
    public void sendParamContactUs(SendParamContactUs sendParamContactUs, String token, String androidId) {
        mView.showProgress();
        retrofit.create(GetInfoService.class).sendParamContactUs(sendParamContactUs, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ContactUs>() {

                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgress();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ContactUs ccntactUs) {
                        mView.getContactUsMessage(ccntactUs);
                    }
                });
    }

//    @Override
//    public void getResultReservationReqStatus(String action, String uid, String lang, String cid, String androidId) {
//        mView.showProgress();
//        retrofit.create(GetInfoService.class).getResultReservationReqStatus(action, uid, lang, cid, androidId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultReservationReqStatus>() {
//
//                    @Override
//                    public void onCompleted() {
//                        mView.dismissProgress();
//                        mView.showComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.dismissProgress();
//                        mView.showError(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResultReservationReqStatus resultReservationReqStatus) {
//                        mView.showResultReservationReqStatus(resultReservationReqStatus);
//                    }
//                });
//    }

    public interface GetInfoService {

        @POST("api-user.php?action=getinfo")
        Observable<GetInfoResult> getInfoUserPostResul(@Body GetInfoReqSend getInfoReqSend,
                                                       @Query("cid") String token,
                                                       @Query("andId") String androidId);
        //   https://api.parsdid.com/iranplanner/app/api-lodging.php?action=req_user_count&uid=792147600796866&lang=fa
//        https://api.parsdid.com/iranplanner/app/api-reservation.php?action=req_user_count_bundle&uid=792147600796866&lang=faervation

        //        @GET("api-reservation.php")
//        Observable<ResultReservationReqStatus> getResultReservationReqStatus(@Query("action") String action,
//                                                                             @Query("uid") String uid,
//                                                                             @Query("lang") String lang,
//                                                                             @Query("cid") String cid,
//                                                                             @Query("andId") String androidId);
//        https://api.parsdid.com/iranplanner/app/api-contact.php?action=contact&lang=fa
        @POST("api-contact.php?action=contact&lang=fa")
        Observable<ContactUs> sendParamContactUs(@Body SendParamContactUs sendParamContactUs,
                                                 @Query("cid") String token,
                                                 @Query("andId") String androidId);
    }
}
