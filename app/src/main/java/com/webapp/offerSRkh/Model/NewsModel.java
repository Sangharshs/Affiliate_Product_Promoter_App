package com.webapp.offerSRkh.Model;

import java.util.Comparator;

public class NewsModel{
    String time;
    String date;
    String news_heading;
    String newsImg;
    String newsContent;
    String cat_Id;
    String id;
    String url;
    String views;

    public String getViews(String views) {
        return this.views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    String btn_name;
    String btn_url;

    public String getBtn_name() {
        return btn_name;
    }

    public void setBtn_name(String btn_name) {
        this.btn_name = btn_name;
    }

    public String getBtn_url() {
        return btn_url;
    }

    public void setBtn_url(String btn_url) {
        this.btn_url = btn_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebNews() {
        return webNews;
    }

    public NewsModel(String url) {
        this.url = url;
    }

    String webNews;




    public void setWebNews(String webNews) {
        this.webNews = webNews;
    }



    public NewsModel() {

    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNews_heading() {
        return news_heading;
    }

    public void setNews_heading(String news_heading) {
        this.news_heading = news_heading;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getCat_Id() {
        return cat_Id;
    }

    public String setCat_Id(String cat_Id) {
        this.cat_Id = cat_Id;
        return cat_Id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Comparator<NewsModel> getMyName() {
        return myName;
    }

    public static void setMyName(Comparator<NewsModel> myName) {
        NewsModel.myName = myName;
    }

    public NewsModel(String time, String date, String news_heading, String newsImg, String newsContent, String cat_Id, String id,String views) {
        this.time = time;
        this.date = date;
        this.news_heading = news_heading;
        this.newsImg = newsImg;
        this.newsContent = newsContent;
        this.cat_Id = cat_Id;
        this.id = id;
        this.views = views;
    }

    public static Comparator<NewsModel> myName = new Comparator<NewsModel>() {
        @Override
        public int compare(NewsModel t1, NewsModel t2) {

            return t2.getId().compareTo(t1.getId());
        }


    };
}
