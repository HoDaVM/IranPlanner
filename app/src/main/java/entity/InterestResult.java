
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InterestResult implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_widget")
    @Expose
    private List<ResultWidget> resultWidget = null;
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

    public List<ResultWidget> getResultWidget() {
        return resultWidget;
    }

    public void setResultWidget(List<ResultWidget> resultWidget) {
        this.resultWidget = resultWidget;
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
