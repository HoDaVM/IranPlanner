
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultItnAdd implements Serializable {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("itn_title")
    @Expose
    private String itnTitle;
    @SerializedName("itn_visibility")
    @Expose
    private String itnVisibility;
    @SerializedName("itn_description")
    @Expose
    private String itnDescription;
    @SerializedName("itn_id")
    @Expose
    private String itnId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ResultItnAdd withUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getItnTitle() {
        return itnTitle;
    }

    public void setItnTitle(String itnTitle) {
        this.itnTitle = itnTitle;
    }

    public ResultItnAdd withItnTitle(String itnTitle) {
        this.itnTitle = itnTitle;
        return this;
    }

    public String getItnVisibility() {
        return itnVisibility;
    }

    public void setItnVisibility(String itnVisibility) {
        this.itnVisibility = itnVisibility;
    }

    public ResultItnAdd withItnVisibility(String itnVisibility) {
        this.itnVisibility = itnVisibility;
        return this;
    }

    public String getItnDescription() {
        return itnDescription;
    }

    public void setItnDescription(String itnDescription) {
        this.itnDescription = itnDescription;
    }

    public ResultItnAdd withItnDescription(String itnDescription) {
        this.itnDescription = itnDescription;
        return this;
    }

    public String getItnId() {
        return itnId;
    }

    public void setItnId(String itnId) {
        this.itnId = itnId;
    }

    public ResultItnAdd withItnId(String itnId) {
        this.itnId = itnId;
        return this;
    }

}
