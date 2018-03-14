
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeRestaurant implements Serializable {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_title")
    @Expose
    private String restaurantTitle;
    @SerializedName("restaurant_city_id")
    @Expose
    private String restaurantCityId;
    @SerializedName("restaurant_province_id")
    @Expose
    private String restaurantProvinceId;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("distance")
    @Expose
    private String distance;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantTitle() {
        return restaurantTitle;
    }

    public void setRestaurantTitle(String restaurantTitle) {
        this.restaurantTitle = restaurantTitle;
    }

    public String getRestaurantCityId() {
        return restaurantCityId;
    }

    public void setRestaurantCityId(String restaurantCityId) {
        this.restaurantCityId = restaurantCityId;
    }

    public String getRestaurantProvinceId() {
        return restaurantProvinceId;
    }

    public void setRestaurantProvinceId(String restaurantProvinceId) {
        this.restaurantProvinceId = restaurantProvinceId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
