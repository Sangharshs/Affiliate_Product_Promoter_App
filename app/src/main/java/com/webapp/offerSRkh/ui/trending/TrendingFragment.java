package com.webapp.offerSRkh.ui.trending;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.webapp.offerSRkh.Adapter.NewsAdapter;
import com.webapp.offerSRkh.Adapter.NewshomeAdapter;
import com.webapp.offerSRkh.CategorywiseNewsActivity;
import com.webapp.offerSRkh.Model.NewsModel;
import com.webapp.offerSRkh.R;
import com.webapp.offerSRkh.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.facebook.FacebookSdk.getApplicationContext;
import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_URL;
import static com.webapp.offerSRkh.Apiconfig.GET_POPULER;

public class TrendingFragment extends Fragment{

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsAdapter adapter_home;
    View root;
    SearchView searchView;
    ShimmerFrameLayout   container1;
    NewsModel newsModel;
    LinearLayoutManager linearLayoutManager;

    private static final String LIST_STATE = "list_state";
    private Parcelable savedRecyclerState;
    private static final String BUNDLE_RECYCLER_LAYOUT ="recycler_layout";
    private ArrayList<NewsModel> offerInstance = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
              //loaded_news_list = new ArrayList<>();
//            if(savedInstanceState != null){
//                offerInstance = savedInstanceState.getParcelable(LIST_STATE);
//                savedRecyclerState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
//                loadData();
//            }else{
                root = inflater.inflate(R.layout.fragment_trending, container, false);
                recyclerView = root.findViewById(R.id.news_list);
                recyclerView.setVisibility(View.VISIBLE);
                container1 = root.findViewById(R.id.shimmer_view_container);
                container1.setAlpha((float) 0.4);
                linearLayoutManager = new LinearLayoutManager(root.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);

                swipeRefreshLayout = root.findViewById(R.id.swipe_loaded_news);
                searchView = root.findViewById(R.id.search_View);

                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getFragmentManager().beginTransaction().detach(TrendingFragment.this).attach(TrendingFragment.this).commit();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


            return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(offerInstance.isEmpty()){
            loadData();
        }

    }

    private void loadData() {
        container1.startShimmer();
        recyclerView.setVisibility(View.GONE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,GET_POPULER, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        newsModel = new NewsModel();
                        newsModel.setNewsImg(object.getString("image"));
                        newsModel.setNews_heading(object.getString("heading"));
                        newsModel.setNewsContent(object.getString("content"));

                        newsModel.setDate(object.getString("date"));
                        newsModel.setTime(object.getString("time"));
                        String cat = newsModel.setCat_Id(object.getString("cat_id"));
                        newsModel.setBtn_name(object.getString("btn_name"));
                        newsModel.setBtn_url(object.getString("btn_url"));
                        offerInstance.add(newsModel);
                        //  progressBar.setVisibility(View.GONE);// Toast.makeText(getContext(),"Success"+response,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                SharedPreferences preferences = root.getContext().getSharedPreferences("offersrkh",root.getContext().MODE_PRIVATE);
//                SharedPreferences.Editor putlist = preferences.edit();
//                putlist.putString("list", String.valueOf(offerInstance));
//                putlist.commit();
                adapter_home = new NewsAdapter(root.getContext(),offerInstance);
                recyclerView.setAdapter(adapter_home);

                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(root.getContext());
        queue.add(request);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(LIST_STATE, (Parcelable) offerInstance);
//        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT,recyclerView.getLayoutManager().onSaveInstanceState());
//    }
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        if (offerInstance != null && savedRecyclerState !=null) {
//            offerInstance = savedInstanceState.getParcelable(LIST_STATE);
//            savedRecyclerState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
//        }
//        super.onViewStateRestored(savedInstanceState);
//    }
//    private void restoreLayoutManagerPosition(){
//        if(savedRecyclerState != null){
//            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerState);
//        }
//    }
}