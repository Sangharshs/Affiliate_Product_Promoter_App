package com.webapp.offerSRkh;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.webapp.offerSRkh.Model.Ads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Apiconfig {

  public static final String VIEW_COUNT          =      "https://offersrkh.com/offerSRkh-admin/viewcount.php?id=";
  public static final String GET_FEATURED        =      "https://offersrkh.com/offerSRkh-admin/getfeatured.php";
  public static final String IMAGE_URL           =      "https://offersrkh.com/offerSRkh-admin/img/";
  public static final String ERROR_REPORT        =      "https://offersrkh.com/offerSRkh-admin/error_report.php";
  public static final String GET_SLIDER_IMAGES   =      "https://offersrkh.com/offerSRkh-admin/getslider.php";
  public static final String GET_NEWS_URL        =      "https://offersrkh.com/offerSRkh-admin/getnews.php";
  public static final String GET_NEWS_CATEGORIES =      "https://offersrkh.com/offerSRkh-admin/getnewscategories.php";
  public static final String GET_POPULER         =      "https://offersrkh.com/offerSRkh-admin/getpopuler.php";
  public static final String CONTACT_US          =      "https://offersrkh.com/offerSRkh-admin/contact_us.php";
  public static final String GET_ADS_SETTINGS    =      "https://offersrkh.com/offerSRkh-admin/get_adsetting.php";

  //load more for next channel list
  public static final int LOAD_MORE = 15;
  public static String BANNER_AD = "";


}
