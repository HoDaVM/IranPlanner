package com.iranplanner.tourism.iranplanner.ui.fragment.blog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.OnVisibleShowCaseViewListener;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.DaggerHomeComponent;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeModule;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import entity.InterestResult;
import entity.ResultBlogFull;
import entity.ResultBlogList;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultPostList;
import entity.ResultWidgetFull;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
public class BlogFragment extends StandardFragment implements View.OnClickListener, BlogPresenter.View, AttractionListMoreContract.View, CommentContract.View {


    String cid;
    String andId;
    String uid;
    View view;
    RecyclerView blogListRecyclerView;
    LinearLayout filterToggleView;
    List<ResultPostList> resultPostList;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    String nextOffset;
    @Inject
    BlogPresenter blogPresenter;

    BlogListAdapter blogListAdapter;
    Toolbar toolbar;
    private ProgressDialog progressBar;

    public static BlogFragment newInstance(OnVisibleShowCaseViewListener onVisibleShowCaseViewListener, List<ResultPostList> resultPostList) {
        BlogFragment fragment = new BlogFragment();
        fragment.resultPostList = resultPostList;
        return fragment;
    }

    private void getSharedpreferences() {
        cid = Util.getTokenFromSharedPreferences(getContext());
        andId = Util.getAndroidIdFromSharedPreferences(getContext());
        uid = Util.getUseRIdFromShareprefrence(getContext());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nextOffset = "20";
        DaggerBlogComponent.builder().netComponent(((App) getContext().getApplicationContext()).getNetComponent())
                .blogModule(new BlogModule(this, this, this))
                .build().inject(this);
        view = inflater.inflate(R.layout.blog_list, container, false);
        blogListRecyclerView = view.findViewById(R.id.blogListRecyclerView);
        filterToggleView = view.findViewById(R.id.filterToggleView);
        toolbar = view.findViewById(R.id.toolbar);
//        ((StandardActivity) getActivity()).setSupportActionBar(toolbar);
//        ((StandardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((StandardActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.abs_layout);
//        ((StandardActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("مجله ایران پلنر");
        mLayoutManager = new LinearLayoutManager(getContext());
        blogListAdapter = new BlogListAdapter(getActivity(), resultPostList, getActivity().getApplicationContext());
        filterToggleView.setOnClickListener(this);
        getSharedpreferences();
        blogListRecyclerView.setLayoutManager(mLayoutManager);

        blogListRecyclerView.setAdapter(blogListAdapter);
        blogListRecyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                blogPresenter.getBlogFull("full", resultPostList.get(position).getPostId(), Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
            }
        }));
        blogListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && loading) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        blogPresenter.getBlogList("list",nextOffset, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
                    }
                }
            }

        });

        return view;
    }

    public void onClick(View v) {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {

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

    }

    @Override
    public void setLoadWidget(ResultWidgetFull resultWidgetFull) {

    }

    @Override
    public void setRate(ResultParamUser resultParamUser) {

    }

    @Override
    public void setRateUser(ResultParamUser resultParamUser) {

    }

    @Override
    public void showProgress() {

        progressBar = Util.showProgressDialog(getContext(), "لطفا منتظر بمانید",getActivity());
    }

    @Override
    public void dismissProgress() {
        progressBar.dismiss();
    }

    @Override
    public void showMoreImages(ResultImageList resultImageList) {

    }

    @Override
    public void setImageUri(Uri uri) {

    }

    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {

    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {

    }

    @Override
    public void showBlogList(ResultBlogList resultBlogList) {
        resultBlogList.getResultPostList();
        if (!nextOffset.equals(resultBlogList.getStatistics().getOffsetNext())) {
            resultPostList.addAll(resultBlogList.getResultPostList());
            blogListAdapter.notifyDataSetChanged();
            nextOffset = resultBlogList.getStatistics().getOffsetNext().toString();
            if (resultBlogList.getResultPostList().size() > 0) {
                loading = true;
            }

        }
    }

    @Override
    public void showBlogFull(ResultBlogFull resultBlogFull) {
        if (resultBlogFull.getResultPostFull() != null) {
            Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
            intent.putExtra("ResultPostFull", (Serializable) resultBlogFull.getResultPostFull());
            startActivity(intent);
        }
    }
}
