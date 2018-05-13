
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyItineraryAdd implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_itn_add")
    @Expose
    private ResultItnAdd resultItnAdd;
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

    public MyItineraryAdd withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultItnAdd getResultItnAdd() {
        return resultItnAdd;
    }

    public void setResultItnAdd(ResultItnAdd resultItnAdd) {
        this.resultItnAdd = resultItnAdd;
    }

    public MyItineraryAdd withResultItnAdd(ResultItnAdd resultItnAdd) {
        this.resultItnAdd = resultItnAdd;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public MyItineraryAdd withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public MyItineraryAdd withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
