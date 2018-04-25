
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultLocalfood implements Serializable {

    @SerializedName("localfood_id")
    @Expose
    private String localfoodId;
    @SerializedName("localfood_title")
    @Expose
    private String localfoodTitle;
    @SerializedName("localfood_img_url")
    @Expose
    private String localfoodImgUrl;
    @SerializedName("localfood_province_name")
    @Expose
    private String localfoodProvinceName;
    @SerializedName("localfood_province_id")
    @Expose
    private String localfoodProvinceId;

    public String getLocalfoodId() {
        return localfoodId;
    }

    public void setLocalfoodId(String localfoodId) {
        this.localfoodId = localfoodId;
    }

    public ResultLocalfood withLocalfoodId(String localfoodId) {
        this.localfoodId = localfoodId;
        return this;
    }

    public String getLocalfoodTitle() {
        return localfoodTitle;
    }

    public void setLocalfoodTitle(String localfoodTitle) {
        this.localfoodTitle = localfoodTitle;
    }

    public ResultLocalfood withLocalfoodTitle(String localfoodTitle) {
        this.localfoodTitle = localfoodTitle;
        return this;
    }

    public String getLocalfoodImgUrl() {
        return localfoodImgUrl;
    }

    public void setLocalfoodImgUrl(String localfoodImgUrl) {
        this.localfoodImgUrl = localfoodImgUrl;
    }

    public ResultLocalfood withLocalfoodImgUrl(String localfoodImgUrl) {
        this.localfoodImgUrl = localfoodImgUrl;
        return this;
    }

    public String getLocalfoodProvinceName() {
        return localfoodProvinceName;
    }

    public void setLocalfoodProvinceName(String localfoodProvinceName) {
        this.localfoodProvinceName = localfoodProvinceName;
    }

    public ResultLocalfood withLocalfoodProvinceName(String localfoodProvinceName) {
        this.localfoodProvinceName = localfoodProvinceName;
        return this;
    }

    public String getLocalfoodProvinceId() {
        return localfoodProvinceId;
    }

    public void setLocalfoodProvinceId(String localfoodProvinceId) {
        this.localfoodProvinceId = localfoodProvinceId;
    }

    public ResultLocalfood withLocalfoodProvinceId(String localfoodProvinceId) {
        this.localfoodProvinceId = localfoodProvinceId;
        return this;
    }

}
