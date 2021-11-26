package com.webapp.offerSRkh.ui.Categries;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.google.gson.Gson;
import com.webapp.offerSRkh.Adapter.CategoryAdapter;
import com.webapp.offerSRkh.Model.CategoryModel;
import com.webapp.offerSRkh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_CATEGORIES;

public class CategoryFragment extends Fragment {


RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CategoryAdapter adapter;
    List<CategoryModel> list;
    ShimmerFrameLayout container1;

    //View noInte;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);


        recyclerView = root.findViewById(R.id.recyclerview);
        swipeRefreshLayout = root.findViewById(R.id.categorySwipe);
        container1 = root.findViewById(R.id.shimmer_view_container);
        container1.startShimmer();
        container1.setAlpha((float) 0.4);
        recyclerView.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();

        loadData();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = new ArrayList<>();
                loadData();

                //swipeRefreshLayout.setRefreshing(false);
                adapter = new CategoryAdapter(list,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                }
        });
        ConnectivityManager connectivityManager
                = (ConnectivityManager) root.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return root;

    }

    public void loadData(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,GET_NEWS_CATEGORIES, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setCname(object.getString("name"));
                        categoryModel.setImage(object.getString("image"));
                        list.add(categoryModel);
                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new CategoryAdapter(list,getContext());
                recyclerView.setAdapter(adapter);
                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }
}