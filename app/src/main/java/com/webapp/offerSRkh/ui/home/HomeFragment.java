package com.webapp.offerSRkh.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.webapp.offerSRkh.Adapter.HomescreenFeaturedAdapter;
import com.webapp.offerSRkh.Adapter.NewshomeAdapter;
import com.webapp.offerSRkh.Adapter.RoundedcategoryAdapter;
import com.webapp.offerSRkh.Adapter.SliderAdapter;
import com.webapp.offerSRkh.Model.CategoryModel;
import com.webapp.offerSRkh.Model.NewsModel;
import com.webapp.offerSRkh.Model.SliderModel;
import com.webapp.offerSRkh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.webapp.offerSRkh.Apiconfig.GET_FEATURED;
import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_CATEGORIES;
import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_URL;
import static com.webapp.offerSRkh.Apiconfig.GET_SLIDER_IMAGES;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView, recyclerView_featured, recyclerView_home_news_content, recyclerview_rounded_categories;
    List<NewsModel> offer_list = new ArrayList<>();
    List<NewsModel> featured_news_list = new ArrayList<>();
    List<SliderModel> sliderModelList = new ArrayList<>();
    List<CategoryModel> list_category = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    NewshomeAdapter adapter_home;
    NewshomeAdapter adapter1;
    String id;
    Context context;
    private ViewPager bannersliderViewPager;
    private int currentpage = 0;
    Timer timer;
    AlertDialog.Builder builder;
    final private long DELAY_TIME = 1500;
    final private long PERIOD_TIME = 1500;
    ProgressBar pbar;
    View wrong;
    View noInte;
    Button button1, button2;
    RoundedcategoryAdapter roundedcategoryAdapter;
    HomescreenFeaturedAdapter homescreenFeaturedAdapter;
    CardView all_cat_card;
    ImageButton view_all_1;
    Boolean isScrolling = false;
    int total_items, current_items, onScrolled_items;
    ShimmerFrameLayout container1;
    LinearLayout hide_content_linearLayout;
    LinearLayoutManager linearLayoutManager2;
    NewsModel newsModel;
    ProgressBar end_progress_bar;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerview_rounded_categories = root.findViewById(R.id.rounded_category_recyclerview);
        recyclerView = root.findViewById(R.id.recyclerview_home);
        recyclerView_featured = root.findViewById(R.id.recyclerview_home_featured);
        recyclerView_home_news_content = root.findViewById(R.id.recyclerview_home_News_content);
        view_all_1 = root.findViewById(R.id.view_all_btn_1);
        builder = new AlertDialog.Builder(getContext());
        hide_content_linearLayout = root.findViewById(R.id.lllllllll);

        container1 = root.findViewById(R.id.shimmer_view_container);

        noInte = root.findViewById(R.id.noInternetLayout);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefresh);
        all_cat_card = root.findViewById(R.id.all_c_card);
        bannersliderViewPager = root.findViewById(R.id.image_slider_view_pager);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sliderModelList.clear();
                featured_news_list.clear();
                list_category.clear();
                offer_list.clear();

                    //getActivity().getSupportFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();
                    getFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();
            }
        });

        displayData();
