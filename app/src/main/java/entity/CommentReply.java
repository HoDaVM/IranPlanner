package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by h.vahidimehr on 31/12/2017.
 */

public class CommentReply implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("comment_date")
    @Expose
    private String commentDate;
    @SerializedName("comment_body")
    @Expose
    private String commentBody;
    @SerializedName("comment_lang")
    @Expose
    private String commentLang;
    @SerializedName("comment_id")
    @Expose
    private String commentId;
    @SerializedName("comment_type")
    @Expose
    private String commentType;
    @SerializedName("user_fname")
    @Expose
    private String userFname;
    @SerializedName("user_lname")
    @Expose
    private String userLname;
    @SerializedName("user_gender")
    @Expose
    private String userGender;
    @SerializedName("user_register")
    @Expose
    private String userRegister;
    @SerializedName("reply_register_date_formated")
    @Expose
    private Object replyRegisterDateFormated;
    @SerializedName("reply_register_date_shamsi")
    @Expose
    private Object replyRegisterDateShamsi;
    @SerializedName("reply_comment_date_formated")
    @Expose
    private String replyCommentDateFormated;
    @SerializedName("reply_comment_date_shamsi")
    @Expose
    private String replyCommentDateShamsi;
    @SerializedName("reply_comment_date_time")
    @Expose
    private String replyCommentDateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getCommentLang() {
        return commentLang;
    }

    public void setCommentLang(String commentLang) {
        this.commentLang = commentLang;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(String userRegister) {
        this.userRegister = userRegister;
    }

    public Object getReplyRegisterDateFormated() {
        return replyRegisterDateFormated;
    }

    public void setReplyRegisterDateFormated(Object replyRegisterDateFormated) {
        this.replyRegisterDateFormated = replyRegisterDateFormated;
    }

    public Object getReplyRegisterDateShamsi() {
        return replyRegisterDateShamsi;
    }

    public void setReplyRegisterDateShamsi(Object replyRegisterDateShamsi) {
        this.replyRegisterDateShamsi = replyRegisterDateShamsi;
    }

    public String getReplyCommentDateFormated() {
        return replyCommentDateFormated;
    }

    public void setReplyCommentDateFormated(String replyCommentDateFormated) {
        this.replyCommentDateFormated = replyCommentDateFormated;
    }

    public String getReplyCommentDateShamsi() {
        return replyCommentDateShamsi;
    }

    public void setReplyCommentDateShamsi(String replyCommentDateShamsi) {
        this.replyCommentDateShamsi = replyCommentDateShamsi;
    }

    public String getReplyCommentDateTime() {
        return replyCommentDateTime;
    }

    public void setReplyCommentDateTime(String replyCommentDateTime) {
        this.replyCommentDateTime = replyCommentDateTime;
    }
}
