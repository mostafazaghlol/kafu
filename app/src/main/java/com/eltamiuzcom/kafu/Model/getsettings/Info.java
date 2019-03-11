
package com.eltamiuzcom.kafu.Model.getsettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("policy")
    @Expose
    private String policy;
    @SerializedName("aboutapp")
    @Expose
    private Object aboutapp;
    @SerializedName("logo")
    @Expose
    private String logo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public Object getAboutapp() {
        return aboutapp;
    }

    public void setAboutapp(Object aboutapp) {
        this.aboutapp = aboutapp;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
