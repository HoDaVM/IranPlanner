
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultSouvenirFull implements Serializable {

    @SerializedName("souvenir_id")
    @Expose
    private String souvenirId;
    @SerializedName("souvenir_title")
    @Expose
    private String souvenirTitle;
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
    @SerializedName("souvenir_body")
    @Expose
    private String souvenirBody;
    @SerializedName("Result_souvenir_list")
    @Expose
    private List<ResultSouvenirList> resultSouvenirList = null;

    public String getSouvenirId() {
        return souvenirId;
    }

    public void setSouvenirId(String souvenirId) {
        this.souvenirId = souvenirId;
    }

    public ResultSouvenirFull withSouvenirId(String souvenirId) {
        this.souvenirId = souvenirId;
        return this;
    }

    public String getSouvenirTitle() {
        return souvenirTitle;
    }

    public void setSouvenirTitle(String souvenirTitle) {
        this.souvenirTitle = souvenirTitle;
    }

    public ResultSouvenirFull withSouvenirTitle(String souvenirTitle) {
        this.souvenirTitle = souvenirTitle;
        return this;
    }

    public String getSouvenirProvinceId() {
        return souvenirProvinceId;
    }

    public void setSouvenirProvinceId(String souvenirProvinceId) {
        this.souvenirProvinceId = souvenirProvinceId;
    }

    public ResultSouvenirFull withSouvenirProvinceId(String souvenirProvinceId) {
        this.souvenirProvinceId = souvenirProvinceId;
        return this;
    }

    public String getSouvenirProvinceName() {
        return souvenirProvinceName;
    }

    public void setSouvenirProvinceName(String souvenirProvinceName) {
        this.souvenirProvinceName = souvenirProvinceName;
    }

    public ResultSouvenirFull withSouvenirProvinceName(String souvenirProvinceName) {
        this.souvenirProvinceName = souvenirProvinceName;
        return this;
    }

    public String getSouvenirCityName() {
        return souvenirCityName;
    }

    public void setSouvenirCityName(String souvenirCityName) {
        this.souvenirCityName = souvenirCityName;
    }

    public ResultSouvenirFull withSouvenirCityName(String souvenirCityName) {
        this.souvenirCityName = souvenirCityName;
        return this;
    }

    public String getSouvenirCityId() {
        return souvenirCityId;
    }

    public void setSouvenirCityId(String souvenirCityId) {
        this.souvenirCityId = souvenirCityId;
    }

    public ResultSouvenirFull withSouvenirCityId(String souvenirCityId) {
        this.souvenirCityId = souvenirCityId;
        return this;
    }

    public String getSouvenirImgUrl() {
        return souvenirImgUrl;
    }

    public void setSouvenirImgUrl(String souvenirImgUrl) {
        this.souvenirImgUrl = souvenirImgUrl;
    }

    public ResultSouvenirFull withSouvenirImgUrl(String souvenirImgUrl) {
        this.souvenirImgUrl = souvenirImgUrl;
        return this;
    }

    public String getSouvenirBody() {
        return souvenirBody;
    }

    public void setSouvenirBody(String souvenirBody) {
        this.souvenirBody = souvenirBody;
    }

    public ResultSouvenirFull withSouvenirBody(String souvenirBody) {
        this.souvenirBody = souvenirBody;
        return this;
    }

    public List<ResultSouvenirList> getResultSouvenirList() {
        return resultSouvenirList;
    }

    public void setResultSouvenirList(List<ResultSouvenirList> resultSouvenirList) {
        this.resultSouvenirList = resultSouvenirList;
    }

    public ResultSouvenirFull withResultSouvenirList(List<ResultSouvenirList> resultSouvenirList) {
        this.resultSouvenirList = resultSouvenirList;
        return this;
    }

}
