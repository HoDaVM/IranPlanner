
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Position implements Serializable {

    @SerializedName("position_lat")
    @Expose
    private String positionLat;
    @SerializedName("position_lon")
    @Expose
    private String positionLon;

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public Position withPositionLat(String positionLat) {
        this.positionLat = positionLat;
        return this;
    }

    public String getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(String positionLon) {
        this.positionLon = positionLon;
    }

    public Position withPositionLon(String positionLon) {
        this.positionLon = positionLon;
        return this;
    }

}
