
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultParamUser implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_rate_post")
    @Expose
    private ResultRatePost resultRatePost;
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

    public ResultRatePost getResultRatePost() {
        return resultRatePost;
    }

    public void setResultRatePost(ResultRatePost resultRatePost) {
        this.resultRatePost = resultRatePost;
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
