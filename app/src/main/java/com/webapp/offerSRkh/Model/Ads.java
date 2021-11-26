package com.webapp.offerSRkh.Model;

public class Ads {
    String bannerAd, interstitial_Ad;

    public Ads(String bannerAd, String interstitial_Ad) {
        this.bannerAd = bannerAd;
        this.interstitial_Ad = interstitial_Ad;
    }


    public String getBannerAd() {
        return bannerAd;
    }

    public void setBannerAd(String bannerAd) {
        this.bannerAd = bannerAd;
    }

    public String getInterstitial_Ad() {
        return interstitial_Ad;
    }

    public void setInterstitial_Ad(String interstitial_Ad) {
        this.interstitial_Ad = interstitial_Ad;
    }


}
