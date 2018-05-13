
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultItnUpdate implements Serializable{

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("itn_id")
    @Expose
    private String itnId;
    @SerializedName("day_count")
    @Expose
    private String dayCount;
    @SerializedName("itn_position")
    @Expose
    private List<ItnPosition> itnPosition = null;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ResultItnUpdate withUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getItnId() {
        return itnId;
    }

    public void setItnId(String itnId) {
        this.itnId = itnId;
    }

    public ResultItnUpdate withItnId(String itnId) {
        this.itnId = itnId;
        return this;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public ResultItnUpdate withDayCount(String dayCount) {
        this.dayCount = dayCount;
        return this;
    }

    public List<ItnPosition> getItnPosition() {
        return itnPosition;
    }

    public void setItnPosition(List<ItnPosition> itnPosition) {
        this.itnPosition = itnPosition;
    }

    public ResultItnUpdate withItnPosition(List<ItnPosition> itnPosition) {
        this.itnPosition = itnPosition;
        return this;
    }

}
