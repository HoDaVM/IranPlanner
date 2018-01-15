
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResulAttraction implements Serializable {

    @SerializedName("attraction_img_url")
    @Expose
    private String attractionImgUrl;
    @SerializedName("attraction_id")
    @Expose
    private String attractionId;
    @SerializedName("attraction_title")
    @Expose
    private String attractionTitle;
    @SerializedName("attraction_unesco")
    @Expose
    private String attractionUnesco;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("city_title")
    @Expose
    private String cityTitle;
    @SerializedName("province_title")
    @Expose
    private String provinceTitle;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("attraction_slogan")
    @Expose
    private String attractionSlogan;
    @SerializedName("attraction_difficulty")
    @Expose
    private String attractionDifficulty;
    @SerializedName("attraction_price")
    @Expose
    private String attractionPrice;
    @SerializedName("attraction_price_foreign")
    @Expose
    private String attractionPriceForeign;
    @SerializedName("attraction_itinerary_type_title")
    @Expose
    private String attractionItineraryTypeTitle;
    @SerializedName("attraction_itinerary_type_id")
    @Expose
    private String attractionItineraryTypeId;
    @SerializedName("attraction_address")
    @Expose
    private String attractionAddress;
    @SerializedName("attraction_position_lat")
    @Expose
    private String attractionPositionLat;
    @SerializedName("attraction_position_lon")
    @Expose
    private String attractionPositionLon;
    @SerializedName("attraction_4sq_id")
    @Expose
    private String attraction4sqId;
    @SerializedName("attraction_estimated_time")
    @Expose
    private String attractionEstimatedTime;
    @SerializedName("attraction_lang")
    @Expose
    private String attractionLang;
    @SerializedName("attraction_distance")
    @Expose
    private String attractionDistance;
    @SerializedName("attraction_body")
    @Expose
    private String attractionBody;
    @SerializedName("rate")
    @Expose
    private Rate rate;

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
    public String getAttractionImgUrl() {
        return attractionImgUrl;
    }

    public void setAttractionImgUrl(String attractionImgUrl) {
        this.attractionImgUrl = attractionImgUrl;
    }

    public String getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(String attractionId) {
        this.attractionId = attractionId;
    }

    public String getAttractionTitle() {
        return attractionTitle;
    }

    public void setAttractionTitle(String attractionTitle) {
        this.attractionTitle = attractionTitle;
    }

    public String getAttractionUnesco() {
        return attractionUnesco;
    }

    public void setAttractionUnesco(String attractionUnesco) {
        this.attractionUnesco = attractionUnesco;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public String getProvinceTitle() {
        return provinceTitle;
    }

    public void setProvinceTitle(String provinceTitle) {
        this.provinceTitle = provinceTitle;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getAttractionSlogan() {
        return attractionSlogan;
    }

    public void setAttractionSlogan(String attractionSlogan) {
        this.attractionSlogan = attractionSlogan;
    }

    public String getAttractionDifficulty() {
        return attractionDifficulty;
    }

    public void setAttractionDifficulty(String attractionDifficulty) {
        this.attractionDifficulty = attractionDifficulty;
    }

    public String getAttractionPrice() {
        return attractionPrice;
    }

    public void setAttractionPrice(String attractionPrice) {
        this.attractionPrice = attractionPrice;
    }

    public String getAttractionPriceForeign() {
        return attractionPriceForeign;
    }

    public void setAttractionPriceForeign(String attractionPriceForeign) {
        this.attractionPriceForeign = attractionPriceForeign;
    }

    public String getAttractionItineraryTypeTitle() {
        return attractionItineraryTypeTitle;
    }

    public void setAttractionItineraryTypeTitle(String attractionItineraryTypeTitle) {
        this.attractionItineraryTypeTitle = attractionItineraryTypeTitle;
    }

    public String getAttractionItineraryTypeId() {
        return attractionItineraryTypeId;
    }

    public void setAttractionItineraryTypeId(String attractionItineraryTypeId) {
        this.attractionItineraryTypeId = attractionItineraryTypeId;
    }

    public String getAttractionAddress() {
        return attractionAddress;
    }

    public void setAttractionAddress(String attractionAddress) {
        this.attractionAddress = attractionAddress;
    }

    public String getAttractionPositionLat() {
        return attractionPositionLat;
    }

    public void setAttractionPositionLat(String attractionPositionLat) {
        this.attractionPositionLat = attractionPositionLat;
    }

    public String getAttractionPositionLon() {
        return attractionPositionLon;
    }

    public void setAttractionPositionLon(String attractionPositionLon) {
        this.attractionPositionLon = attractionPositionLon;
    }

    public String getAttraction4sqId() {
        return attraction4sqId;
    }

    public void setAttraction4sqId(String attraction4sqId) {
        this.attraction4sqId = attraction4sqId;
    }

    public String getAttractionEstimatedTime() {
        return attractionEstimatedTime;
    }

    public void setAttractionEstimatedTime(String attractionEstimatedTime) {
        this.attractionEstimatedTime = attractionEstimatedTime;
    }

    public String getAttractionLang() {
        return attractionLang;
    }

    public void setAttractionLang(String attractionLang) {
        this.attractionLang = attractionLang;
    }

    public String getAttractionDistance() {
        return attractionDistance;
    }

    public void setAttractionDistance(String attractionDistance) {
        this.attractionDistance = attractionDistance;
    }

    public String getAttractionBody() {
        return attractionBody;
    }

    public void setAttractionBody(String attractionBody) {
        this.attractionBody = attractionBody;
    }

}
