
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultGlobalSearch implements Serializable{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("province_name")
    @Expose
    private String provinceName;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("position_lat")
    @Expose
    private String positionLat;
    @SerializedName("position_lon")
    @Expose
    private String positionLon;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("type_title")
    @Expose
    private String typeTitle;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("rate_title")
    @Expose
    private String rateTitle;
    @SerializedName("rate_id")
    @Expose
    private String rateId;
    @SerializedName("rate_int")
    @Expose
    private String rateInt;
    @SerializedName("post_author_id")
    @Expose
    private String postAuthorId;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("author_name")
    @Expose
    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getType() {
        return type;

    }

    public void setType(String type) {
        this.type = type;
    }

    public ResultGlobalSearch withType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultGlobalSearch withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ResultGlobalSearch withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public ResultGlobalSearch withProvinceName(String provinceName) {
        this.provinceName = provinceName;
        return this;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public ResultGlobalSearch withProvinceId(String provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ResultGlobalSearch withCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public ResultGlobalSearch withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public ResultGlobalSearch withPositionLat(String positionLat) {
        this.positionLat = positionLat;
        return this;
    }

    public String getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(String positionLon) {
        this.positionLon = positionLon;
    }

    public ResultGlobalSearch withPositionLon(String positionLon) {
        this.positionLon = positionLon;
        return this;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public ResultGlobalSearch withTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public ResultGlobalSearch withTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ResultGlobalSearch withLang(String lang) {
        this.lang = lang;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ResultGlobalSearch withImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getRateTitle() {
        return rateTitle;
    }

    public void setRateTitle(String rateTitle) {
        this.rateTitle = rateTitle;
    }

    public ResultGlobalSearch withRateTitle(String rateTitle) {
        this.rateTitle = rateTitle;
        return this;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public ResultGlobalSearch withRateId(String rateId) {
        this.rateId = rateId;
        return this;
    }

    public String getRateInt() {
        return rateInt;
    }

    public void setRateInt(String rateInt) {
        this.rateInt = rateInt;
    }

    public ResultGlobalSearch withRateInt(String rateInt) {
        this.rateInt = rateInt;
        return this;
    }

    public String getPostAuthorId() {
        return postAuthorId;
    }

    public void setPostAuthorId(String postAuthorId) {
        this.postAuthorId = postAuthorId;
    }

    public ResultGlobalSearch withPostAuthorId(String postAuthorId) {
        this.postAuthorId = postAuthorId;
        return this;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public ResultGlobalSearch withPostDate(String postDate) {
        this.postDate = postDate;
        return this;
    }

}
