
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityProvince implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("position_lat")
    @Expose
    private String position_lat;

    public String getPosition_lat() {
        return position_lat;
    }

    public void setPosition_lat(String position_lat) {
        this.position_lat = position_lat;
    }

    public String getPosition_lon() {
        return position_lon;
    }

    public void setPosition_lon(String position_lon) {
        this.position_lon = position_lon;
    }

    @SerializedName("position_lon")
    @Expose

    private String position_lon;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
