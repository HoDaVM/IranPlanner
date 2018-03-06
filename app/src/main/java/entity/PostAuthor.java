
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostAuthor implements Serializable {

    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("authore_img")
    @Expose
    private String authoreImg;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthoreImg() {
        return authoreImg;
    }

    public void setAuthoreImg(String authoreImg) {
        this.authoreImg = authoreImg;
    }

}
