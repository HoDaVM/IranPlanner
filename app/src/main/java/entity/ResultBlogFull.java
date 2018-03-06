
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultBlogFull implements Serializable{

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_post_full")
    @Expose
    private ResultPostFull resultPostFull;
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

    public ResultPostFull getResultPostFull() {
        return resultPostFull;
    }

    public void setResultPostFull(ResultPostFull resultPostFull) {
        this.resultPostFull = resultPostFull;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

}
