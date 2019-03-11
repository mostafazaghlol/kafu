
package com.eltamiuzcom.kafu.Model.myacutions;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("item_desc")
    @Expose
    private String itemDesc;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("itemtype")
    @Expose
    private String itemtype;
    @SerializedName("itemlang")
    @Expose
    private String itemlang;
    @SerializedName("itemweight")
    @Expose
    private String itemweight;
    @SerializedName("labanatype")
    @Expose
    private String labanatype;
    @SerializedName("humantime")
    @Expose
    private String humantime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("bidders")
    @Expose
    private List<Bidder> bidders = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getItemlang() {
        return itemlang;
    }

    public void setItemlang(String itemlang) {
        this.itemlang = itemlang;
    }

    public String getItemweight() {
        return itemweight;
    }

    public void setItemweight(String itemweight) {
        this.itemweight = itemweight;
    }

    public String getLabanatype() {
        return labanatype;
    }

    public void setLabanatype(String labanatype) {
        this.labanatype = labanatype;
    }

    public String getHumantime() {
        return humantime;
    }

    public void setHumantime(String humantime) {
        this.humantime = humantime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Bidder> getBidders() {
        return bidders;
    }

    public void setBidders(List<Bidder> bidders) {
        this.bidders = bidders;
    }

}
