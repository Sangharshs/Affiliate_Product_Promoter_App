package com.webapp.offerSRkh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.webapp.offerSRkh.Adapter.CategoryAdapter;
import com.webapp.offerSRkh.Adapter.NewsAdapter;
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

public class CategorywiseNewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NewsModel> list;
    List<Ads> adsList = new ArrayList<>();
    TextView textView;
    ProgressBar progressBar;
    AdView mAdView;
    InterstitialAd interstitialAd;
    LinearLayoutManager linearLayoutManager;
    ShimmerFrameLayout container1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorywise_news);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });




        recyclerView=findViewById(R.id.recyclerView);
        textView = findViewById(R.id.categoryName);
        progressBar = findViewById(R.id.pbr);
        Intent intent = getIntent();
        String C_HEAD = intent.getStringExtra("C_NAME");
        textView.setText(C_HEAD);

        container1 = findViewById(R.id.shimmer_view_container);
        container1.startShimmer();
        container1.setAlpha((float) 0.4);

        recyclerView.setVisibility(View.GONE);

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_ADS_SETTINGS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        Ads ads = new Ads(object.getString("banner"),object.getString("interstitial"));
                        View view = findViewById(R.id.adView);

                        mAdView = new AdView(getApplicationContext());

                        ((RelativeLayout) view).addView(mAdView);

                        mAdView.setAdSize(AdSize.BANNER);

                        mAdView.setAdUnitId(ads.getBannerAd());

                        AdRequest adRequest1 = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest1);


                        MobileAds.initialize(getApplicationContext(),
                                "ca-app-pub-3940256099942544~3347511713");

                        interstitialAd = new InterstitialAd(getApplicationContext());
                        interstitialAd.setAdUnitId(ads.getInterstitial_Ad());
                        interstitialAd.loadAd(new AdRequest.Builder().build());

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

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();

        loadNews();

        NewsAdapter adapter_home = new NewsAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(adapter_home);

    }

    private void loadNews() {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,GET_NEWS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        NewsModel newsModel = new NewsModel();
                        newsModel.setNewsImg(object.getString("image"));
                        newsModel.setNews_heading(object.getString("heading"));
                        newsModel.setNewsContent(object.getString("content"));

                        newsModel.setDate(object.getString("date"));
                        newsModel.setTime(object.getString("time"));
                        String cat = newsModel.setCat_Id(object.getString("cat_id"));
                        newsModel.setBtn_name(object.getString("btn_name"));
                        newsModel.setBtn_url(object.getString("btn_url"));
                        Intent matchCategory = getIntent();

                        if (matchCategory.getStringExtra("C_NAME").equals(cat)){
                            list.add(newsModel);

                        }                        // Toast.makeText(getContext(),"Success"+response,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(list.isEmpty()){
                    Toast.makeText(CategorywiseNewsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }

                Collections.reverse(list);
                NewsAdapter adapter_home = new NewsAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(adapter_home);
                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public void onBackPressed() {

        if(interstitialAd.isLoaded()   ){
            interstitialAd.show();
        }else if(interstitialAd == null){
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }
}
