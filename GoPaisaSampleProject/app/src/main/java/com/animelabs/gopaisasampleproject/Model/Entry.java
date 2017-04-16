package com.animelabs.gopaisasampleproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class Entry  implements Serializable {
    @SerializedName("im:name")
    private Name name;
    @SerializedName("summary")
    private Summary summary;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    private String appId;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @SerializedName("im:price")
    private Price price;
    @SerializedName("im:image")
    private Image image;

    @Override
    public String toString() {
        return image.getLabel() + " " + summary.getLabel() + " " + price.getPriceAttributes().getAmount() + " " + name.getLabel() ;
    }

    public Entry(Image image, Summary summary, Name name, Price price, String appId){
        this.price = price;
        this.summary = summary;
        this.name = name;
        this.image = image;
        this.appId = appId;
    }


    public Entry(String imageURL, String summaryText, String nameText, String priceAmount, String currency, String appId){
        this.image = new Image();
        this.image.setPn(new Attributes("100"));
        this.image.setLabel(imageURL);
        this.name = new Name();
        this.name.setLabel(nameText);
        this.price = new Price();
        this.price.setPriceAttributes(new PriceAttributes(priceAmount, currency));
        this.price.setLabel("PRICE");
        this.summary = new Summary();
        this.summary.setLabel(summaryText);
        this.appId = appId;
    }

}
