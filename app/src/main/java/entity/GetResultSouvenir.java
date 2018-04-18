
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetResultSouvenir implements Serializable{

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_souvenir_full")
    @Expose
    private ResultSouvenirFull resultSouvenirFull;
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

    public GetResultSouvenir withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultSouvenirFull getResultSouvenirFull() {
        return resultSouvenirFull;
    }

    public void setResultSouvenirFull(ResultSouvenirFull resultSouvenirFull) {
        this.resultSouvenirFull = resultSouvenirFull;
    }

    public GetResultSouvenir withResultSouvenirFull(ResultSouvenirFull resultSouvenirFull) {
        this.resultSouvenirFull = resultSouvenirFull;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public GetResultSouvenir withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public GetResultSouvenir withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
