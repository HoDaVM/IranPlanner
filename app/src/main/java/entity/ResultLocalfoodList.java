
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultLocalfoodList implements Serializable{

    @SerializedName("Result_localfood")
    @Expose
    private ResultLocalfood resultLocalfood;

    public ResultLocalfood getResultLocalfood() {
        return resultLocalfood;
    }

    public void setResultLocalfood(ResultLocalfood resultLocalfood) {
        this.resultLocalfood = resultLocalfood;
    }

    public ResultLocalfoodList withResultLocalfood(ResultLocalfood resultLocalfood) {
        this.resultLocalfood = resultLocalfood;
        return this;
    }

}
