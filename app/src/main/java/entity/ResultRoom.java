
package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultRoom implements Serializable {


    @SerializedName("req_bundle_order")
    @Expose
    private String reqBundleOrder;
    @SerializedName("req_id")
    @Expose
    private String reqId;
    @SerializedName("req_status")
    @Expose
    private String reqStatus;
    @SerializedName("req_bundle_id")
    @Expose
    private String reqBundleId;

    public String getReqBundleOrder() {
        return reqBundleOrder;
    }

    public void setReqBundleOrder(String reqBundleOrder) {
        this.reqBundleOrder = reqBundleOrder;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqBundleId() {
        return reqBundleId;
    }

    public void setReqBundleId(String reqBundleId) {
        this.reqBundleId = reqBundleId;
    }

    @SerializedName("ok_confirm_change")
    @Expose
    private Boolean okConfirmChange;

    public Boolean getOkConfirmChange() {
        return okConfirmChange;
    }

    public void setOkConfirmChange(Boolean okConfirmChange) {
        this.okConfirmChange = okConfirmChange;
    }

    public String getSelectedAddNumbers() {
        return selectedAddNumbers;
    }

    public void setSelectedAddNumbers(String selectedAddNumbers) {
        this.selectedAddNumbers = selectedAddNumbers;
    }

    @SerializedName("selected_AddNumbers")
   @Expose
    private String selectedAddNumbers;

    @SerializedName("selected_foreign")
    @Expose
    private String selectedForeign;

    public String getSelectedForeign() {
        return selectedForeign;
    }

    public void setSelectedForeign(String selectedForeign) {
        this.selectedForeign = selectedForeign;
    }

    @SerializedName("head_name")
    @Expose
    private String headName;

    @SerializedName("head_last_name")

    @Expose
    private String headLastName;

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadLastName() {
        return headLastName;
    }

    public void setHeadLastName(String headLastName) {
        this.headLastName = headLastName;
    }



    @SerializedName("room_title")
    @Expose
    private String roomTitle;

    @SerializedName("Price_add_people")
    @Expose
    private String PriceAddPeople;
    @SerializedName("is_halfIn")
    @Expose
    private Boolean isHalfIn;
    @SerializedName("is_halfOut")
    @Expose
    private Boolean isHalfOut;

    public Boolean getHalfIn() {
        return isHalfIn;
    }

    public void setHalfIn(Boolean halfIn) {
        isHalfIn = halfIn;
    }

    public Boolean getHalfOut() {
        return isHalfOut;
    }

    public void setHalfOut(Boolean halfOut) {
        isHalfOut = halfOut;
    }

    public String getPriceAddPeople() {
        return PriceAddPeople;
    }

    public void setPriceAddPeople(String priceAddPeople) {
        PriceAddPeople = priceAddPeople;
    }

    public String getRoom_price_final() {
        return room_price_final;
    }

    public void setRoom_price_final(String room_price_final) {
        this.room_price_final = room_price_final;
    }

    @SerializedName("room_price_final")
    @Expose

    private String room_price_final;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("room_language")
    @Expose
    private String roomLanguage;
    @SerializedName("room_lodging_id")
    @Expose
    private String roomLodgingId;
    @SerializedName("room_area")
    @Expose
    private String roomArea;
    @SerializedName("room_capacity_adult")
    @Expose
    private String roomCapacityAdult;
    @SerializedName("room_capacity_children")
    @Expose
    private String roomCapacityChildren;
    @SerializedName("room_capacity_extra")
    @Expose
    private String roomCapacityExtra;
    @SerializedName("room_type_id")
    @Expose
    private String roomTypeId;
    @SerializedName("room_type_name")
    @Expose
    private String roomTypeName;
    @SerializedName("room_price")
    @Expose
    private String roomPrice;
    @SerializedName("room_price_promotion")
    @Expose
    private String roomPricePromotion;
    @SerializedName("room_price_foreign")
    @Expose
    private String roomPriceForeign;
    @SerializedName("room_price_ad_people")
    @Expose
    private String roomPriceAdPeople;
    @SerializedName("room_price_difference")
    @Expose
    private String roomPriceDifference;
    @SerializedName("room_price_difference_percent")
    @Expose
    private String roomPriceDifferencePercent;
    @SerializedName("room_price_halfboard_in")
    @Expose
    private String roomPriceHalfboardIn;
    @SerializedName("room_price_halfboard_out")
    @Expose
    private String roomPriceHalfboardOut;
    @SerializedName("room_price_quantity")
    @Expose
    private String roomPriceQuantity;
    @SerializedName("lodging_room_bed")
    @Expose
    private List<LodgingRoomBed> lodgingRoomBed = null;
    @SerializedName("lodging_room_facility")
    @Expose
    private List<LodgingRoomFacility> lodgingRoomFacility = null;
    @SerializedName("lodging_room_availability")
    @Expose
    private List<LodgingRoomAvailability> lodgingRoomAvailability = null;
    @SerializedName("lodging_room_price")
    @Expose
    private String lodgingRoomPrice;

    @SerializedName("room_img_url")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomLanguage() {
        return roomLanguage;
    }

    public void setRoomLanguage(String roomLanguage) {
        this.roomLanguage = roomLanguage;
    }

    public String getRoomLodgingId() {
        return roomLodgingId;
    }

    public void setRoomLodgingId(String roomLodgingId) {
        this.roomLodgingId = roomLodgingId;
    }

    public String getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(String roomArea) {
        this.roomArea = roomArea;
    }

    public String getRoomCapacityAdult() {
        return roomCapacityAdult;
    }

    public void setRoomCapacityAdult(String roomCapacityAdult) {
        this.roomCapacityAdult = roomCapacityAdult;
    }

    public String getRoomCapacityChildren() {
        return roomCapacityChildren;
    }

    public void setRoomCapacityChildren(String roomCapacityChildren) {
        this.roomCapacityChildren = roomCapacityChildren;
    }

    public String getRoomCapacityExtra() {
        return roomCapacityExtra;
    }

    public void setRoomCapacityExtra(String roomCapacityExtra) {
        this.roomCapacityExtra = roomCapacityExtra;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomPricePromotion() {
        return roomPricePromotion;
    }

    public void setRoomPricePromotion(String roomPricePromotion) {
        this.roomPricePromotion = roomPricePromotion;
    }

    public String getRoomPriceForeign() {
        return roomPriceForeign;
    }

    public void setRoomPriceForeign(String roomPriceForeign) {
        this.roomPriceForeign = roomPriceForeign;
    }

    public String getRoomPriceAdPeople() {
        return roomPriceAdPeople;
    }

    public void setRoomPriceAdPeople(String roomPriceAdPeople) {
        this.roomPriceAdPeople = roomPriceAdPeople;
    }

    public String getRoomPriceDifference() {
        return roomPriceDifference;
    }

    public void setRoomPriceDifference(String roomPriceDifference) {
        this.roomPriceDifference = roomPriceDifference;
    }

    public String getRoomPriceDifferencePercent() {
        return roomPriceDifferencePercent;
    }

    public void setRoomPriceDifferencePercent(String roomPriceDifferencePercent) {
        this.roomPriceDifferencePercent = roomPriceDifferencePercent;
    }

    public String getRoomPriceHalfboardIn() {
        return roomPriceHalfboardIn;
    }

    public void setRoomPriceHalfboardIn(String roomPriceHalfboardIn) {
        this.roomPriceHalfboardIn = roomPriceHalfboardIn;
    }

    public String getRoomPriceHalfboardOut() {
        return roomPriceHalfboardOut;
    }

    public void setRoomPriceHalfboardOut(String roomPriceHalfboardOut) {
        this.roomPriceHalfboardOut = roomPriceHalfboardOut;
    }

    public String getRoomPriceQuantity() {
        return roomPriceQuantity;
    }

    public void setRoomPriceQuantity(String roomPriceQuantity) {
        this.roomPriceQuantity = roomPriceQuantity;
    }

    public List<LodgingRoomBed> getLodgingRoomBed() {
        return lodgingRoomBed;
    }

    public void setLodgingRoomBed(List<LodgingRoomBed> lodgingRoomBed) {
        this.lodgingRoomBed = lodgingRoomBed;
    }

    public List<LodgingRoomFacility> getLodgingRoomFacility() {
        return lodgingRoomFacility;
    }

    public void setLodgingRoomFacility(List<LodgingRoomFacility> lodgingRoomFacility) {
        this.lodgingRoomFacility = lodgingRoomFacility;
    }

    public List<LodgingRoomAvailability> getLodgingRoomAvailability() {
        return lodgingRoomAvailability;
    }

    public void setLodgingRoomAvailability(List<LodgingRoomAvailability> lodgingRoomAvailability) {
        this.lodgingRoomAvailability = lodgingRoomAvailability;
    }

    public String getLodgingRoomPrice() {
        return lodgingRoomPrice;
    }

    public void setLodgingRoomPrice(String lodgingRoomPrice) {
        this.lodgingRoomPrice = lodgingRoomPrice;
    }

}
