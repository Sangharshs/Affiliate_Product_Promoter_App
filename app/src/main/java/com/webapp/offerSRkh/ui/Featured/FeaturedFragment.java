package com.webapp.offerSRkh.ui.Featured;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.api.Distribution;
import com.webapp.offerSRkh.Adapter.FeaturedAdapter;
import com.webapp.offerSRkh.Model.NewsModel;
import com.webapp.offerSRkh.R;
import com.webapp.offerSRkh.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.GET_FEATURED;
import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_URL;

public class FeaturedFragment extends Fragment {
    RecyclerView recyclerView;
    FeaturedAdapter adapter_home;
    List<NewsModel> loaded_news_list;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;
    ShimmerFrameLayout container1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_featured, container, false);
        recyclerView = root.findViewById(R.id.news_list);
        swipeRefreshLayout = root.findViewById(R.id.swipe_loaded_news);
        container1 = root.findViewById(R.id.shimmer_view_container);
        container1.startShimmer();
        container1.setAlpha((float) 0.4);
        recyclerView.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(getContext());
        linearLayoutManager0.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager0);

        loaded_news_list = new ArrayList<>();
        setHasOptionsMenu(true);

        FeaturedFragment.loadAppData loadAppData = new loadAppData();
        loadAppData.execute(GET_NEWS_URL);

        adapter_home = new FeaturedAdapter(getContext(), loaded_news_list);
        recyclerView.setAdapter(adapter_home);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().detach(FeaturedFragment.this).attach(FeaturedFragment.this).commit();
            }
        });

        return root;
    }
    public class loadAppData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            container1.startShimmer();
            container1.setAlpha((float) 0.4);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            isCancelled();
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            isCancelled();
        }

        @Override
        protected String doInBackground(String... strings) {

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,  GET_FEATURED, null, new Response.Listener<JSONArray>() {
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
                    adapter_home = new FeaturedAdapter(getContext(), loaded_news_list);
                    recyclerView.setAdapter(adapter_home);
                    container1.stopShimmer();
                    container1.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
//                    swipeRefreshLayout.setRefreshing(false);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swipeRefreshLayout.setRefreshing(false);
                    container1.stopShimmer();
                    container1.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);                }
            });

            RequestQueue queue = Volley.newRequestQueue(requireContext());
            queue.add(request);
            return "Process Finished";
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}