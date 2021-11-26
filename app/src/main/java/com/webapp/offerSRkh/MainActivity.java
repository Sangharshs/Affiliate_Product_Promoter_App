package com.webapp.offerSRkh;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.webapp.offerSRkh.Adapter.FeaturedAdapter;
import com.webapp.offerSRkh.Model.Ads;
import com.webapp.offerSRkh.Model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.ibrahimsn.lib.SmoothBottomBar;
import meow.bottomnavigation.MeowBottomNavigation;

import static com.webapp.offerSRkh.Apiconfig.GET_ADS_SETTINGS;
import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_URL;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FeaturedAdapter adapter_home;
    List<NewsModel> loaded_news_list;
    List<Ads> adsList = new ArrayList<>();

    public static final String BANNER_AD = null;
    // ActivityMain2Binding binding;
    public static final String BrodcastStringForAction = "checkinternet";
    // AdView mAdView;
    private IntentFilter mIntentFilter;
    Toolbar toolbar;
    ImageView search_btn, share_btn;
    View no_interenet_design, fragment_home, fragment_dash, fragment_trend, fragment_search, fragment_info;
    SearchView searchview;

    private static final int HOME_ICON = 1;
    private static final int POPULAR_ICON = 2;
    private static final int MENU_ICON = 3;
    private static final int FEATURED_ICON = 4;
    private static final int CATEGORY_ICON = 5;
    NavController navController;

    @SuppressLint("CutPasteId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        searchview = findViewById(R.id.search_View);
        share_btn = findViewById(R.id.share_btn);
        recyclerView = findViewById(R.id.news_list);

        loaded_news_list = new ArrayList<>();

        adapter_home = new FeaturedAdapter(getApplicationContext(), loaded_news_list);
        recyclerView.setAdapter(adapter_home);

        searchview.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout relativeLayout = findViewById(R.id.hide_layout);
                relativeLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                searchview.setFitsSystemWindows(true);
                searchview.setBackgroundColor(getResources().getColor(R.color.browser_actions_bg_grey));
            }
        });
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                RelativeLayout relativeLayout = findViewById(R.id.hide_layout);
                relativeLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                searchview.onActionViewCollapsed();
                return true;
            }
        });
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlll = getString(R.string.play_store_link) + getApplicationContext().getPackageName();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        getString(R.string.app_name) + "\n\n" + urlll);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        BottomNavigationView navView = findViewById(R.id.nav_view);


        no_interenet_design = findViewById(R.id.no_internet_design);
        fragment_home = findViewById(R.id.nav_host_fragment);
        no_interenet_design.setVisibility(View.GONE);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BrodcastStringForAction);
        Intent serviceIntent = new Intent(this, MyServices.class);
        startService(serviceIntent);
        if (isOnline(getApplicationContext())) {

            // no_interenet_design.setVisibility(View.VISIBLE);
            setVisibility_ON();
        } else {
            setVisibility_OFF();
        }
        toolbar = findViewById(R.id.toolBar);
        search_result();






        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_trending_news, R.id.navigation_info, R.id.navigation_search, R.id.navigation_dashboard)
                .build();

        navController  = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void search_result() {
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

    @Override
    public void onBackPressed() {
        if (!searchview.isIconified()) {
            searchview.setIconified(true);
            // This method does not ex
            searchview.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
    }

    private void se() {


        searchview.setQueryHint("Search Offers Here");
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public BroadcastReceiver myReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BrodcastStringForAction)) {
                if (intent.getStringExtra("online_status").equals("true")) {
                    setVisibility_ON();
                } else {
                    setVisibility_OFF();

                }
            }
        }
    };

    public Boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        registerReceiver(myReciver, mIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(myReciver, mIntentFilter);
    }

    public void setVisibility_ON() {
        no_interenet_design.setVisibility(View.GONE);
        fragment_home.setVisibility(View.VISIBLE);
    }

    public void setVisibility_OFF() {
        no_interenet_design.setVisibility(View.VISIBLE);
        fragment_home.setVisibility(View.GONE);
        registerReceiver(myReciver, mIntentFilter);
    }

    @Override
    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
        unregisteredBrodcastReciver();
        super.onDestroy();
    }


    protected void unregisteredBrodcastReciver() {
        try {
            unregisterReceiver(myReciver);
            // unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search News Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                adapter_home.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }


}