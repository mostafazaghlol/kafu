
package com.eltamiuzcom.kafu.Model.montage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Montage {

    @SerializedName("message")
    @Expose
    private Boolean message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
