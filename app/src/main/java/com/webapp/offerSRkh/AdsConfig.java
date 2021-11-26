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


import static com.webapp.offerSRkh.Apiconfig.GET_ADS_SETTINGS;

public class AdsConfig {

    public static String BA = "";
    public static String IA = "";
    Context context;
    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_ADS_SETTINGS, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
//                    Ads adsmodel = new Ads();
//                     object.getString("banner");
//                     object.getString("interstitial");
//                    adsmodel.setBannerAd(object.getString("banner"));
//                    adsmodel.setInterstitial_Ad(object.getString("interstitial"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(context.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    });

}
