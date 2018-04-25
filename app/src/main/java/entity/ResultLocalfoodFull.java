
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultLocalfoodFull implements Serializable{

    @SerializedName("localfood_id")
    @Expose
    private String localfoodId;
    @SerializedName("localfood_title")
    @Expose
    private String localfoodTitle;
    @SerializedName("localfood_description")
    @Expose
    private String localfoodDescription;
    @SerializedName("localfood_recipe")
    @Expose
    private String localfoodRecipe;
    @SerializedName("localfood_img_url")
    @Expose
    private String localfoodImgUrl;
    @SerializedName("localfood_province_name")
    @Expose
    private String localfoodProvinceName;
    @SerializedName("localfood_province_id")
    @Expose
    private String localfoodProvinceId;
    @SerializedName("localfood_body")
    @Expose
    private String localfoodBody;
    @SerializedName("Result_localfood_list")
    @Expose
    private List<ResultLocalfoodList> resultLocalfoodList = null;

    public String getLocalfoodId() {
        return localfoodId;
    }

    public void setLocalfoodId(String localfoodId) {
        this.localfoodId = localfoodId;
    }

    public ResultLocalfoodFull withLocalfoodId(String localfoodId) {
        this.localfoodId = localfoodId;
        return this;
    }

    public String getLocalfoodTitle() {
        return localfoodTitle;
    }

    public void setLocalfoodTitle(String localfoodTitle) {
        this.localfoodTitle = localfoodTitle;
    }

    public ResultLocalfoodFull withLocalfoodTitle(String localfoodTitle) {
        this.localfoodTitle = localfoodTitle;
        return this;
    }

    public String getLocalfoodDescription() {
        return localfoodDescription;
    }

    public void setLocalfoodDescription(String localfoodDescription) {
        this.localfoodDescription = localfoodDescription;
    }

    public ResultLocalfoodFull withLocalfoodDescription(String localfoodDescription) {
        this.localfoodDescription = localfoodDescription;
        return this;
    }

    public String getLocalfoodRecipe() {
        return localfoodRecipe;
    }

    public void setLocalfoodRecipe(String localfoodRecipe) {
        this.localfoodRecipe = localfoodRecipe;
    }

    public ResultLocalfoodFull withLocalfoodRecipe(String localfoodRecipe) {
        this.localfoodRecipe = localfoodRecipe;
        return this;
    }

    public String getLocalfoodImgUrl() {
        return localfoodImgUrl;
    }

    public void setLocalfoodImgUrl(String localfoodImgUrl) {
        this.localfoodImgUrl = localfoodImgUrl;
    }

    public ResultLocalfoodFull withLocalfoodImgUrl(String localfoodImgUrl) {
        this.localfoodImgUrl = localfoodImgUrl;
        return this;
    }

    public String getLocalfoodProvinceName() {
        return localfoodProvinceName;
    }

    public void setLocalfoodProvinceName(String localfoodProvinceName) {
        this.localfoodProvinceName = localfoodProvinceName;
    }

    public ResultLocalfoodFull withLocalfoodProvinceName(String localfoodProvinceName) {
        this.localfoodProvinceName = localfoodProvinceName;
        return this;
    }

    public String getLocalfoodProvinceId() {
        return localfoodProvinceId;
    }

    public void setLocalfoodProvinceId(String localfoodProvinceId) {
        this.localfoodProvinceId = localfoodProvinceId;
    }

    public ResultLocalfoodFull withLocalfoodProvinceId(String localfoodProvinceId) {
        this.localfoodProvinceId = localfoodProvinceId;
        return this;
    }

    public String getLocalfoodBody() {
        return localfoodBody;
    }

    public void setLocalfoodBody(String localfoodBody) {
        this.localfoodBody = localfoodBody;
    }

    public ResultLocalfoodFull withLocalfoodBody(String localfoodBody) {
        this.localfoodBody = localfoodBody;
        return this;
    }

    public List<ResultLocalfoodList> getResultLocalfoodList() {
        return resultLocalfoodList;
    }

    public void setResultLocalfoodList(List<ResultLocalfoodList> resultLocalfoodList) {
        this.resultLocalfoodList = resultLocalfoodList;
    }

    public ResultLocalfoodFull withResultLocalfoodList(List<ResultLocalfoodList> resultLocalfoodList) {
        this.resultLocalfoodList = resultLocalfoodList;
        return this;
    }

}
