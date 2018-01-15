package com.iranplanner.tourism.iranplanner.ui.activity.comment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;

import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import entity.CommentSend;
import entity.ResulAttraction;
import entity.ResultComment;
import entity.ResultCommentList;
import entity.ResultItinerary;
import entity.ResultItineraryAttraction;
import tools.Util;


/**
 * Created by h.vahidimehr on 21/02/2017.
 */

public class CommentListActivity extends StandardActivity implements DataTransferInterface, CommentContract.View {
    private CommentListAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ImageView sendCommentBtn;
    EditText txtAddComment;
    ResultItinerary itineraryData;
    TextView commentTitle;
    boolean sending = false;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String nextOffset;
    List<ResultComment> resultComments;
    RecyclerView recyclerView;
    ResulAttraction attractionData;
    String fromWhere;
    DaggerCommentComponent.Builder builder;
    @Inject
    CommentPresenter commentPresenter;
    private String cParent;
    int STATIC_INTEGER_VALUE_COMMENT = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_itinerary_comment);
        recyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        sendCommentBtn = (ImageView) findViewById(R.id.sendCommentBtn);
        txtAddComment = (EditText) findViewById(R.id.txtAddComment);
        commentTitle = (TextView) findViewById(R.id.commentTitle);
//---

        builder = DaggerCommentComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .commentModule(new CommentModule(this));
        builder.build().injectComment(this);
        //----
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        Bundle extras = getIntent().getExtras();
        resultComments = (List<ResultComment>) extras.getSerializable("resultComments");
        fromWhere = extras.getString("fromWhere");
        if (fromWhere.equals("Itinerary")) {
            itineraryData = (ResultItinerary) extras.getSerializable("itineraryData");

            if (itineraryData != null) {
                commentTitle.setText(itineraryData.getItineraryFromCityName() + " " + itineraryData.getItineraryToCityName() + " " + Util.persianNumbers(itineraryData.getItineraryDuration()) + " روز");
                nextOffset = (String) extras.getSerializable("nextOffset");
            }

        } else if (fromWhere.equals("Attraction")) {
            attractionData = (ResulAttraction) extras.getSerializable("attraction");

            if (attractionData != null) {
                nextOffset = (String) extras.getSerializable("nextOffset");
                commentTitle.setText(attractionData.getAttractionTitle());
            }
        }
        cParent = "";
        cParent = extras.getString("cParent");
            adapter = new CommentListAdapter(CommentListActivity.this, this, resultComments, getApplicationContext(), R.layout.fragment_comment_item, cParent);
            recyclerView.setAdapter(adapter);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            if (nextOffset != null && Integer.valueOf(nextOffset) > 1) {
                recyclerView.smoothScrollToPosition(Integer.valueOf(nextOffset) - 1);
            }
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                TextView reservationBtn = view.findViewById(R.id.replyBtn);
                reservationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("reserve", "click");
                        List<ResultComment> rComment = new ArrayList<>();
                        ResultComment r = new ResultComment();
                        r = resultComments.get(position);
                        rComment.add(r);


                        Intent intent = new Intent(CommentListActivity.this, CommentListActivity.class);
                        intent.putExtra("resultComments", (Serializable) rComment);
                        intent.putExtra("nextOffset", "20");
                        intent.putExtra("fromWhere", fromWhere);
                        intent.putExtra("cParent", rComment.get(0).getCommentId());
                        if (fromWhere.equals("Itinerary")) {
                            intent.putExtra("itineraryData", (Serializable) itineraryData);

                        } else if (fromWhere.equals("Attraction")) {
                            intent.putExtra("attractionData", (Serializable) attractionData);
                        }

                        startActivityForResult(intent, STATIC_INTEGER_VALUE_COMMENT);
