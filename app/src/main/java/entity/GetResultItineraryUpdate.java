
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetResultItineraryUpdate implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_itn_update")
    @Expose
    private ResultItnUpdate resultItnUpdate;
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

    public GetResultItineraryUpdate withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultItnUpdate getResultItnUpdate() {
        return resultItnUpdate;
    }

    public void setResultItnUpdate(ResultItnUpdate resultItnUpdate) {
        this.resultItnUpdate = resultItnUpdate;
    }

    public GetResultItineraryUpdate withResultItnUpdate(ResultItnUpdate resultItnUpdate) {
        this.resultItnUpdate = resultItnUpdate;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public GetResultItineraryUpdate withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public GetResultItineraryUpdate withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
