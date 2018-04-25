
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultContact implements Serializable {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Response Time")
    @Expose
    private String responseTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResultContact withCode(String code) {
        this.code = code;
        return this;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public ResultContact withResponseTime(String responseTime) {
        this.responseTime = responseTime;
        return this;
    }

}
