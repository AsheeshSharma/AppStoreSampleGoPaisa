package com.animelabs.gopaisasampleproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class Name implements Serializable {
    @SerializedName("label")
    private String label;

    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
