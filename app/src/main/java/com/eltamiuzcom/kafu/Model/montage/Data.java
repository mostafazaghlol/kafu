
package com.eltamiuzcom.kafu.Model.montage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("item_desc")
    @Expose
    private String itemDesc;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("labanatype")
    @Expose
    private String labanatype;
    @SerializedName("suspensed")
    @Expose
    private Integer suspensed;
    @SerializedName("humantime")
    @Expose
    private String humantime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLabanatype() {
        return labanatype;
    }

    public void setLabanatype(String labanatype) {
        this.labanatype = labanatype;
    }

    public Integer getSuspensed() {
        return suspensed;
    }

    public void setSuspensed(Integer suspensed) {
        this.suspensed = suspensed;
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

}
