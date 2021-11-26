package com.webapp.offerSRkh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.webapp.offerSRkh.Adapter.FeaturedAdapter;
import com.webapp.offerSRkh.Model.Ads;
import com.webapp.offerSRkh.Model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.GET_ADS_SETTINGS;
import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_URL;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FeaturedAdapter adapter_home;
    List<NewsModel> loaded_news_list;
    List<Ads> adsList = new ArrayList<>();
    SearchView searchView;
    AdView mAdView,mAdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.news_list);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_ADS_SETTINGS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        Ads ads = new Ads(object.getString("banner"),object.getString("interstitial"));
                        View view = findViewById(R.id.adView1);

                        mAdView = new AdView(getApplicationContext());

                        ((RelativeLayout) view).addView(mAdView);

                        mAdView.setAdSize(AdSize.BANNER);

                        mAdView.setAdUnitId(ads.getBannerAd());

                        AdRequest adRequest1 = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest1);

                        MobileAds.initialize(getApplicationContext(),
                                "ca-app-pub-3940256099942544~3347511713");



                      //  Toast.makeText(SearchActivity.this, ads.getBannerAd(), Toast.LENGTH_SHORT).show();
                        adsList.add(ads);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        searchView = findViewById(R.id.search_View);

        LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager0.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager0);

        loaded_news_list = new ArrayList<>();


        adapter_home = new FeaturedAdapter(getApplicationContext(), loaded_news_list);
        recyclerView.setAdapter(adapter_home);


        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, GET_NEWS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        NewsModel newsModel = new NewsModel();
                        // id = object.getString("id");
                        // id = newsModel.setId(object.getString("id"));
                        newsModel.setNewsImg(object.getString("image"));
                        newsModel.setNews_heading(object.getString("heading"));

                        newsModel.setNewsContent(object.getString("content"));

                        newsModel.setUrl(object.getString("url"));
                        newsModel.setDate(object.getString("date"));
                        newsModel.setTime(object.getString("time"));
                        newsModel.setBtn_name(object.getString("btn_name"));
                        newsModel.setBtn_url(object.getString("btn_url"));
                        // ,Toast.LENGTH_SHORT).show();
                        loaded_news_list.add(newsModel);
                        // Collections.sort(list);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.reverse(loaded_news_list);

                // Collections.sort(list, NewsModel.myName);
                adapter_home = new FeaturedAdapter(getApplicationContext(), loaded_news_list);
                recyclerView.setAdapter(adapter_home);
               // swipeRefreshLayout.setRefreshing(false);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //swipeRefreshLayout.setRefreshing(false);
                //   noInte.setVisibility(View.VISIBLE);
                //  wrong.setVisibility(View.VISIBLE);
                //  Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        queue1.add(request1);
        se();
    }

    private void se() {
        searchView.setQueryHint("Search Offers Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter_home.getFilter().filter(s);
                return false;
            }
        });
    }
}