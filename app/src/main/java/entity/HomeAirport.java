
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeAirport implements Serializable{

    @SerializedName("airport_id")
    @Expose
    private String airportId;
    @SerializedName("airport_title")
    @Expose
    private String airportTitle;
    @SerializedName("position_lat")
    @Expose
    private String positionLat;
    @SerializedName("position_lon")
    @Expose
    private String positionLon;
    @SerializedName("airport_iata")
    @Expose
    private String airportIata;
    @SerializedName("airport_icao")
    @Expose
    private String airportIcao;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("province_name")
    @Expose
    private String provinceName;

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public String getAirportTitle() {
        return airportTitle;
    }

    public void setAirportTitle(String airportTitle) {
        this.airportTitle = airportTitle;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public String getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(String positionLon) {
        this.positionLon = positionLon;
    }

    public String getAirportIata() {
        return airportIata;
    }

    public void setAirportIata(String airportIata) {
        this.airportIata = airportIata;
    }

    public String getAirportIcao() {
        return airportIcao;
    }

    public void setAirportIcao(String airportIcao) {
        this.airportIcao = airportIcao;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

}
