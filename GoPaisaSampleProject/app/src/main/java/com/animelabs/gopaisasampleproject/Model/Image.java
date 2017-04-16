package com.animelabs.gopaisasampleproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class Image  implements Serializable {

    public String getLabel() {
        return label;
    }

    @SerializedName("label")
    private String label;

    public Attributes getPn() {
        return pn;
    }

    public void setPn(Attributes pn) {
        this.pn = pn;
    }

    @SerializedName("attributes")
    private Attributes pn;
    public void setLabel(String label) {
        this.label = label;
    }
}
