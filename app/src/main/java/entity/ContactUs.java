
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContactUs implements Serializable {

    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("Result_contact")
    @Expose
    private ResultContact resultContact;
    @SerializedName("Statistics")
    @Expose
    private Statistics statistics;
    @SerializedName("ParsDID")
    @Expose
    private ParsDID parsDID;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ContactUs withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ResultContact getResultContact() {
        return resultContact;
    }

    public void setResultContact(ResultContact resultContact) {
        this.resultContact = resultContact;
    }

    public ContactUs withResultContact(ResultContact resultContact) {
        this.resultContact = resultContact;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ContactUs withStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public ParsDID getParsDID() {
        return parsDID;
    }

    public void setParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
    }

    public ContactUs withParsDID(ParsDID parsDID) {
        this.parsDID = parsDID;
        return this;
    }

}
