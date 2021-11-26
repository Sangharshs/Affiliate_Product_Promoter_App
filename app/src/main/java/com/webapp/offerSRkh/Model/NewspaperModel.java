package com.webapp.offerSRkh.Model;

public class NewspaperModel {
    String newspaper_name, newspaper_logo;

    public NewspaperModel() {

    }

    public String getNewspaper_name() {
        return newspaper_name;
    }

    public void setNewspaper_name(String newspaper_name) {
        this.newspaper_name = newspaper_name;
    }

    public String getNewspaper_logo() {
        return newspaper_logo;
    }

    public void setNewspaper_logo(String newspaper_logo) {
        this.newspaper_logo = newspaper_logo;
    }

    public NewspaperModel(String newspaper_name, String newspaper_logo) {
        this.newspaper_name = newspaper_name;
        this.newspaper_logo = newspaper_logo;
    }
}
