
package com.eltamiuzcom.kafu.Model.getsettings;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("info")
    @Expose
    private List<Info> info = null;
    @SerializedName("cats")
    @Expose
    private List<Cat> cats = null;
    @SerializedName("itemtypes")
    @Expose
    private List<Itemtype> itemtypes = null;
    @SerializedName("labanatypes")
    @Expose
    private List<Labanatype> labanatypes = null;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public List<Itemtype> getItemtypes() {
        return itemtypes;
    }

    public void setItemtypes(List<Itemtype> itemtypes) {
        this.itemtypes = itemtypes;
    }

    public List<Labanatype> getLabanatypes() {
        return labanatypes;
    }

    public void setLabanatypes(List<Labanatype> labanatypes) {
        this.labanatypes = labanatypes;
    }

}
