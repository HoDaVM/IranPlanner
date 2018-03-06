
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultPostList  implements Serializable{
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("post_date")
    @Expose
    private String postDate;

    @SerializedName("post_lang")
    @Expose
    private String postLang;
    @SerializedName("post_title")
    @Expose
    private String postTitle;

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    @SerializedName("post_author_id")

    @Expose
    private String postAuthorId;
    @SerializedName("post_description")
    @Expose
    private String postDescription;
    @SerializedName("post_img_url")
    @Expose
    private String postImgUrl;
    @SerializedName("post_url")
    @Expose
    private String postUrl;
    @SerializedName("post_author")
    @Expose
    private List<PostAuthor> postAuthor = null;
    @SerializedName("post_category")
    @Expose
    private List<PostCategory> postCategory = null;
    @SerializedName("post_comment_count")
    @Expose
    private String postCommentCount;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostLang() {
        return postLang;
    }

    public void setPostLang(String postLang) {
        this.postLang = postLang;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostAuthorId() {
        return postAuthorId;
    }

    public void setPostAuthorId(String postAuthorId) {
        this.postAuthorId = postAuthorId;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public List<PostAuthor> getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(List<PostAuthor> postAuthor) {
        this.postAuthor = postAuthor;
    }

    public List<PostCategory> getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(List<PostCategory> postCategory) {
        this.postCategory = postCategory;
    }

    public String getPostCommentCount() {
        return postCommentCount;
    }

    public void setPostCommentCount(String postCommentCount) {
        this.postCommentCount = postCommentCount;
    }
    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
