
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantFull implements Serializable{

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_Restaurant_Full")
    @Expose
    private ResultRestaurantFull resultRestaurantFull;
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

    public RestaurantFull withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultRestaurantFull getResultRestaurantFull() {
        return resultRestaurantFull;
    }

    public void setResultRestaurantFull(ResultRestaurantFull resultRestaurantFull) {
        this.resultRestaurantFull = resultRestaurantFull;
    }

    public RestaurantFull withResultRestaurantFull(ResultRestaurantFull resultRestaurantFull) {
        this.resultRestaurantFull = resultRestaurantFull;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public RestaurantFull withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public RestaurantFull withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
