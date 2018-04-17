
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalResult implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_GlobalSearch")
    @Expose
    private List<ResultGlobalSearch> resultGlobalSearch = null;
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

    public GlobalResult withStatus(Status status) {
        this.status = status;
        return this;
    }

    public List<ResultGlobalSearch> getResultGlobalSearch() {
        return resultGlobalSearch;
    }

    public void setResultGlobalSearch(List<ResultGlobalSearch> resultGlobalSearch) {
        this.resultGlobalSearch = resultGlobalSearch;
    }

    public GlobalResult withResultGlobalSearch(List<ResultGlobalSearch> resultGlobalSearch) {
        this.resultGlobalSearch = resultGlobalSearch;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public GlobalResult withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public GlobalResult withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