//                        startActivity(intent);
                    }
                });
            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                             @Override
                                             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


                                                 if (dy < 0) //check for scroll down
                                                 {
                                                     visibleItemCount = mLayoutManager.getChildCount();
                                                     totalItemCount = mLayoutManager.getItemCount();
                                                     pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

//                                                     if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                                     if ((pastVisiblesItems) <= 15 && loading && (Integer.valueOf(nextOffset) > 0)) {
                                                         loading = false;
                                                         visibleItemCount = mLayoutManager.getChildCount();
                                                         totalItemCount = mLayoutManager.getItemCount();
                                                         pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
//                                                         if ((visibleItemCount + pastVisiblesItems) == totalItemCount) {
                                                         if (fromWhere.equals("Itinerary")) {
                                                             commentPresenter.getCommentList("pagecomments", itineraryData.getItineraryId(), "itinerary", nextOffset);
                                                         } else if (fromWhere.equals("Attraction")) {
                                                             commentPresenter.getCommentList("pagecomments", attractionData.getAttractionId(), "itinerary", nextOffset);
                                                         }

//                                                         }
                                                     }

                                                 }
                                             }
                                         }

        );


        txtAddComment.addTextChangedListener(new

                                                     TextWatcher() {
                                                         @Override
                                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                         }

                                                         @Override
                                                         public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                                             if (start != 0 && (start == 0 && before == 0 && count == 1)) {
//                                                                 sendCommentBtn.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_send_grey));
//                                                             }
                                                             if (start != 0 || (start == 0 && before == 0 && count == 1)) {
                                                                 sendCommentBtn.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_send_pink));
                                                             }
                                                         }

                                                         @Override
                                                         public void afterTextChanged(Editable s) {
                                                             Log.e("d", s.toString());
                                                             if (s.toString().equals("")) {
                                                                 sendCommentBtn.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_send_grey));
                                                             }
                                                         }
                                                     }

        );
        sendCommentBtn.setOnClickListener(new View.OnClickListener()
                                                  //// TODO: 9/20/17 salam hoda chetori injaro ba farid barresi konnn
                                          {
                                              @Override
                                              public void onClick(View v) {
                                                  Log.e("send comment", "clicked");
                                                  String userId = Util.getUseRIdFromShareprefrence(getApplicationContext());
                                                  if (!userId.isEmpty() && !txtAddComment.getText().equals("") && !sending) {
                                                      sending = true;
                                                      sendCommentBtn.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_send_grey));
                                                      if (fromWhere.equals("Itinerary")) {
                                                          commentPresenter.callInsertComment(new CommentSend(userId, "1", "itinerary", itineraryData.getItineraryId(), "comment", String.valueOf(txtAddComment.getText()), cParent, ""), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                                                      } else if (fromWhere.equals("Attraction")) {
                                                          commentPresenter.callInsertComment(new CommentSend(userId, "1", "attraction", attractionData.getAttractionId(), "comment", String.valueOf(txtAddComment.getText()), cParent, ""), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                                                      }
                                                      txtAddComment.setText("");
                                                  } else if (userId.isEmpty()) {
                                                      Log.e("user is not login", "error");
                                                      Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید ", Toast.LENGTH_LONG).show();
                                                  }
                                              }
                                          }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (STATIC_INTEGER_VALUE_COMMENT == requestCode) {
            setDataSetChange((ResultCommentList) data.getExtras().get("replyComment"));
        }
    }

    private void setDataSetChange(ResultCommentList resultC) {
        int i = 0;
        for (ResultComment resultComment : resultComments) {
            if (resultComment.getCommentId().equals(resultC.getResultComment().get(0).getCommentId())) {
                resultComments.set(i, resultC.getResultComment().get(0));
            }
            i++;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_itinerary_comment;
    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {

        List<ResultComment> newresultComments = resultCommentList.getResultComment();
        if (!nextOffset.equals(resultCommentList.getStatistics().getOffsetNext().toString())) {
            resultComments.addAll(0, newresultComments);
            adapter.notifyDataSetChanged();
            nextOffset = resultCommentList.getStatistics().getOffsetNext().toString();
            loading = true;
        }
    }

    @Override
    public void sendCommentMessage(ResultCommentList resultCommentList) {
        entity.Status status = resultCommentList.getStatus();
        if (status.getStatus() == 200) {
            sending = false;
            if (cParent != null && !cParent.equals("")) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("replyComment", resultCommentList);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } else {
                resultComments.add(resultCommentList.getResultComment().get(0));
                //hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
            }
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void commentResult(String message) {
        Toast.makeText(getApplicationContext(), "اشکال در ارسال پیام، بعد از بررسی اتصال به اینترنت دوباره تلاش کنید", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showComplete() {

    }





    public class CustomDialogAlert extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public TextView txtNo, standardTitle, alertDescription;


        public CustomDialogAlert(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_alert_close);
            txtNo = (TextView) findViewById(R.id.txtNo);
            standardTitle = (TextView) findViewById(R.id.txtAlertTitle);
            alertDescription = (TextView) findViewById(R.id.alertDescription);
            alertDescription.setText("پیام شما با موفقیت دریافت شد، بعد از بررسی منتشر خواهد شد.");
            txtNo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtNo:
                    dismiss();
                    break;
            }
            dismiss();
        }
    }


    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }
}
