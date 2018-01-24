
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RateFinalParam implements Serializable {

    @SerializedName("gtype")
    @Expose
    private String gtype;
    @SerializedName("value_sum")
    @Expose
    private String valueSum;
    @SerializedName("value_count")
    @Expose
    private String valueCount;
    @SerializedName("value_avg")
    @Expose
    private String valueAvg;
    @SerializedName("value_title")
    @Expose
    private String valueTitle;

    public String getValueTitle() {
        return valueTitle;
    }

    public void setValueTitle(String valueTitle) {
        this.valueTitle = valueTitle;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public String getValueSum() {
        return valueSum;
    }

    public void setValueSum(String valueSum) {
        this.valueSum = valueSum;
    }

    public String getValueCount() {
        return valueCount;
    }

    public void setValueCount(String valueCount) {
        this.valueCount = valueCount;
    }

    public String getValueAvg() {
        return valueAvg;
    }

    public void setValueAvg(String valueAvg) {
        this.valueAvg = valueAvg;
    }

}
