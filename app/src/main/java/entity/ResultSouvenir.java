
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultSouvenir implements Serializable{

    @SerializedName("souvenir_id")
    @Expose
    private String souvenirId;
    @SerializedName("souvenir_name")
    @Expose
    private String souvenirName;
    @SerializedName("souvenir_description")
    @Expose
    private String souvenirDescription;
    @SerializedName("souvenir_province_id")
    @Expose
    private String souvenirProvinceId;
    @SerializedName("souvenir_province_name")
    @Expose
    private String souvenirProvinceName;
    @SerializedName("souvenir_city_name")
    @Expose
    private String souvenirCityName;
    @SerializedName("souvenir_city_id")
    @Expose
    private String souvenirCityId;
    @SerializedName("souvenir_img_url")
    @Expose
    private String souvenirImgUrl;

    public String getSouvenirId() {
        return souvenirId;
    }

    public void setSouvenirId(String souvenirId) {
        this.souvenirId = souvenirId;
    }

    public ResultSouvenir withSouvenirId(String souvenirId) {
        this.souvenirId = souvenirId;
        return this;
    }

    public String getSouvenirName() {
        return souvenirName;
    }

    public void setSouvenirName(String souvenirName) {
        this.souvenirName = souvenirName;
    }

    public ResultSouvenir withSouvenirName(String souvenirName) {
        this.souvenirName = souvenirName;
        return this;
    }

    public String getSouvenirDescription() {
        return souvenirDescription;
    }

    public void setSouvenirDescription(String souvenirDescription) {
        this.souvenirDescription = souvenirDescription;
    }

    public ResultSouvenir withSouvenirDescription(String souvenirDescription) {
        this.souvenirDescription = souvenirDescription;
        return this;
    }

    public String getSouvenirProvinceId() {
        return souvenirProvinceId;
    }

    public void setSouvenirProvinceId(String souvenirProvinceId) {
        this.souvenirProvinceId = souvenirProvinceId;
    }

    public ResultSouvenir withSouvenirProvinceId(String souvenirProvinceId) {
        this.souvenirProvinceId = souvenirProvinceId;
        return this;
    }

    public String getSouvenirProvinceName() {
        return souvenirProvinceName;
    }

    public void setSouvenirProvinceName(String souvenirProvinceName) {
        this.souvenirProvinceName = souvenirProvinceName;
    }

    public ResultSouvenir withSouvenirProvinceName(String souvenirProvinceName) {
        this.souvenirProvinceName = souvenirProvinceName;
        return this;
    }

    public String getSouvenirCityName() {
        return souvenirCityName;
    }

    public void setSouvenirCityName(String souvenirCityName) {
        this.souvenirCityName = souvenirCityName;
    }

    public ResultSouvenir withSouvenirCityName(String souvenirCityName) {
        this.souvenirCityName = souvenirCityName;
        return this;
    }

    public String getSouvenirCityId() {
        return souvenirCityId;
    }

    public void setSouvenirCityId(String souvenirCityId) {
        this.souvenirCityId = souvenirCityId;
    }

    public ResultSouvenir withSouvenirCityId(String souvenirCityId) {
        this.souvenirCityId = souvenirCityId;
        return this;
    }

    public String getSouvenirImgUrl() {
        return souvenirImgUrl;
    }

    public void setSouvenirImgUrl(String souvenirImgUrl) {
        this.souvenirImgUrl = souvenirImgUrl;
    }

    public ResultSouvenir withSouvenirImgUrl(String souvenirImgUrl) {
        this.souvenirImgUrl = souvenirImgUrl;
        return this;
    }

}
