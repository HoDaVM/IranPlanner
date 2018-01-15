
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultComment implements Serializable {
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
    @SerializedName("comment_ntype")
    @Expose
    private String commentNtype;
    @SerializedName("comment_gtype")
    @Expose
    private String commentGtype;
    @SerializedName("comment_nid")
    @Expose
    private String commentNid;
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
    @SerializedName("comment_service")
    @Expose
    private String comment_service;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @SerializedName("comment_reply")

    @Expose
    private List<CommentReply> commentReply = null;

    public String getComment_service() {
        return comment_service;
    }

    public void setComment_service(String comment_service) {
        this.comment_service = comment_service;
    }

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

    public String getCommentNtype() {
        return commentNtype;
    }

    public void setCommentNtype(String commentNtype) {
        this.commentNtype = commentNtype;
    }

    public String getCommentGtype() {
        return commentGtype;
    }

    public void setCommentGtype(String commentGtype) {
        this.commentGtype = commentGtype;
    }

    public String getCommentNid() {
        return commentNid;
    }

    public void setCommentNid(String commentNid) {
        this.commentNid = commentNid;
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

    public List<CommentReply> getCommentReply() {
        return commentReply;
    }

    public void setCommentReply(List<CommentReply> commentReply) {
        this.commentReply = commentReply;
    }
}
