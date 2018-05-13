
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultUserItnFull implements Serializable {

    @SerializedName("itn_id")
    @Expose
    private String itnId;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("itn_title")
    @Expose
    private String itnTitle;
    @SerializedName("itn_visibility")
    @Expose
    private String itnVisibility;
    @SerializedName("itn_date_create")
    @Expose
    private String itnDateCreate;
    @SerializedName("itn_daily")
    @Expose
    private List<ItnDaily> itnDaily = null;
    @SerializedName("itn_localfood")
    @Expose
    private String itnLocalfood;
    @SerializedName("itn_souvenirs")
    @Expose
    private String itnSouvenirs;

    public String getItnId() {
        return itnId;
    }

    public void setItnId(String itnId) {
        this.itnId = itnId;
    }

    public ResultUserItnFull withItnId(String itnId) {
        this.itnId = itnId;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ResultUserItnFull withUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getItnTitle() {
        return itnTitle;
    }

    public void setItnTitle(String itnTitle) {
        this.itnTitle = itnTitle;
    }

    public ResultUserItnFull withItnTitle(String itnTitle) {
        this.itnTitle = itnTitle;
        return this;
    }

    public String getItnVisibility() {
        return itnVisibility;
    }

    public void setItnVisibility(String itnVisibility) {
        this.itnVisibility = itnVisibility;
    }

    public ResultUserItnFull withItnVisibility(String itnVisibility) {
        this.itnVisibility = itnVisibility;
        return this;
    }

    public String getItnDateCreate() {
        return itnDateCreate;
    }

    public void setItnDateCreate(String itnDateCreate) {
        this.itnDateCreate = itnDateCreate;
    }

    public ResultUserItnFull withItnDateCreate(String itnDateCreate) {
        this.itnDateCreate = itnDateCreate;
        return this;
    }

    public List<ItnDaily> getItnDaily() {
        return itnDaily;
    }

    public void setItnDaily(List<ItnDaily> itnDaily) {
        this.itnDaily = itnDaily;
    }

    public ResultUserItnFull withItnDaily(List<ItnDaily> itnDaily) {
        this.itnDaily = itnDaily;
        return this;
    }

    public String getItnLocalfood() {
        return itnLocalfood;
    }

    public void setItnLocalfood(String itnLocalfood) {
        this.itnLocalfood = itnLocalfood;
    }

    public ResultUserItnFull withItnLocalfood(String itnLocalfood) {
        this.itnLocalfood = itnLocalfood;
        return this;
    }

    public String getItnSouvenirs() {
        return itnSouvenirs;
    }

    public void setItnSouvenirs(String itnSouvenirs) {
        this.itnSouvenirs = itnSouvenirs;
    }

    public ResultUserItnFull withItnSouvenirs(String itnSouvenirs) {
        this.itnSouvenirs = itnSouvenirs;
        return this;
    }

}
