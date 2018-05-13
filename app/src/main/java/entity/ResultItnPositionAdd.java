
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultItnPositionAdd implements Serializable {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("itn_id")
    @Expose
    private String itnId;
    @SerializedName("ntype")
    @Expose
    private String ntype;
    @SerializedName("nid")
    @Expose
    private String nid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ResultItnPositionAdd withUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getItnId() {
        return itnId;
    }

    public void setItnId(String itnId) {
        this.itnId = itnId;
    }

    public ResultItnPositionAdd withItnId(String itnId) {
        this.itnId = itnId;
        return this;
    }

    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
    }

    public ResultItnPositionAdd withNtype(String ntype) {
        this.ntype = ntype;
        return this;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public ResultItnPositionAdd withNid(String nid) {
        this.nid = nid;
        return this;
    }

}
