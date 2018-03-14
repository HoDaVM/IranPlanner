
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultRestaurantFull implements Serializable{

    @SerializedName("Result_Restaurant")
    @Expose
    private ResultRestaurant resultRestaurant;

    public ResultRestaurant getResultRestaurant() {
        return resultRestaurant;
    }

    public void setResultRestaurant(ResultRestaurant resultRestaurant) {
        this.resultRestaurant = resultRestaurant;
    }

    public ResultRestaurantFull withResultRestaurant(ResultRestaurant resultRestaurant) {
        this.resultRestaurant = resultRestaurant;
        return this;
    }

}
