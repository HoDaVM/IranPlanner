
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultSouvenirList implements Serializable {

    @SerializedName("Result_souvenir")
    @Expose
    private ResultSouvenir resultSouvenir;

    public ResultSouvenir getResultSouvenir() {
        return resultSouvenir;
    }

    public void setResultSouvenir(ResultSouvenir resultSouvenir) {
        this.resultSouvenir = resultSouvenir;
    }

    public ResultSouvenirList withResultSouvenir(ResultSouvenir resultSouvenir) {
        this.resultSouvenir = resultSouvenir;
        return this;
    }

}
