
package entity;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventInfo implements Serializable{

    @SerializedName("event_title")
    @Expose
    private String eventTitle;
    @SerializedName("event_slogan")
    @Expose
    private String eventSlogan;
    @SerializedName("event_city_id")
    @Expose
    private String eventCityId;
    @SerializedName("event_city_title")
    @Expose
    private String eventCityTitle;
    @SerializedName("event_province_id")
    @Expose
    private String eventProvinceId;
    @SerializedName("event_province_title")
    @Expose
    private String eventProvinceTitle;
    @SerializedName("event_duration_id")
    @Expose
    private String eventDurationId;
    @SerializedName("event_duration_title")
    @Expose
    private String eventDurationTitle;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("event_date_start")
    @Expose
    private String eventDateStart;
    @SerializedName("event_date_end")
    @Expose
    private String eventDateEnd;
    @SerializedName("event_date_duration")
    @Expose
    private String eventDateDuration;
    @SerializedName("event_pos_lat")
    @Expose
    private String eventPosLat;
    @SerializedName("event_pos_lon")
    @Expose
    private String eventPosLon;
    @SerializedName("event_time_start")
    @Expose
    private String eventTimeStart;
    @SerializedName("event_time_end")
    @Expose
    private String eventTimeEnd;
    @SerializedName("event_address")
    @Expose
    private String eventAddress;
    @SerializedName("event_images")
    @Expose
    private List<EventImage> eventImages = null;
    @SerializedName("event_body")
    @Expose
    private String eventBody;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventSlogan() {
        return eventSlogan;
    }

    public void setEventSlogan(String eventSlogan) {
        this.eventSlogan = eventSlogan;
    }

    public String getEventCityId() {
        return eventCityId;
    }

    public void setEventCityId(String eventCityId) {
        this.eventCityId = eventCityId;
    }

    public String getEventCityTitle() {
        return eventCityTitle;
    }

    public void setEventCityTitle(String eventCityTitle) {
        this.eventCityTitle = eventCityTitle;
    }

    public String getEventProvinceId() {
        return eventProvinceId;
    }

    public void setEventProvinceId(String eventProvinceId) {
        this.eventProvinceId = eventProvinceId;
    }

    public String getEventProvinceTitle() {
        return eventProvinceTitle;
    }

    public void setEventProvinceTitle(String eventProvinceTitle) {
        this.eventProvinceTitle = eventProvinceTitle;
    }

    public String getEventDurationId() {
        return eventDurationId;
    }

    public void setEventDurationId(String eventDurationId) {
        this.eventDurationId = eventDurationId;
    }

    public String getEventDurationTitle() {
        return eventDurationTitle;
    }

    public void setEventDurationTitle(String eventDurationTitle) {
        this.eventDurationTitle = eventDurationTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDateStart() {
        return eventDateStart;
    }

    public void setEventDateStart(String eventDateStart) {
        this.eventDateStart = eventDateStart;
    }

    public String getEventDateEnd() {
        return eventDateEnd;
    }

    public void setEventDateEnd(String eventDateEnd) {
        this.eventDateEnd = eventDateEnd;
    }

    public String getEventDateDuration() {
        return eventDateDuration;
    }

    public void setEventDateDuration(String eventDateDuration) {
        this.eventDateDuration = eventDateDuration;
    }

    public String getEventPosLat() {
        return eventPosLat;
    }

    public void setEventPosLat(String eventPosLat) {
        this.eventPosLat = eventPosLat;
    }

    public String getEventPosLon() {
        return eventPosLon;
    }

    public void setEventPosLon(String eventPosLon) {
        this.eventPosLon = eventPosLon;
    }

    public String getEventTimeStart() {
        return eventTimeStart;
    }

    public void setEventTimeStart(String eventTimeStart) {
        this.eventTimeStart = eventTimeStart;
    }

    public String getEventTimeEnd() {
        return eventTimeEnd;
    }

    public void setEventTimeEnd(String eventTimeEnd) {
        this.eventTimeEnd = eventTimeEnd;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public List<EventImage> getEventImages() {
        return eventImages;
    }

    public void setEventImages(List<EventImage> eventImages) {
        this.eventImages = eventImages;
    }

    public String getEventBody() {
        return eventBody;
    }

    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

}
