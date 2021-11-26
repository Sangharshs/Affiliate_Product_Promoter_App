package com.webapp.offerSRkh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.webapp.offerSRkh.Adapter.NewsAdapter;
import com.webapp.offerSRkh.Model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_URL;

public class ViewAllOffersActivity extends AppCompatActivity {

    List<NewsModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_offers);
        recyclerView = findViewById(R.id.view_all_offers_recyclerview);
        swipeRefreshLayout = findViewById(R.id.swipe_offers);
        swipeRefreshLayout.setRefreshing(true);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

        adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId(getString(R.string.banner));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager2);

        loadNews();
        NewsAdapter adapter_home = new NewsAdapter(this, list);
        recyclerView.setAdapter(adapter_home);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadNews() {
        swipeRefreshLayout.setRefreshing(true);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_NEWS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject object = response.getJSONObject(i);
                        NewsModel newsModel = new NewsModel();

                        //id = object.getString("id");
                        //Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                        newsModel.setId(object.getString("id"));
                        newsModel.setNewsImg(object.getString("image"));
                        newsModel.setNews_heading(object.getString("heading"));

                        newsModel.setNewsContent(object.getString("content"));
                        newsModel.setUrl(object.getString("url"));
                        newsModel.setDate(object.getString("date"));
                        newsModel.setTime(object.getString("time"));
                        newsModel.setBtn_name(object.getString("btn_name"));
                        newsModel.setBtn_url(object.getString("btn_url"));
                        // ,Toast.LENGTH_SHORT).show();
                        list.add(newsModel);
                        // Collections.sort(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.reverse(list);
                swipeRefreshLayout.setRefreshing(false);
                // Collections.sort(list, NewsModel.myName);
                NewsAdapter adapter_home = new NewsAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter_home);
                // swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}