
package com.eltamiuzcom.kafu.Model.Fawteer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("labanatype")
    @Expose
    private String labanatype;
    @SerializedName("ownername")
    @Expose
    private String ownername;
    @SerializedName("delivernamename")
    @Expose
    private String delivernamename;
    @SerializedName("itemprice")
    @Expose
    private String itemprice;
    @SerializedName("deliverprice")
    @Expose
    private String deliverprice;
    @SerializedName("totalprice")
    @Expose
    private Integer totalprice;
    @SerializedName("date")
    @Expose
    private String date;

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

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getDelivernamename() {
        return delivernamename;
    }

    public void setDelivernamename(String delivernamename) {
        this.delivernamename = delivernamename;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getDeliverprice() {
        return deliverprice;
    }

    public void setDeliverprice(String deliverprice) {
        this.deliverprice = deliverprice;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
