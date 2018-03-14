
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantList implements Serializable{

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_Restaurant_List")
    @Expose
    private List<ResultRestaurantList> resultRestaurantList = null;
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

    public RestaurantList withStatus(Status status) {
        this.status = status;
        return this;
    }

    public List<ResultRestaurantList> getResultRestaurantList() {
        return resultRestaurantList;
    }


    public void setResultRestaurantList(List<ResultRestaurantList> resultRestaurantList) {
        this.resultRestaurantList = resultRestaurantList;
    }

    public RestaurantList withResultRestaurantList(List<ResultRestaurantList> resultRestaurantList) {
        this.resultRestaurantList = resultRestaurantList;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public RestaurantList withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public RestaurantList withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
