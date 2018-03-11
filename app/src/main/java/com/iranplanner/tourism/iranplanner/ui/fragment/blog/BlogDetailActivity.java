package com.iranplanner.tourism.iranplanner.ui.fragment.blog;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coinpany.core.android.widget.CTouchyWebView;
import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.photoViewer.GridImageActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.NearAttractionAdapter;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentPresenter;
import com.iranplanner.tourism.iranplanner.ui.navigationDrawer.NavigationFunctionsHelper;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.InterestResult;
import entity.PostBlog;
import entity.PostNode;
import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultBlogFull;
import entity.ResultBlogList;
import entity.ResultComment;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultPostFull;
import entity.ResultWidget;
import entity.ResultWidgetFull;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import tools.Constants;
import tools.Util;


public class BlogDetailActivity extends StandardActivity implements View.OnClickListener, BlogPresenter.View, AttractionListMoreContract.View, CommentContract.View/*, AttractionDetailContract.View, AttractionListMoreContract.View, CommentContract.View*/ {

    ResultPostFull resultPostFull;
    @BindView(R.id.titleBody)
    TextView titleBody;
    @BindView(R.id.txtBlogBestTitle)
    TextView txtBlogBestTitle;
    @BindView(R.id.txtAttractionBestTitle)
    TextView txtAttractionBestTitle;
    @BindView(R.id.txtPhotos)
    TextView txtPhotos;
    @BindView(R.id.textDate)
    TextView textDate;
    @BindView(R.id.WriterName)
    TextView writerName;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.imageWriterName)
    ImageView imageWriterName;
    @BindView(R.id.contentFullDescription)
    CTouchyWebView contentFullDescription;
    @BindView(R.id.recyclerAttractionRelated)
    RecyclerView recyclerAttractionRelated;
    @BindView(R.id.recyclerBlogRelated)
    RecyclerView recyclerBlogRelated;
    @BindView(R.id.commentImg)
    ImageView commentImg;
    @BindView(R.id.commentHolder)
    LinearLayout commentHolder;
    @BindView(R.id.cameraHolder)
    LinearLayout cameraHolder;
    @BindView(R.id.ratingPeopleHolder)
    RelativeLayout ratingPeopleHolder;
    @BindView(R.id.likeImg)
    ImageView likeImg;
    @BindView(R.id.addImg)
    ImageView addImg;
    @BindView(R.id.shareImg)
    ImageView shareImg;
    @Inject
    BlogPresenter blogPresenter;
    @Inject
    AttractionListMorePresenter attractionListMorePresenter;
    @Inject
    CommentPresenter commentPresenter;
    private ProgressDialog progressBar;
    String rotateImage;
    int LikeValue;
    List<ResultWidget> resultWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        DaggerBlogComponent.builder().netComponent(((App) getApplicationContext().getApplicationContext()).getNetComponent())
                .blogModule(new BlogModule(this, this, this))
                .build().inject(this);
        Util.overrideFont();
        getExtra();
        if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).equals("")) {
            commentPresenter.getWidgetResult("nodeuser", resultPostFull.getPostId(), Util.getUseRIdFromShareprefrence(getApplicationContext()), "blog", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        }
        addImg.setVisibility(View.GONE);
        shareImg.setVisibility(View.VISIBLE);
        setNearAttraction(resultPostFull.getPostNode());
        setRelateBlog(resultPostFull.getPostBlog());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("مجله:" + resultPostFull.getPostCategory().get(0).getCategoryTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
        collapsingToolbarLayout.setTitle("مجله: " + resultPostFull.getPostCategory().get(0).getCategoryTitle());


        titleBody.setText(resultPostFull.getPostTitle());
        if (resultPostFull.getPostImgUrl() != null) {
            Util.setImageView(resultPostFull.getPostImgUrl(), getApplicationContext(), img, null);
        }
        if (resultPostFull.getPostAuthor().get(0).getAuthoreImg() != null) {
            Util.setImageView(resultPostFull.getPostAuthor().get(0).getAuthoreImg(), getApplicationContext(), imageWriterName, null);
        }
        if (resultPostFull.getPostBody() != null) {
            setWebViewContent(resultPostFull.getPostBody());
        }
        writerName.setText(resultPostFull.getPostAuthor().get(0).getAuthorName());
//

        textDate.setText(Util.persianNumbers(Utils.getSimpleDateMilli(Long.valueOf(resultPostFull.getPostDate()))));
        ratingPeopleHolder.setVisibility(View.GONE);
        commentImg.setOnClickListener(this);
        likeImg.setOnClickListener(this);
        img.setOnClickListener(this);
        cameraHolder.setOnClickListener(this);


//
//                 aboutCityBtn1.setVisibility(View.VISIBLE);
//                 aboutCityBtn1.setText(resulAttraction.getProvinceTitle() + " - " + resulAttraction.getCityTitle());
//                 if (resulAttraction.getAttractionEstimatedTime() != null) {
//                     int totalMinute = Integer.parseInt(resulAttraction.getAttractionEstimatedTime());
//                     Util.convertMinuteToHour(totalMinute, textTimeDuration);
//                 }
//
//                 setImageHolder();
//                 myData = resulAttraction.getAttractionBody();
//                 if (myData != null) {
//                     setWebViewContent(getShowMoreString(myData));
//                 }
//
//                 if (resulAttraction.getAttractionPrice() == null) {
//                     textEntranceFee.setText("هزینه ورودی : رایگان");
//                 } else {
//                     textEntranceFee.setText("هزینه ورودی : " + Util.persianNumbers(resulAttraction.getAttractionPrice().toString()) + "تومان");
//                 }
//                 attractionType.setText(resulAttraction.getAttractionItineraryTypeTitle());
//                 setAttractionTypeImage();
//                 txtAddress.setText(resulAttraction.getAttractionAddress());
//
//                 mapFragment.getMapAsync(this);
//
//
//                 likeImg.setOnClickListener(this);
//                 commentImg.setOnClickListener(this);
//
//                 commentHolder.setOnClickListener(this);
//                 ratingPeopleHolder.setOnClickListener(this);
//                 MoreInoText.setOnClickListener(this);
//
//
//                 img.setOnClickListener(new View.OnClickListener() {
//
//
//                     @Override
//                     public void onClick(View v) {
//                         commentPresenter.getImages("images", resulAttraction.getAttractionId(), "attraction");
//                     }
//                 });
//
//                 builder = DaggerAtractionDetailComponent.builder()
//                         .netComponent(((App) getApplicationContext()).getNetComponent())
//                         .attractionDetailModule(new AttractionDetailModule(this, this, this));
//                 builder.build().inject(this);
//                 commentPresenter.getWidgetResult("nodeuser", resulAttraction.getAttractionId(), Util.getUseRIdFromShareprefrence(getApplicationContext()), "attraction", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
//                 getPhoto = new GetPhoto(getApplicationContext(), this);
//
//                 cameraHolder.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View v) {
//                         App.getInstance().prepareDirectories();
//
//                         if (Build.VERSION.SDK_INT < 23) {
//                             commentPresenter.selectImage(attractionDetailActivity.this);
//
//                         } else {
//                             if (App.checkGroupPermissions(App.STORAGE_PERMISSIONS)) {
//                                 commentPresenter.selectImage(attractionDetailActivity.this);
//                             } else {
//                                 requestPermissions(App.STORAGE_PERMISSIONS, 5);
//                             }
//                         }
//
//                     }
//                 });
//
//                 ((AdView) findViewById(R.id.banner_ad_view)).setAdListener(mAdListener);
    }

    private void setInterestResponce(List<ResultWidget> resultWidget) {

        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
            LikeValue = 1;
//            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_blog_details;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentHolder:
                if (Util.isLogin(getApplicationContext(), this)) {

                }
                commentPresenter.getCommentList("pagecomments", resultPostFull.getPostId(), "blog", "0");

                break;
            case R.id.commentImg:
                if (Util.isLogin(getApplicationContext(), this)) {
                    commentPresenter.getCommentList("pagecomments", resultPostFull.getPostId(), "blog", "0");
                }
                break;
            case R.id.cameraHolder:
                NavigationFunctionsHelper.getInstance(this, "\u200F \n " + resultPostFull.getPostUrl(), resultPostFull.getPostImgUrl(), resultPostFull.getPostTitle()).sendShareIntent();
                break;
            case R.id.img:
                if (resultPostFull.getResultImages().size() > 0) {
                    Intent intent = new Intent(BlogDetailActivity.this, GridImageActivity.class);
                    intent.putExtra("ResultImagesList", (Serializable) resultPostFull.getResultImages());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "عکسی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.likeImg:
                if (Util.isLogin(getApplicationContext(), this)) {
                    rotateImage = "likeImg";
                    if (LikeValue == 1) {
                        OnClickedIntrestedWidget("like", Constants.intrestDefault, likeImg);
                        likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));


                    } else {
                        OnClickedIntrestedWidget("like", Constants.likeImg, likeImg);
                        likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
                    }
                }
                break;


        }

    }

    private void OnClickedIntrestedWidget(String gType, String gValue, ImageView imageView) {
        commentPresenter.getInterest("widget", Util.getUseRIdFromShareprefrence(getApplicationContext()), "1", "blog", resultPostFull.getPostId(), gType, gValue, Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

    }

    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resultPostFull = (ResultPostFull) bundle.getSerializable("ResultPostFull");
        resultWidget = (List<ResultWidget>) bundle.getSerializable("resultWidget");
        if (resultWidget != null) {
            setInterestResponce(resultWidget);
        }
    }

    private void setWebViewContent(String myData) {
        contentFullDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        contentFullDescription.setLongClickable(false);
        contentFullDescription.setHapticFeedbackEnabled(false);

        String pish = "<html><head><style type=\"text/css\">@font-face {color:#737373;font-family: MyFont;src: url(\"file:///android_asset/fonts/IRANSansMobile.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;direction:rtl}</style></head><body>";
        String pas = "</body></html>";
        String myHtmlString = pish + myData + pas;
        contentFullDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);
    }

    private void setNearAttraction(final List<PostNode> postNodes) {
        if (postNodes.size() > 0) {
            txtAttractionBestTitle.setVisibility(View.VISIBLE);
            recyclerAttractionRelated.setVisibility(View.VISIBLE);
            NearAttractionAdapter attractionHomeAdapter = new NearAttractionAdapter(null, getApplicationContext(), postNodes, null);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerAttractionRelated.setLayoutManager(horizontalLayoutManagaer);
            recyclerAttractionRelated.setAdapter(attractionHomeAdapter);
            recyclerAttractionRelated.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    attractionListMorePresenter.getAttractionDetailNear("full", postNodes.get(position).getNid(), "fa", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                }
            }));
        }
    }

    private void setRelateBlog(final List<PostBlog> blogNodes) {
        if (blogNodes.size() > 0) {
            txtBlogBestTitle.setVisibility(View.VISIBLE);
            recyclerBlogRelated.setVisibility(View.VISIBLE);
            NearAttractionAdapter blogAdapter = new NearAttractionAdapter(null, getApplicationContext(), null, blogNodes);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerBlogRelated.setLayoutManager(horizontalLayoutManagaer);
            recyclerBlogRelated.setAdapter(blogAdapter);
            recyclerBlogRelated.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    blogPresenter.getBlogFull("full", blogNodes.get(position).getNid(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                }
            }));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {
        List<ResultComment> resultComments = resultCommentList.getResultComment();
        Intent intent = new Intent(BlogDetailActivity.this, CommentListActivity.class);
        intent.putExtra("resultComments", (Serializable) resultComments);
        intent.putExtra("resultPostFull", (Serializable) resultPostFull);
        intent.putExtra("nextOffset", resultCommentList.getStatistics().getOffsetNext().toString());
        intent.putExtra("fromWhere", "Blog");
        startActivity(intent);
    }

    @Override
    public void sendCommentMessage(ResultCommentList resultCommentList) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void commentResult(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void setIntrestedWidget(InterestResult interestResult) {
        setWidgetValue(interestResult.getResultWidget());
    }

    @Override
    public void setLoadWidget(ResultWidgetFull resultWidgetFull) {
        List<ResultWidget> resultWidget = resultWidgetFull.getResultWidget();
        setWidgetValue(resultWidget);
    }

    @Override
    public void setRate(ResultParamUser resultParamUser) {

    }

    @Override
    public void setRateUser(ResultParamUser resultParamUser) {

    }

    @Override
    public void showProgress() {

        progressBar = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", BlogDetailActivity.this);
    }

    @Override
    public void dismissProgress() {
        progressBar.dismiss();
    }

    @Override
    public void showBlogList(ResultBlogList resultBlogList) {

    }

    @Override
    public void showBlogFull(ResultBlogFull resultBlogFull) {
        if (resultBlogFull.getResultPostFull() != null) {
            Intent intent = new Intent(this, BlogDetailActivity.class);
            intent.putExtra("ResultPostFull", (Serializable) resultBlogFull.getResultPostFull());
            startActivity(intent);
        }
    }

    @Override
    public void showMoreImages(ResultImageList resultImageList) {
        if (resultPostFull.getResultImages().size() > 0) {
            Intent intent = new Intent(BlogDetailActivity.this, GridImageActivity.class);
            intent.putExtra("ResultImagesList", (Serializable) resultPostFull.getResultImages().size());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "عکسی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setImageUri(Uri uri) {

    }

    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {
        ResulAttraction resulAttraction = showAttractionFull.getResultAttractionFull().getResulAttraction();
        List<ResultAttractionList> resultAttractions = (List<ResultAttractionList>) showAttractionFull.getResultAttractionFull().getResultAttractionList();
        Intent intent = new Intent(this, attractionDetailActivity.class);
        intent.putExtra("resulAttraction", (Serializable) resulAttraction);
        intent.putExtra("resultAttractionList", (Serializable) resultAttractions);
        startActivity(intent);
    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {

    }

    private void setWidgetValue(List<ResultWidget> resultWidget) {

        if (resultWidget.get(0).getWidgetLikeValue() != null) {
            LikeValue = resultWidget.get(0).getWidgetLikeValue();
        }

        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
        } else if (resultWidget.get(0).getWidgetLikeValue() == null || resultWidget.get(0).getWidgetLikeValue() == 0) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
        }
//
    }
}
