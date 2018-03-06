
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostImage implements Serializable {

    @SerializedName("images_id")
    @Expose
    private String imagesId;
    @SerializedName("images_alt")
    @Expose
    private String imagesAlt;
    @SerializedName("images_title")
    @Expose
    private String imagesTitle;
    @SerializedName("imgs_url")
    @Expose
    private String imgsUrl;

    public String getImagesId() {
        return imagesId;
    }

    public void setImagesId(String imagesId) {
        this.imagesId = imagesId;
    }

    public String getImagesAlt() {
        return imagesAlt;
    }

    public void setImagesAlt(String imagesAlt) {
        this.imagesAlt = imagesAlt;
    }

    public String getImagesTitle() {
        return imagesTitle;
    }

    public void setImagesTitle(String imagesTitle) {
        this.imagesTitle = imagesTitle;
    }

    public String getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(String imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

}
