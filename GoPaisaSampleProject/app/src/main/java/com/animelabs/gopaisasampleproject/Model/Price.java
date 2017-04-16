package com.animelabs.gopaisasampleproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class Price  implements Serializable {
    @SerializedName("label")
    private String label;

    public PriceAttributes getPriceAttributes() {
        return priceAttributes;
    }

    public void setPriceAttributes(PriceAttributes priceAttributes) {
        this.priceAttributes = priceAttributes;
    }

    @SerializedName("attributes")
    private PriceAttributes priceAttributes;
    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
