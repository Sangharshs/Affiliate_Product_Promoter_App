package com.webapp.offerSRkh.Model;

public class CategorywiseModel {

    String categoryName, imagePath;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public CategorywiseModel(String categoryName, String imagePath) {
        this.categoryName = categoryName;
        this.imagePath = imagePath;
    }
}
