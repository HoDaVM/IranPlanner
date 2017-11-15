package entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by h.vahidimehr on 12/11/2017.
 */

public class PandaMapList implements Serializable {


    @SerializedName("PandaMap")
    @Expose

    private List<LatLng> pandaMap = null;

    public PandaMapList(List<LatLng> pandaMap) {
        this.pandaMap = pandaMap;
    }

    public List<LatLng> getPandaMap() {
        return pandaMap;

    }

    public void setPandaMap(List<LatLng> pandaMap) {
        this.pandaMap = pandaMap;
    }
}
