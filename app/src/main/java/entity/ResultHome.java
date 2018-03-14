
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultHome implements Serializable {

    @SerializedName("home_info")
    @Expose
    private HomeInfo homeInfo;
    @SerializedName("home_images")
    @Expose
    private List<HomeImage> homeImages = null;
    @SerializedName("home_event")
    @Expose
    private List<HomeEvent> homeEvent = null;
    @SerializedName("home_lodging")
    @Expose
    private List<HomeLodging> homeLodging = null;
    @SerializedName("home_lodging_type")
    @Expose
    private List<HomeLodgingType> homeLodgingType = null;
    @SerializedName("home_restaurant")
    @Expose
    private List<HomeRestaurant> homeRestaurant;

    public List<HomeRestaurant> getHomeRestaurant() {
        return homeRestaurant;
    }

    public void setHomeRestaurant(List<HomeRestaurant> homeRestaurant) {
        this.homeRestaurant = homeRestaurant;
    }
    @SerializedName("home_attraction")
    @Expose
    private List<HomeAttraction> homeAttraction = null;
    @SerializedName("home_attraction_type")
    @Expose
    private List<HomeAttractionType> homeAttractionType = null;
    @SerializedName("home_souvenirs")
    @Expose
    private List<HomeSouvenir> homeSouvenirs = null;
    @SerializedName("home_localfood")
    @Expose
    private List<HomeLocalfood> homeLocalfood = null;
    @SerializedName("home_airport")
    @Expose
    private List<HomeAirport> homeAirport = null;
    @SerializedName("home_neighbor_city")
    @Expose
    private List<HomeNeighborCity> homeNeighborCity = null;
    @SerializedName("home_province_city")
    @Expose
    private List<HomeProvinceCity> homeProvinceCity = null;
    @SerializedName("home_itinerary")
    @Expose
    private List<HomeItinerary> homeItinerary = null;

    public List<HomeCountryProvince> getHomeCountryProvince() {
        return homeCountryProvince;
    }

    public void setHomeCountryProvince(List<HomeCountryProvince> homeCountryProvince) {
        this.homeCountryProvince = homeCountryProvince;
    }

    @SerializedName("home_country_province")
    @Expose
    private List<HomeCountryProvince> homeCountryProvince = null;

    public HomeInfo getHomeInfo() {
        return homeInfo;
    }

    public void setHomeInfo(HomeInfo homeInfo) {
        this.homeInfo = homeInfo;
    }

    public List<HomeImage> getHomeImages() {
        return homeImages;
    }

    public void setHomeImages(List<HomeImage> homeImages) {
        this.homeImages = homeImages;
    }

    public List<HomeEvent> getHomeEvent() {
        return homeEvent;
    }

    public void setHomeEvent(List<HomeEvent> homeEvent) {
        this.homeEvent = homeEvent;
    }

    public List<HomeLodging> getHomeLodging() {
        return homeLodging;
    }

    public void setHomeLodging(List<HomeLodging> homeLodging) {
        this.homeLodging = homeLodging;
    }

    public List<HomeLodgingType> getHomeLodgingType() {
        return homeLodgingType;
    }

    public void setHomeLodgingType(List<HomeLodgingType> homeLodgingType) {
        this.homeLodgingType = homeLodgingType;
    }

    public List<HomeAttraction> getHomeAttraction() {
        return homeAttraction;
    }

    public void setHomeAttraction(List<HomeAttraction> homeAttraction) {
        this.homeAttraction = homeAttraction;
    }

    public List<HomeAttractionType> getHomeAttractionType() {
        return homeAttractionType;
    }

    public void setHomeAttractionType(List<HomeAttractionType> homeAttractionType) {
        this.homeAttractionType = homeAttractionType;
    }

    public List<HomeSouvenir> getHomeSouvenirs() {
        return homeSouvenirs;
    }

    public void setHomeSouvenirs(List<HomeSouvenir> homeSouvenirs) {
        this.homeSouvenirs = homeSouvenirs;
    }

    public List<HomeLocalfood> getHomeLocalfood() {
        return homeLocalfood;
    }

    public void setHomeLocalfood(List<HomeLocalfood> homeLocalfood) {
        this.homeLocalfood = homeLocalfood;
    }

    public List<HomeAirport> getHomeAirport() {
        return homeAirport;
    }

    public void setHomeAirport(List<HomeAirport> homeAirport) {
        this.homeAirport = homeAirport;
    }

    public List<HomeNeighborCity> getHomeNeighborCity() {
        return homeNeighborCity;
    }

    public void setHomeNeighborCity(List<HomeNeighborCity> homeNeighborCity) {
        this.homeNeighborCity = homeNeighborCity;
    }

    public List<HomeProvinceCity> getHomeProvinceCity() {
        return homeProvinceCity;
    }

    public void setHomeProvinceCity(List<HomeProvinceCity> homeProvinceCity) {
        this.homeProvinceCity = homeProvinceCity;
    }

    public List<HomeItinerary> getHomeItinerary() {
        return homeItinerary;
    }

    public void setHomeItinerary(List<HomeItinerary> homeItinerary) {
        this.homeItinerary = homeItinerary;
    }

}
