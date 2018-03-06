
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultPostFull implements Serializable {

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("post_lang")
    @Expose
    private String postLang;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("post_author_id")
    @Expose
    private String postAuthorId;
    @SerializedName("post_description")
    @Expose
    private String postDescription;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("post_url")
    @Expose
    private String postUrl;
    @SerializedName("post_img_url")
    @Expose
    private String postImgUrl;
    @SerializedName("post_audio")
    @Expose
    private String postAudio;
    @SerializedName("post_video")
    @Expose
    private String postVideo;
    @SerializedName("post_body")
    @Expose
    private String postBody;
    @SerializedName("post_author")
    @Expose
    private List<PostAuthor> postAuthor = null;
    @SerializedName("post_category")
    @Expose
    private List<PostCategory> postCategory = null;
    @SerializedName("result_images")
    @Expose
    private List<ResultImage> resultImages = null;
    @SerializedName("post_node")
    @Expose
    private List<PostNode> postNode = null;
    @SerializedName("post_blog")
    @Expose
    private List<PostBlog> postBlog = null;

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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public String getPostAudio() {
        return postAudio;
    }

    public void setPostAudio(String postAudio) {
        this.postAudio = postAudio;
    }

    public String getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(String postVideo) {
        this.postVideo = postVideo;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
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


    public List<ResultImage> getResultImages() {
        return resultImages;
    }

    public void setResultImages(List<ResultImage> resultImages) {
        this.resultImages = resultImages;
    }

    public List<PostNode> getPostNode() {
        return postNode;
    }

    public void setPostNode(List<PostNode> postNode) {
        this.postNode = postNode;
    }

    public List<PostBlog> getPostBlog() {
        return postBlog;
    }

    public void setPostBlog(List<PostBlog> postBlog) {
        this.postBlog = postBlog;
    }

}
