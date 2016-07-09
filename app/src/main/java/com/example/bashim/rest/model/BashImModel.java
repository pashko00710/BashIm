package com.example.bashim.rest.model;

import com.google.gson.annotations.SerializedName;

public class BashImModel {
    @SerializedName("link")
    private String link;
    @SerializedName("elementPureHtml")
    private String elementPureHtml;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getElementPureHtml() {
        return elementPureHtml;
    }

    public void setElementPureHtml(String elementPureHtml) {
        this.elementPureHtml = elementPureHtml;
    }
}
