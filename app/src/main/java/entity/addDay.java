package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by h.vahidimehr on 09/05/2018.
 */

public class addDay implements Serializable {


    @SerializedName("uid")
    @Expose

    private String uid;
    @SerializedName("daysCount")
    @Expose
    private String daysCount;
    @SerializedName("itn_id")
    @Expose
    private String itnId;

    public addDay(String uid, String daysCount, String itnId) {
        this.uid = uid;
        this.daysCount = daysCount;
        this.itnId = itnId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(String daysCount) {
        this.daysCount = daysCount;
    }

    public String getItnId() {
        return itnId;
    }

    public void setItnId(String itnId) {
        this.itnId = itnId;
    }
}
