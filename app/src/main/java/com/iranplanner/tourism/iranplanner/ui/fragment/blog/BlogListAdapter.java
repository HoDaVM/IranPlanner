package com.iranplanner.tourism.iranplanner.ui.fragment.blog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.coinpany.core.android.widget.CTouchyWebView;
import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

import entity.ResultBlogList;
import entity.ResultItinerary;
import entity.ResultPostList;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.ViewHolder> {
    private List<ResultPostList> resultPostList;
    Context context;
    int rowLayout;


    LayoutInflater inflater;

    public BlogListAdapter(Activity a, List<ResultPostList> resultPostList, Context context) {
        this.resultPostList = resultPostList;
        this.context = context;
        Activity activity = a;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BlogListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.content_blog_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BlogListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText(resultPostList.get(i).getPostTitle());
//        viewHolder.txtDescription.setText(resultPostList.get(i).getPostDescription());
        viewHolder.txtCat.setText(resultPostList.get(i).getPostCategory().get(0).getCategoryTitle());
        Long date = Long.valueOf(resultPostList.get(i).getPostDate());
        viewHolder.textDate.setText(Util.persianNumbers(Utils.getSimpleDateMilli(date)));
        Util.setImageView(resultPostList.get(i).getPostImgUrl(), context, viewHolder.imgBlog, viewHolder.imageLoading);
        viewHolder.txtDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        viewHolder.txtDescription.setLongClickable(false);
        viewHolder.txtDescription.setHapticFeedbackEnabled(false);

        String pish = "<html><head><style type=\"text/css\">@font-face {color:#737373;font-family: MyFont;src: url(\"file:///android_asset/fonts/IRANSansMobile.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;direction:rtl}</style></head><body>";
        String pas = "</body></html>";
        String myHtmlString = pish +getShowMoreString( resultPostList.get(i).getPostDescription()) + pas;
        viewHolder.txtDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);

    }

    private String getShowMoreString(String myData) {
        int count = 0;
        int position = 0;
        for (count = 0; count < 30; count++) {
            position = myData.indexOf(" ", position + 1);
        }
        return myData.substring(0, position) + "...";
    }

    @Override
    public int getItemCount() {
        return resultPostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {
        private TextView txtTitle;
        private CTouchyWebView txtDescription;
        private TextView txtCat;
        private TextView textDate;
        private ImageView imgBlog;
        private ProgressBar imageLoading;

        public ViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtDescription = view.findViewById(R.id.txtDescription);
            txtCat = view.findViewById(R.id.txtCat);
            textDate = view.findViewById(R.id.textDate);
            imgBlog = view.findViewById(R.id.imgBlog);
            imageLoading = view.findViewById(R.id.imageLoading);
        }

    }


}


