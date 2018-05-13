
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItnPosition implements Serializable {

    @SerializedName("nid")
    @Expose
    private String nid;
    @SerializedName("ntype")
    @Expose
    private String ntype;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("day")
    @Expose
    private String day;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public ItnPosition withNid(String nid) {
        this.nid = nid;
        return this;
    }

    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
    }

    public ItnPosition withNtype(String ntype) {
        this.ntype = ntype;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public ItnPosition withOrder(String order) {
        this.order = order;
        return this;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ItnPosition withDay(String day) {
        this.day = day;
        return this;
    }

}
