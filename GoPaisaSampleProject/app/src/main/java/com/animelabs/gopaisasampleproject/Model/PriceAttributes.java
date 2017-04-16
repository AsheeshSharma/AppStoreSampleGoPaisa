package com.animelabs.gopaisasampleproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class PriceAttributes  implements Serializable {
    @SerializedName("amount")
    private String amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @SerializedName("currency")
    private String currency;
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() {
        return amount;
    }

    public PriceAttributes(String amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    public PriceAttributes(){

    }
}
