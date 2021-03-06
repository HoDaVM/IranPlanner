
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultUpdate implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_user_update")
    @Expose
    private ResultUserInfo resultUserUpdate;
    @SerializedName("ParsDID")
    @Expose
    private ParsDID parsDID;

    public ResultUserInfo getResultUserUpdate() {
        return resultUserUpdate;
    }

    public void setResultUserUpdate(ResultUserInfo resultUserUpdate) {
        this.resultUserUpdate = resultUserUpdate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

}
