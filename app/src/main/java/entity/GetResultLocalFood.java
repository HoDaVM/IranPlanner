
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetResultLocalFood implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_localfood_full")
    @Expose
    private ResultLocalfoodFull resultLocalfoodFull;
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

    public GetResultLocalFood withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultLocalfoodFull getResultLocalfoodFull() {
        return resultLocalfoodFull;
    }

    public void setResultLocalfoodFull(ResultLocalfoodFull resultLocalfoodFull) {
        this.resultLocalfoodFull = resultLocalfoodFull;
    }

    public GetResultLocalFood withResultLocalfoodFull(ResultLocalfoodFull resultLocalfoodFull) {
        this.resultLocalfoodFull = resultLocalfoodFull;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public GetResultLocalFood withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public GetResultLocalFood withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
