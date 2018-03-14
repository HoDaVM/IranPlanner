
package entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResulRestaurant {

    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
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
    private Object googleplaceId;
    @SerializedName("restaurant_position_lat")
    @Expose
    private Double restaurantPositionLat;
    @SerializedName("restaurant_position_lon")
    @Expose
    private Double restaurantPositionLon;
    @SerializedName("restaurant_type_id")
    @Expose
    private Object restaurantTypeId;
    @SerializedName("restaurant_type_title")
    @Expose
    private Object restaurantTypeTitle;
    @SerializedName("city_title")
    @Expose
    private String cityTitle;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("province_title")
    @Expose
    private String provinceTitle;
    @SerializedName("province_id")
    @Expose
    private Integer provinceId;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("restaurant_distance")
    @Expose
    private Integer restaurantDistance;
    @SerializedName("Restaurant_Phone")
    @Expose
    private Integer restaurantPhone;
    @SerializedName("Restaurant_Facility")
    @Expose
    private List<Object> restaurantFacility = null;
    @SerializedName("restaurant_body")
    @Expose
    private Object restaurantBody;
    @SerializedName("rate")
    @Expose
    private Rate rate;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantTitle() {
        return restaurantTitle;
    }

    public void setRestaurantTitle(String restaurantTitle) {
        this.restaurantTitle = restaurantTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurant4sqId() {
        return restaurant4sqId;
    }

    public void setRestaurant4sqId(String restaurant4sqId) {
        this.restaurant4sqId = restaurant4sqId;
    }

    public String getRestaurantLang() {
        return restaurantLang;
    }

    public void setRestaurantLang(String restaurantLang) {
        this.restaurantLang = restaurantLang;
    }

    public Object getGoogleplaceId() {
        return googleplaceId;
    }

    public void setGoogleplaceId(Object googleplaceId) {
        this.googleplaceId = googleplaceId;
    }

    public Double getRestaurantPositionLat() {
        return restaurantPositionLat;
    }

    public void setRestaurantPositionLat(Double restaurantPositionLat) {
        this.restaurantPositionLat = restaurantPositionLat;
    }

    public Double getRestaurantPositionLon() {
        return restaurantPositionLon;
    }

    public void setRestaurantPositionLon(Double restaurantPositionLon) {
        this.restaurantPositionLon = restaurantPositionLon;
    }

    public Object getRestaurantTypeId() {
        return restaurantTypeId;
    }

    public void setRestaurantTypeId(Object restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
    }

    public Object getRestaurantTypeTitle() {
        return restaurantTypeTitle;
    }

    public void setRestaurantTypeTitle(Object restaurantTypeTitle) {
        this.restaurantTypeTitle = restaurantTypeTitle;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getProvinceTitle() {
        return provinceTitle;
    }

    public void setProvinceTitle(String provinceTitle) {
        this.provinceTitle = provinceTitle;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getRestaurantDistance() {
        return restaurantDistance;
    }

    public void setRestaurantDistance(Integer restaurantDistance) {
        this.restaurantDistance = restaurantDistance;
    }

    public Integer getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(Integer restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public List<Object> getRestaurantFacility() {
        return restaurantFacility;
    }

    public void setRestaurantFacility(List<Object> restaurantFacility) {
        this.restaurantFacility = restaurantFacility;
    }

    public Object getRestaurantBody() {
        return restaurantBody;
    }

    public void setRestaurantBody(Object restaurantBody) {
        this.restaurantBody = restaurantBody;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

}
