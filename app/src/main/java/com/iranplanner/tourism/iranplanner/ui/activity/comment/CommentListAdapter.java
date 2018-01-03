package com.iranplanner.tourism.iranplanner.ui.activity.comment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entity.CommentReply;
import entity.ResultComment;

/**
 * Created by Hoda on 10/01/2017.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;
    LayoutInflater inflater;
    List<ResultComment> resultComments;
    String cParent;


    public CommentListAdapter(Activity a, DataTransferInterface dtInterface, List<ResultComment> resultComments, Context context, int rowLayout,String cParent) {
        this.resultComments = resultComments;
        this.context = context;
        this.rowLayout = rowLayout;
        Activity activity = a;
        this.dtInterface = dtInterface;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       this. cParent=cParent;
    }


    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.fragment_comment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.commentText.setText(resultComments.get(i).getCommentBody());
        viewHolder.commentSenderName.setText(resultComments.get(i).getUserFname());
        viewHolder.commentSentTime.setText(Utils.timeElapsedFromDate(getDate(resultComments.get(i).getCommentDate())) + " پیش");

//        viewHolder.replyBtn.setVisibility(item.commentConfig.isReplyEnable() ? View.VISIBLE : View.GONE);
//        mediaController.loadImage(holder1.commentSenderPic, item.User.ProfilePic, R.drawable.samplepic_2);


        if (resultComments.get(i).getCommentReply().size() > 0) {
            viewHolder.replyBtn.setText(Utils.persianNumbers(String.format("%d پاسخ", resultComments.get(i).getCommentReply().size())));
            viewHolder.replyUserMainLayout.setVisibility(View.VISIBLE);

            viewHolder.replyUserMainLayout.removeAllViews();
            View repLayout;
            LayoutInflater inflater = LayoutInflater.from(context);

            List<CommentReply> commentReplies = resultComments.get(i).getCommentReply();
            for (CommentReply commentReply : commentReplies) {
                repLayout = inflater.inflate(R.layout.replay_comment, null);
                ImageView repProPic = (ImageView) repLayout.findViewById(R.id.replySenderPic);
                TextView repSenderName = (TextView) repLayout.findViewById(R.id.replySenderName);
                TextView repSentTime = (TextView) repLayout.findViewById(R.id.commentSentTime);
                TextView replyText = (TextView) repLayout.findViewById(R.id.replyText);

//                Media pic = reply.User.getProfilePic();
//                mediaController.loadImage(repProPic, pic, R.drawable.samplepic_2);
                repSenderName.setText(commentReply.getUserFname());
                repSentTime.setText(Utils.persianNumbers(Utils.timeElapsedFromDate(getDate(commentReply.getCommentDate()))));
                replyText.setText(commentReply.getCommentBody());


                viewHolder.replyUserMainLayout.addView(repLayout);
            }



        }else {
            viewHolder.replyUserMainLayout.setVisibility(View.GONE);
            viewHolder.replyBtn.setText("پاسخ");
        }
        if(cParent!=null && !cParent.equals("")) {
            viewHolder.replyBtn.setVisibility(View.GONE);
        }else {

        }
    }

    private Long getDate(String commentTimeString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);

        Date commentDate = null;
        try {
            commentDate = formatter.parse(commentTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return commentDate.getTime();
    }

    @Override
    public int getItemCount() {
        return resultComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout commentButtonHolder;


        public ImageView commentSenderPic;
        public TextView commentSenderName;
        public TextView commentSentTime;
        public TextView commentText;
        public Button replyBtn;
        public View commentMainLayout;
        public ViewGroup replyUserMainLayout;

        public ViewHolder(View view) {
            super(view);
            commentText = (TextView) view.findViewById(R.id.commentText);
            commentSenderName = (TextView) view.findViewById(R.id.commentSenderName);
            commentSentTime = (TextView) view.findViewById(R.id.commentSentTime);


            commentSenderName = (TextView) itemView.findViewById(R.id.commentSenderName);
            commentSentTime = (TextView) itemView.findViewById(R.id.commentSentTime);
            commentText = (TextView) itemView.findViewById(R.id.commentText);
            commentSenderPic = (ImageView) itemView.findViewById(R.id.commentSenderPic);
            commentMainLayout = itemView.findViewById(R.id.commentMainLayout);
            replyUserMainLayout = (ViewGroup) itemView.findViewById(R.id.replyUserMainLayout);
            replyBtn =  itemView.findViewById(R.id.replyBtn);
        }
    }

}


