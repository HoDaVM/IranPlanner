
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultEditDynamicItinerary implements Serializable{

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_user_itn_full")
    @Expose
    private ResultUserItnFull resultUserItnFull;
    @SerializedName("Statistics")
    @Expose
    private Statistics statistics;
    @SerializedName("ParsDID")
    @Expose
    private ParsDID parsDID;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ResultEditDynamicItinerary withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultUserItnFull getResultUserItnFull() {
        return resultUserItnFull;
    }

    public void setResultUserItnFull(ResultUserItnFull resultUserItnFull) {
        this.resultUserItnFull = resultUserItnFull;
    }

    public ResultEditDynamicItinerary withResultUserItnFull(ResultUserItnFull resultUserItnFull) {
        this.resultUserItnFull = resultUserItnFull;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ResultEditDynamicItinerary withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public ResultEditDynamicItinerary withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
