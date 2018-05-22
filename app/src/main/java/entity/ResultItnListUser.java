
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by h.vahidimehr on 05/05/2018.
 */
public class ResultItnListUser implements Serializable {

    @SerializedName("itn_title")
    @Expose
    private String itnTitle;
    @SerializedName("itn_id")
    @Expose
    private String itnId;
    @SerializedName("itn_date_create")
    @Expose
    private String itnDateCreate;
    @SerializedName("img_url")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ResultItnListUser(String itnTitle, String itnId, String itnVisibility, String itnInside) {
        this.itnTitle = itnTitle;
        this.itnId = itnId;

        this.itnVisibility = itnVisibility;
        this.itnInside = itnInside;
    }

    @SerializedName("itn_visibility")
    @Expose

    private String itnVisibility;
    @SerializedName("itn_inside")
    @Expose
    private String itnInside;

    public String getItnTitle() {
        return itnTitle;
    }

    public void setItnTitle(String itnTitle) {
        this.itnTitle = itnTitle;
    }

    public ResultItnListUser withItnTitle(String itnTitle) {
        this.itnTitle = itnTitle;
        return this;
    }

    public String getItnId() {
        return itnId;
    }

    public void setItnId(String itnId) {
        this.itnId = itnId;
    }

    public ResultItnListUser withItnId(String itnId) {
        this.itnId = itnId;
        return this;
    }

    public String getItnDateCreate() {
        return itnDateCreate;
    }

    public void setItnDateCreate(String itnDateCreate) {
        this.itnDateCreate = itnDateCreate;
    }

    public ResultItnListUser withItnDateCreate(String itnDateCreate) {
        this.itnDateCreate = itnDateCreate;
        return this;
    }

    public String getItnVisibility() {
        return itnVisibility;
    }

    public void setItnVisibility(String itnVisibility) {
        this.itnVisibility = itnVisibility;
    }

    public ResultItnListUser withItnVisibility(String itnVisibility) {
        this.itnVisibility = itnVisibility;
        return this;
    }

    public String getItnInside() {
        return itnInside;
    }

    public void setItnInside(String itnInside) {
        this.itnInside = itnInside;
    }

    public ResultItnListUser withItnInside(String itnInside) {
        this.itnInside = itnInside;
        return this;
    }

}
