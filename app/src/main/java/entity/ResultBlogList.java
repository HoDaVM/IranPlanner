
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultBlogList implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_post_list")
    @Expose
    private List<ResultPostList> resultPostList = null;
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

    public List<ResultPostList> getResultPostList() {
        return resultPostList;
    }

    public void setResultPostList(List<ResultPostList> resultPostList) {
        this.resultPostList = resultPostList;
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
