
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rate implements Serializable {

    @SerializedName("rate_final_avg")
    @Expose
    private String rateFinalAvg;
    @SerializedName("rate_final_count")
    @Expose
    private String rateFinalCount;

    public String getRateFinalAvg() {
        return rateFinalAvg;
    }

    public void setRateFinalAvg(String rateFinalAvg) {
        this.rateFinalAvg = rateFinalAvg;
    }

    public String getRateFinalCount() {
        return rateFinalCount;
    }

    public void setRateFinalCount(String rateFinalCount) {
        this.rateFinalCount = rateFinalCount;
    }

}
