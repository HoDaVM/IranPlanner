
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DayPosition implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("node_id")
    @Expose
    private String nodeId;
    @SerializedName("node_day")
    @Expose
    private String nodeDay;
    @SerializedName("node_order")
    @Expose
    private String nodeOrder;
    @SerializedName("node_type")
    @Expose
    private String nodeType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("city_title")
    @Expose
    private String cityTitle;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("province_title")
    @Expose
    private String provinceTitle;
    @SerializedName("position_lat")
    @Expose
    private String positionLat;
    @SerializedName("position_lon")
    @Expose
    private String positionLon;
    @SerializedName("attraction_price")
    @Expose
    private String attractionPrice;
    @SerializedName("attraction_estimated_time")
    @Expose
    private String attractionEstimatedTime;
    @SerializedName("attraction_difficulty")
    @Expose
    private String attractionDifficulty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DayPosition withId(String id) {
        this.id = id;
        return this;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public DayPosition withNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public String getNodeDay() {
        return nodeDay;
    }

    public void setNodeDay(String nodeDay) {
        this.nodeDay = nodeDay;
    }

    public DayPosition withNodeDay(String nodeDay) {
        this.nodeDay = nodeDay;
        return this;
    }

    public String getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(String nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public DayPosition withNodeOrder(String nodeOrder) {
        this.nodeOrder = nodeOrder;
        return this;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public DayPosition withNodeType(String nodeType) {
        this.nodeType = nodeType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DayPosition withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public DayPosition withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public DayPosition withCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
        return this;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public DayPosition withProvinceId(String provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public String getProvinceTitle() {
        return provinceTitle;
    }

    public void setProvinceTitle(String provinceTitle) {
        this.provinceTitle = provinceTitle;
    }

    public DayPosition withProvinceTitle(String provinceTitle) {
        this.provinceTitle = provinceTitle;
        return this;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public DayPosition withPositionLat(String positionLat) {
        this.positionLat = positionLat;
        return this;
    }

    public String getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(String positionLon) {
        this.positionLon = positionLon;
    }

    public DayPosition withPositionLon(String positionLon) {
        this.positionLon = positionLon;
        return this;
    }

    public String getAttractionPrice() {
        return attractionPrice;
    }

    public void setAttractionPrice(String attractionPrice) {
        this.attractionPrice = attractionPrice;
    }

    public DayPosition withAttractionPrice(String attractionPrice) {
        this.attractionPrice = attractionPrice;
        return this;
    }

    public String getAttractionEstimatedTime() {
        return attractionEstimatedTime;
    }

    public void setAttractionEstimatedTime(String attractionEstimatedTime) {
        this.attractionEstimatedTime = attractionEstimatedTime;
    }

    public DayPosition withAttractionEstimatedTime(String attractionEstimatedTime) {
        this.attractionEstimatedTime = attractionEstimatedTime;
        return this;
    }

    public String getAttractionDifficulty() {
        return attractionDifficulty;
    }

    public void setAttractionDifficulty(String attractionDifficulty) {
        this.attractionDifficulty = attractionDifficulty;
    }

    public DayPosition withAttractionDifficulty(String attractionDifficulty) {
        this.attractionDifficulty = attractionDifficulty;
        return this;
    }

}
