package com.animelabs.gopaisasampleproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class Attributes  implements Serializable {
    @SerializedName("attributes")
    private String height;
    public Attributes(String height){
        this.height = height;
    }
    public Attributes(){

    }
    public void setheight(String height) {
        this.height = height;
    }
    public String getTotalPages() {
        return height;
    }
}
