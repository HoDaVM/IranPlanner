
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultImage implements Serializable {

    @SerializedName("img_id")
    @Expose
    private String imgId;
    @SerializedName("img_size")
    @Expose
    private String imgSize;
    @SerializedName("img_demansion")
    @Expose
    private ImgDemansion imgDemansion;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("img_source")
    @Expose
    private String imgSource;
    @SerializedName("img_auhotr")
    @Expose
    private ImgAuhotr imgAuhotr;
    @SerializedName("img_title")
    @Expose
    private String imgTitle;
    @SerializedName("img_description")
    @Expose
    private String imgDescription;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public ImgDemansion getImgDemansion() {
        return imgDemansion;
    }

    public void setImgDemansion(ImgDemansion imgDemansion) {
        this.imgDemansion = imgDemansion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public ImgAuhotr getImgAuhotr() {
        return imgAuhotr;
    }

    public void setImgAuhotr(ImgAuhotr imgAuhotr) {
        this.imgAuhotr = imgAuhotr;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public String getImgDescription() {
        return imgDescription;
    }

    public void setImgDescription(String imgDescription) {
        this.imgDescription = imgDescription;
    }

}
