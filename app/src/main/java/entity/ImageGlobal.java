
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageGlobal implements Serializable {

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("list")
    @Expose
    private String list;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public ImageGlobal withMain(String main) {
        this.main = main;
        return this;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public ImageGlobal withList(String list) {
        this.list = list;
        return this;
    }

}
