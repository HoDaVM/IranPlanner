
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendParamUser implements Serializable {

    public SendParamUser(String uid, String cid, String ntype, String nid, RateParam rateParam) {
        this.uid = uid;
        this.cid = cid;
        this.ntype = ntype;
        this.nid = nid;
        this.rateParam = rateParam;

           }

    public SendParamUser(String uid, String cid, String ntype, String nid) {
        this.uid = uid;
        this.cid = cid;
        this.ntype = ntype;
        this.nid = nid;
    }

    @SerializedName("uid")
    @Expose

    private String uid;
    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("ntype")
    @Expose
    private String ntype;
    @SerializedName("nid")
    @Expose
    private String nid;

    @SerializedName("rate_param")
    @Expose
    private RateParam rateParam;

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

    public RateParam getRateParam() {
        return rateParam;
    }

    public void setRateParam(RateParam rateParam) {
        this.rateParam = rateParam;
    }

}
