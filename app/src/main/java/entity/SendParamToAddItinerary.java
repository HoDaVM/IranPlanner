
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendParamToAddItinerary implements Serializable {

    public SendParamToAddItinerary(String uid, String itn_title, String itn_visibility, String itn_description) {
        this.uid = uid;
        this.itn_title = itn_title;
        this.itn_visibility = itn_visibility;
        this.itn_description = itn_description;
    }

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("itn_title")
    @Expose
    private String itn_title;
    @SerializedName("itn_visibility")
    @Expose
    private String itn_visibility;
    @SerializedName("itn_description")
    @Expose
    private String itn_description;

    public String getUid() {
        return uid;
    }

    public String getItn_title() {
        return itn_title;
    }

    public void setItn_title(String itn_title) {
        this.itn_title = itn_title;
    }

    public String getItn_visibility() {
        return itn_visibility;
    }

    public void setItn_visibility(String itn_visibility) {
        this.itn_visibility = itn_visibility;
    }

    public String getItn_description() {
        return itn_description;
    }

    public void setItn_description(String itn_description) {
        this.itn_description = itn_description;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }




}
