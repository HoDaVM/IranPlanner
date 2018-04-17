
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
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("image")
    @Expose
    private ImageGlobal image;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("rate")
    @Expose
    private Rate rate;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("parent_title")
    @Expose
    private String parentTitle;
    @SerializedName("position_lon")
    @Expose
    private Double positionLon;
    @SerializedName("position_lat")
    @Expose
    private Double positionLat;

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

    public ImageGlobal getImage() {
        return image;
    }

    public void setImage(ImageGlobal image) {
        this.image = image;
    }

    public ResultGlobalSearch withImage(ImageGlobal image) {
        this.image = image;
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ResultGlobalSearch withContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public ResultGlobalSearch withRate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ResultGlobalSearch withParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public ResultGlobalSearch withParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
        return this;
    }

    public Double getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(Double positionLon) {
        this.positionLon = positionLon;
    }

    public ResultGlobalSearch withPositionLon(Double positionLon) {
        this.positionLon = positionLon;
        return this;
    }

    public Double getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(Double positionLat) {
        this.positionLat = positionLat;
    }

    public ResultGlobalSearch withPositionLat(Double positionLat) {
        this.positionLat = positionLat;
        return this;
    }

}
