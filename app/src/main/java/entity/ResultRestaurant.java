
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultRestaurant implements Serializable {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_title")
    @Expose
    private String restaurantTitle;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("restaurant_4sq_id")
    @Expose
    private String restaurant4sqId;
    @SerializedName("restaurant_lang")
    @Expose
    private String restaurantLang;
    @SerializedName("googleplace_id")
    @Expose
    private String googleplaceId;
    @SerializedName("restaurant_position_lat")
    @Expose
    private String restaurantPositionLat;
    @SerializedName("restaurant_position_lon")
    @Expose
    private String restaurantPositionLon;
    @SerializedName("restaurant_type_id")
    @Expose
    private String restaurantTypeId;
    @SerializedName("restaurant_type_title")
    @Expose
    private String restaurantTypeTitle;
    @SerializedName("city_title")
    @Expose
    private String cityTitle;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("province_title")
    @Expose
    private String provinceTitle;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("restaurant_distance")
    @Expose
    private String restaurantDistance;
    @SerializedName("Restaurant_Phone")
    @Expose
    private String restaurantPhone;
    @SerializedName("Restaurant_Facility")
    @Expose
    private List<RestaurantFacility> restaurantFacility = null;
    @SerializedName("restaurant_body")
    @Expose
    private String restaurantBody;
    @SerializedName("rate")
    @Expose
    private Rate rate;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public ResultRestaurant withRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public String getRestaurantTitle() {
        return restaurantTitle;
    }

    public void setRestaurantTitle(String restaurantTitle) {
        this.restaurantTitle = restaurantTitle;
    }

    public ResultRestaurant withRestaurantTitle(String restaurantTitle) {
        this.restaurantTitle = restaurantTitle;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ResultRestaurant withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getRestaurant4sqId() {
        return restaurant4sqId;
    }

    public void setRestaurant4sqId(String restaurant4sqId) {
        this.restaurant4sqId = restaurant4sqId;
    }

    public ResultRestaurant withRestaurant4sqId(String restaurant4sqId) {
        this.restaurant4sqId = restaurant4sqId;
        return this;
    }

    public String getRestaurantLang() {
        return restaurantLang;
    }

    public void setRestaurantLang(String restaurantLang) {
        this.restaurantLang = restaurantLang;
    }

    public ResultRestaurant withRestaurantLang(String restaurantLang) {
        this.restaurantLang = restaurantLang;
        return this;
    }

    public String getGoogleplaceId() {
        return googleplaceId;
    }

    public void setGoogleplaceId(String googleplaceId) {
        this.googleplaceId = googleplaceId;
    }

    public ResultRestaurant withGoogleplaceId(String googleplaceId) {
        this.googleplaceId = googleplaceId;
        return this;
    }

    public String getRestaurantPositionLat() {
        return restaurantPositionLat;
    }

    public void setRestaurantPositionLat(String restaurantPositionLat) {
        this.restaurantPositionLat = restaurantPositionLat;
    }

    public ResultRestaurant withRestaurantPositionLat(String restaurantPositionLat) {
        this.restaurantPositionLat = restaurantPositionLat;
        return this;
    }

    public String getRestaurantPositionLon() {
        return restaurantPositionLon;
    }

    public void setRestaurantPositionLon(String restaurantPositionLon) {
        this.restaurantPositionLon = restaurantPositionLon;
    }

    public ResultRestaurant withRestaurantPositionLon(String restaurantPositionLon) {
        this.restaurantPositionLon = restaurantPositionLon;
        return this;
    }

    public String getRestaurantTypeId() {
        return restaurantTypeId;
    }

    public void setRestaurantTypeId(String restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
    }

    public ResultRestaurant withRestaurantTypeId(String restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
        return this;
    }

    public String getRestaurantTypeTitle() {
        return restaurantTypeTitle;
    }

    public void setRestaurantTypeTitle(String restaurantTypeTitle) {
        this.restaurantTypeTitle = restaurantTypeTitle;
    }

    public ResultRestaurant withRestaurantTypeTitle(String restaurantTypeTitle) {
        this.restaurantTypeTitle = restaurantTypeTitle;
        return this;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public ResultRestaurant withCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
        return this;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public ResultRestaurant withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getProvinceTitle() {
        return provinceTitle;
    }

    public void setProvinceTitle(String provinceTitle) {
        this.provinceTitle = provinceTitle;
    }

    public ResultRestaurant withProvinceTitle(String provinceTitle) {
        this.provinceTitle = provinceTitle;
        return this;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public ResultRestaurant withProvinceId(String provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ResultRestaurant withImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getRestaurantDistance() {
        return restaurantDistance;
    }

    public void setRestaurantDistance(String restaurantDistance) {
        this.restaurantDistance = restaurantDistance;
    }

    public ResultRestaurant withRestaurantDistance(String restaurantDistance) {
        this.restaurantDistance = restaurantDistance;
        return this;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public ResultRestaurant withRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
        return this;
    }

    public List<RestaurantFacility> getRestaurantFacility() {
        return restaurantFacility;
    }

    public void setRestaurantFacility(List<RestaurantFacility> restaurantFacility) {
        this.restaurantFacility = restaurantFacility;
    }

    public ResultRestaurant withRestaurantFacility(List<RestaurantFacility> restaurantFacility) {
        this.restaurantFacility = restaurantFacility;
        return this;
    }

    public String getRestaurantBody() {
        return restaurantBody;
    }

    public void setRestaurantBody(String restaurantBody) {
        this.restaurantBody = restaurantBody;
    }

    public ResultRestaurant withRestaurantBody(String restaurantBody) {
        this.restaurantBody = restaurantBody;
        return this;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public ResultRestaurant withRate(Rate rate) {
        this.rate = rate;
        return this;
    }

}
