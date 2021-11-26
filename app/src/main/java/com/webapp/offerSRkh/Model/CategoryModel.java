package com.webapp.offerSRkh.Model;

public class CategoryModel {
    String cname;
    String image;

    public CategoryModel() {

    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategoryModel(String cname, String image) {
        this.cname = cname;
        this.image = image;
    }

}
