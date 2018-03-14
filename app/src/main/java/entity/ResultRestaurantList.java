
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultRestaurantList implements Serializable{

    @SerializedName("Result_Restaurant")
    @Expose
    private ResultRestaurant resultRestaurant;

    public ResultRestaurant getResultRestaurant() {
        return resultRestaurant;
    }

    public void setResultRestaurant(ResultRestaurant resultRestaurant) {
        this.resultRestaurant = resultRestaurant;
    }

    public ResultRestaurantList withResultRestaurant(ResultRestaurant resultRestaurant) {
        this.resultRestaurant = resultRestaurant;
        return this;
    }

}
