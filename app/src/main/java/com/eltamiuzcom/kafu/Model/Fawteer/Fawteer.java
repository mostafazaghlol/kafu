
package com.eltamiuzcom.kafu.Model.Fawteer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fawteer {

    @SerializedName("message")
    @Expose
    private Boolean message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
