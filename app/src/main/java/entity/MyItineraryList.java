
package entity;
/**
 * Created by h.vahidimehr on 05/05/2018.
 */
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyItineraryList implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_itn_list_user")
    @Expose
    private List<ResultItnListUser> resultItnListUser = null;
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

    public MyItineraryList withStatus(Status status) {
        this.status = status;
        return this;
    }

    public List<ResultItnListUser> getResultItnListUser() {
        return resultItnListUser;
    }

    public void setResultItnListUser(List<ResultItnListUser> resultItnListUser) {
        this.resultItnListUser = resultItnListUser;
    }

    public MyItineraryList withResultItnListUser(List<ResultItnListUser> resultItnListUser) {
        this.resultItnListUser = resultItnListUser;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public MyItineraryList withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public MyItineraryList withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
