
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultRatePost implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("rate_final_param")
    @Expose
    private List<RateFinalParam> rateFinalParam = null;
    @SerializedName("rate_final_avg")
    @Expose
    private String rateFinalAvg;
    @SerializedName("rate_final_count")
    @Expose
    private Integer rateFinalCount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RateFinalParam> getRateFinalParam() {
        return rateFinalParam;
    }

    public void setRateFinalParam(List<RateFinalParam> rateFinalParam) {
        this.rateFinalParam = rateFinalParam;
    }

    public String getRateFinalAvg() {
        return rateFinalAvg;
    }

    public void setRateFinalAvg(String rateFinalAvg) {
        this.rateFinalAvg = rateFinalAvg;
    }

    public Integer getRateFinalCount() {
        return rateFinalCount;
    }

    public void setRateFinalCount(Integer rateFinalCount) {
        this.rateFinalCount = rateFinalCount;
    }

}
