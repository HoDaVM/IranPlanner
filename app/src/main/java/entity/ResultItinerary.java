
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultItinerary  implements Serializable  {
    @SerializedName("rate")
    @Expose
    private Rate rate;
    @SerializedName("itinerary_id")
    @Expose
    private String itineraryId;
    @SerializedName("itinerary_lang")
    @Expose
    private String itineraryLang;
    @SerializedName("itinerary_duration")
    @Expose
    private String itineraryDuration;
    @SerializedName("itinerary_duration_id")
    @Expose
    private String itineraryDurationId;

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    @SerializedName("itinerary_from_city_name")
    @Expose
    private String itineraryFromCityName;
    @SerializedName("itinerary_to_city_name")
    @Expose
    private String itineraryToCityName;
    @SerializedName("itinerary_to_city_id")
    @Expose
    private String itineraryToCityId;
    @SerializedName("itinerary_from_city_id")
    @Expose
    private String itineraryFromCityId;
    @SerializedName("itinerary_transport_id")
    @Expose
    private String itineraryTransportId;
    @SerializedName("itinerary_transport_name")
    @Expose
    private String itineraryTransportName;
    @SerializedName("itinerary_slogan")
    @Expose
    private String itinerarySlogan;
    @SerializedName("itinerary_to_pos_lat")
    @Expose
    private String itineraryToPosLat;
    @SerializedName("itinerary_to_pos_lon")
    @Expose
    private String itineraryToPosLon;
    @SerializedName("itinerary_from_pos_lat")
    @Expose
    private String itineraryFromPosLat;
    @SerializedName("itinerary_from_pos_lon")
    @Expose
    private String itineraryFromPosLon;
    @SerializedName("itinerary_img_url")
    @Expose
    private String itineraryImgUrl;
    @SerializedName("itinerary_site_url")
    @Expose
    private String itinerarySiteUrl;
    @SerializedName("itinerary_count_attraction")
    @Expose
    private String itineraryCountAttraction;
    @SerializedName("itinerary_percentage")
    @Expose
    private List<ItineraryPercentage> itineraryPercentage = null;
    @SerializedName("itinerary_body")
    @Expose
    private String itineraryBody;
    @SerializedName("itinerary_Lodging_city")
    @Expose
    private List<ItineraryLodgingCity> itineraryLodgingCity = null;
    @SerializedName("itinerary_difficulty")
    @Expose
    private ItineraryDifficulty itineraryDifficulty;
    @SerializedName("itinerary_seasson")
    @Expose
    private List<ItinerarySeasson> itinerarySeasson = null;

    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getItineraryLang() {
        return itineraryLang;
    }

    public void setItineraryLang(String itineraryLang) {
        this.itineraryLang = itineraryLang;
    }

    public String getItineraryDuration() {
        return itineraryDuration;
    }

    public void setItineraryDuration(String itineraryDuration) {
        this.itineraryDuration = itineraryDuration;
    }

    public String getItineraryDurationId() {
        return itineraryDurationId;
    }

    public void setItineraryDurationId(String itineraryDurationId) {
        this.itineraryDurationId = itineraryDurationId;
    }

    public String getItineraryFromCityName() {
        return itineraryFromCityName;
    }

    public void setItineraryFromCityName(String itineraryFromCityName) {
        this.itineraryFromCityName = itineraryFromCityName;
    }

    public String getItineraryToCityName() {
        return itineraryToCityName;
    }

    public void setItineraryToCityName(String itineraryToCityName) {
        this.itineraryToCityName = itineraryToCityName;
    }

    public String getItineraryToCityId() {
        return itineraryToCityId;
    }

    public void setItineraryToCityId(String itineraryToCityId) {
        this.itineraryToCityId = itineraryToCityId;
    }

    public String getItineraryFromCityId() {
        return itineraryFromCityId;
    }

    public void setItineraryFromCityId(String itineraryFromCityId) {
        this.itineraryFromCityId = itineraryFromCityId;
    }

    public String getItineraryTransportId() {
        return itineraryTransportId;
    }

    public void setItineraryTransportId(String itineraryTransportId) {
        this.itineraryTransportId = itineraryTransportId;
    }

    public String getItineraryTransportName() {
        return itineraryTransportName;
    }

    public void setItineraryTransportName(String itineraryTransportName) {
        this.itineraryTransportName = itineraryTransportName;
    }

    public String getItinerarySlogan() {
        return itinerarySlogan;
    }

    public void setItinerarySlogan(String itinerarySlogan) {
        this.itinerarySlogan = itinerarySlogan;
    }

    public String getItineraryToPosLat() {
        return itineraryToPosLat;
    }

    public void setItineraryToPosLat(String itineraryToPosLat) {
        this.itineraryToPosLat = itineraryToPosLat;
    }

    public String getItineraryToPosLon() {
        return itineraryToPosLon;
    }

    public void setItineraryToPosLon(String itineraryToPosLon) {
        this.itineraryToPosLon = itineraryToPosLon;
    }

    public String getItineraryFromPosLat() {
        return itineraryFromPosLat;
    }

    public void setItineraryFromPosLat(String itineraryFromPosLat) {
        this.itineraryFromPosLat = itineraryFromPosLat;
    }

    public String getItineraryFromPosLon() {
        return itineraryFromPosLon;
    }

    public void setItineraryFromPosLon(String itineraryFromPosLon) {
        this.itineraryFromPosLon = itineraryFromPosLon;
    }

    public String getItineraryImgUrl() {
        return itineraryImgUrl;
    }

    public void setItineraryImgUrl(String itineraryImgUrl) {
        this.itineraryImgUrl = itineraryImgUrl;
    }

    public String getItinerarySiteUrl() {
        return itinerarySiteUrl;
    }

    public void setItinerarySiteUrl(String itinerarySiteUrl) {
        this.itinerarySiteUrl = itinerarySiteUrl;
    }

    public String getItineraryCountAttraction() {
        return itineraryCountAttraction;
    }

    public void setItineraryCountAttraction(String itineraryCountAttraction) {
        this.itineraryCountAttraction = itineraryCountAttraction;
    }

    public List<ItineraryPercentage> getItineraryPercentage() {
        return itineraryPercentage;
    }

    public void setItineraryPercentage(List<ItineraryPercentage> itineraryPercentage) {
        this.itineraryPercentage = itineraryPercentage;
    }

    public String getItineraryBody() {
        return itineraryBody;
    }

    public void setItineraryBody(String itineraryBody) {
        this.itineraryBody = itineraryBody;
    }

    public List<ItineraryLodgingCity> getItineraryLodgingCity() {
        return itineraryLodgingCity;
    }

    public void setItineraryLodgingCity(List<ItineraryLodgingCity> itineraryLodgingCity) {
        this.itineraryLodgingCity = itineraryLodgingCity;
    }

    public ItineraryDifficulty getItineraryDifficulty() {
        return itineraryDifficulty;
    }

    public void setItineraryDifficulty(ItineraryDifficulty itineraryDifficulty) {
        this.itineraryDifficulty = itineraryDifficulty;
    }

    public List<ItinerarySeasson> getItinerarySeasson() {
        return itinerarySeasson;
    }

    public void setItinerarySeasson(List<ItinerarySeasson> itinerarySeasson) {
        this.itinerarySeasson = itinerarySeasson;
    }

}
