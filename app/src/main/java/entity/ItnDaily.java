
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItnDaily implements Serializable{

    @SerializedName("day_number")
    @Expose
    private String dayNumber;
    @SerializedName("day_description")
    @Expose
    private String dayDescription;
    @SerializedName("day_position")
    @Expose
    private List<DayPosition> dayPosition = null;

    public String getDayNumber(String dayCount) {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public ItnDaily withDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
        return this;
    }

    public String getDayDescription() {
        return dayDescription;
    }

    public void setDayDescription(String dayDescription) {
        this.dayDescription = dayDescription;
    }

    public ItnDaily withDayDescription(String dayDescription) {
        this.dayDescription = dayDescription;
        return this;
    }

    public List<DayPosition> getDayPosition() {
        return dayPosition;
    }

    public void setDayPosition(List<DayPosition> dayPosition) {
        this.dayPosition = dayPosition;
    }

    public ItnDaily withDayPosition(List<DayPosition> dayPosition) {
        this.dayPosition = dayPosition;
        return this;
    }

}
