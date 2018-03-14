
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantFacility implements Serializable {

    @SerializedName("facility_title")
    @Expose
    private String facilityTitle;

    public String getFacilityTitle() {
        return facilityTitle;
    }

    public void setFacilityTitle(String facilityTitle) {
        this.facilityTitle = facilityTitle;
    }

    public RestaurantFacility withFacilityTitle(String facilityTitle) {
        this.facilityTitle = facilityTitle;
        return this;
    }

}
