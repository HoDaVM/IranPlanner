
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RateParam implements Serializable{

    @SerializedName("param1")
    @Expose
    private String param1;

    public RateParam(String param1, String param2, String param3, String param4) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
    }

    @SerializedName("param2")

    @Expose
    private String param2;
    @SerializedName("param3")
    @Expose
    private String param3;
    @SerializedName("param4")
    @Expose
    private String param4;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

}
