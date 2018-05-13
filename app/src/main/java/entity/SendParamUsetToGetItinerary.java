
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendParamUsetToGetItinerary implements Serializable {
    public SendParamUsetToGetItinerary(String uid, String cid, String ntype, String nid) {
        this.uid = uid;
        this.cid = cid;
        this.ntype = ntype;
        this.nid = nid;
    }

    @SerializedName("uid")
    @Expose
    private String uid;

    public String getItn_id() {
        return itn_id;
    }

    public void setItn_id(String itn_id) {

        this.itn_id = itn_id;
    }

    public SendParamUsetToGetItinerary(String uid, String cid, String ntype, String nid, String itn_id) {
        this.uid = uid;
        this.cid = cid;
        this.ntype = ntype;
        this.nid = nid;
        this.itn_id = itn_id;
    }


    @SerializedName("cid")

    @Expose

    private String cid;
    @SerializedName("ntype")
    @Expose
    private String ntype;
    @SerializedName("nid")
    @Expose
    private String nid;
    @SerializedName("itn_id")
    @Expose
    private String itn_id;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }


}
