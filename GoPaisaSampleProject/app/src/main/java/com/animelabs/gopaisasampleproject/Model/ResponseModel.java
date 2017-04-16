package com.animelabs.gopaisasampleproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class ResponseModel implements Serializable {
    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    @SerializedName("entry")
    private Entry entry;

}