//container1.setVisibility(View.GONE);
//swipeRefreshLayout.setVisibility(View.VISIBLE);
        return root;
    }

    private void displayData() {
        LinearLayoutManager layoutManager_round = new LinearLayoutManager(getContext());
        layoutManager_round.setOrientation(RecyclerView.HORIZONTAL);
        recyclerview_rounded_categories.setLayoutManager(layoutManager_round);
        recyclerview_rounded_categories.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(getContext());
        linearLayoutManager0.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager0);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_featured.setLayoutManager(linearLayoutManager1);
        recyclerView_featured.setHasFixedSize(true);

        linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        recyclerView_home_news_content.setLayoutManager(linearLayoutManager2);
        recyclerView_home_news_content.setHasFixedSize(true);

        homescreenFeaturedAdapter = new HomescreenFeaturedAdapter(getContext(), featured_news_list);
        recyclerView_featured.setAdapter(homescreenFeaturedAdapter);
        recyclerView_featured.setNestedScrollingEnabled(false);

        adapter1 = new NewshomeAdapter(getContext(), offer_list);
        recyclerView_home_news_content.setAdapter(adapter1);
        recyclerView_home_news_content.setNestedScrollingEnabled(false);

        roundedcategoryAdapter = new RoundedcategoryAdapter(list_category, getContext());
        recyclerview_rounded_categories.setAdapter(roundedcategoryAdapter);
        recyclerview_rounded_categories.setNestedScrollingEnabled(false);

        loadCategory();

        loadFeaturedNews();

        load_offers();

        loadIM();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void loadFeaturedNews() {
        container1.startShimmer();
        container1.setAlpha((float) 0.4);
        hide_content_linearLayout.setVisibility(View.GONE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_FEATURED, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                featured_news_list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        NewsModel newsModel = new NewsModel();

                        id = object.getString("id");
                        // id = newsModel.setId(object.getString("id"));
                        newsModel.setNewsImg(object.getString("image"));
                        newsModel.setNews_heading(object.getString("heading"));

                        newsModel.setNewsContent(object.getString("content"));
                        newsModel.setId(object.getString("id"));

                        newsModel.setUrl(object.getString("url"));
                        newsModel.setDate(object.getString("date"));
                        newsModel.setTime(object.getString("time"));
                        newsModel.setBtn_name(object.getString("btn_name"));
                        newsModel.setBtn_url(object.getString("btn_url"));

                        featured_news_list.add(newsModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Collections.reverse(featured_news_list);

                homescreenFeaturedAdapter = new HomescreenFeaturedAdapter(getContext(), featured_news_list);
                recyclerView_featured.setAdapter(homescreenFeaturedAdapter);
                recyclerView_featured.setHasFixedSize(true);
                recyclerView_featured.setNestedScrollingEnabled(false);

                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                hide_content_linearLayout.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                     swipeRefreshLayout.setRefreshing(false);
//                     container1.setVisibility(View.GONE);
//                     linearLayout.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    public void load_offers() {
        container1.startShimmer();
        container1.setAlpha((float) 0.4);
        hide_content_linearLayout.setVisibility(View.GONE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_NEWS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                offer_list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        newsModel = new NewsModel();

                        id = object.getString("id");

                        newsModel.setNewsImg(object.getString("image"));
                        newsModel.setNews_heading(object.getString("heading"));

                        newsModel.setNewsContent(object.getString("content"));
                        newsModel.setId(object.getString("id"));

                        newsModel.setUrl(object.getString("url"));
                        newsModel.setDate(object.getString("date"));
                        newsModel.setTime(object.getString("time"));
                        newsModel.setBtn_name(object.getString("btn_name"));
                        newsModel.setBtn_url(object.getString("btn_url"));
                        offer_list.add(newsModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.reverse(offer_list);
                adapter_home = new NewshomeAdapter(getContext(), offer_list);
                recyclerView_home_news_content.setAdapter(adapter_home);
                recyclerView_home_news_content.setHasFixedSize(true);
                adapter_home.notifyDataSetChanged();

                container1.stopShimmer();
                container1.setVisibility(View.GONE);

                hide_content_linearLayout.setVisibility(View.VISIBLE);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                    linearLayout.setVisibility(View.VISIBLE);
//                    // swipeRefreshLayout.setRefreshing(false);
//                    container1.stopShimmer();
//                    container1.setVisibility(View.GONE);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    public void loadCategory() {
        container1.startShimmer();
        container1.setAlpha((float) 0.4);
        hide_content_linearLayout.setVisibility(View.GONE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_NEWS_CATEGORIES, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                list_category.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setCname(object.getString("name"));
                        categoryModel.setImage(object.getString("image"));
                        list_category.add(categoryModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                roundedcategoryAdapter = new RoundedcategoryAdapter(list_category, getContext());
                recyclerview_rounded_categories.setAdapter(roundedcategoryAdapter);
                recyclerview_rounded_categories.setHasFixedSize(true);

                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                hide_content_linearLayout.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    public void loadIM() {
        container1.startShimmer();
        container1.setAlpha((float) 0.4);
        hide_content_linearLayout.setVisibility(View.GONE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_SLIDER_IMAGES, null, new Response.Listener<JSONArray>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onResponse(JSONArray response) {
                sliderModelList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        SliderModel slideModel = new SliderModel(object.getString("img_url"));
                        ///////BANNER SLIDER/////
                        sliderModelList.add(slideModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (sliderModelList.isEmpty()) {
                    bannersliderViewPager.setVisibility(View.GONE);

                } else {
                    bannersliderViewPager.setVisibility(View.VISIBLE);
                }
                SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
                bannersliderViewPager.setAdapter(sliderAdapter);
                bannersliderViewPager.setAdapter(sliderAdapter);
                bannersliderViewPager.setClipToPadding(false);
                bannersliderViewPager.setPageMargin(20);

                container1.stopShimmer();
                container1.setVisibility(View.GONE);
                hide_content_linearLayout.setVisibility(View.VISIBLE);

                ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        currentpage = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        if (state == ViewPager.SCROLL_STATE_IDLE) ;
                        pageLooper();
                    }
                };
                bannersliderViewPager.addOnPageChangeListener(onPageChangeListener);

                startbannerslideShow();
                bannersliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        pageLooper();
                        stopbannerSlideShow();
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            startbannerslideShow();
                        }
                        return false;
                    }
                });
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private void startbannerslideShow() {
        Handler handler = new Handler();
        Runnable udate = new Runnable() {
            @Override
            public void run() {
                if (currentpage >= sliderModelList.size()) {
                    currentpage = 0;
                }
                bannersliderViewPager.setCurrentItem(currentpage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(udate);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    private void stopbannerSlideShow() {
        timer.cancel();
    }

    private void pageLooper() {
        if (currentpage == sliderModelList.size()) {
            currentpage++;
            bannersliderViewPager.setCurrentItem(currentpage, false);
        }
        if (currentpage == 0) {
            currentpage--;
            bannersliderViewPager.setCurrentItem(currentpage, false);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

