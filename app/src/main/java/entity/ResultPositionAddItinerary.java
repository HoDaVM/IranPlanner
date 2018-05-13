
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultPositionAddItinerary implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_itn_position_add")
    @Expose
    private ResultItnPositionAdd resultItnPositionAdd;
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

    public ResultPositionAddItinerary withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultItnPositionAdd getResultItnPositionAdd() {
        return resultItnPositionAdd;
    }

    public void setResultItnPositionAdd(ResultItnPositionAdd resultItnPositionAdd) {
        this.resultItnPositionAdd = resultItnPositionAdd;
    }

    public ResultPositionAddItinerary withResultItnPositionAdd(ResultItnPositionAdd resultItnPositionAdd) {
        this.resultItnPositionAdd = resultItnPositionAdd;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ResultPositionAddItinerary withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public ResultPositionAddItinerary withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
