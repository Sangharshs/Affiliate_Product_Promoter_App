package com.webapp.offerSRkh;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sysdata.htmlspanner.HtmlSpanner;
import com.webapp.offerSRkh.Model.Ads;
import com.webapp.offerSRkh.Model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlFormatter;
import org.sufficientlysecure.htmltextview.HtmlFormatterBuilder;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.sufficientlysecure.htmltextview.OnClickATagListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webapp.offerSRkh.Apiconfig.BANNER_AD;
import static com.webapp.offerSRkh.Apiconfig.ERROR_REPORT;
import static com.webapp.offerSRkh.Apiconfig.GET_ADS_SETTINGS;
import static com.webapp.offerSRkh.Apiconfig.IMAGE_URL;
import static com.webapp.offerSRkh.Apiconfig.VIEW_COUNT;

public class FullNewsActivity extends AppCompatActivity {
    public ImageView imageView;
    public  TextView txt_head,date, time,btn_TITilE;
    WebView txt_content;
    List<Ads> adsList = new ArrayList<>();
    WebView web_view;
    ImageButton sharebtn1 , whatsappshare2, instashare3, fbshare4, twittershare5,save_offline,share_on_gmail;
    String img;
    InterstitialAd interstitialAd;
    CardView cardView;
    AdView mAdView,mAdView1;

    String apk_link;
    String share_body;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullnews);

     //   Toast.makeText(this, share_body, Toast.LENGTH_SHORT).show();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        apk_link = "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName();
         share_body  = "Install offerSRkh if not yet: "+apk_link+"   \n" +
                 "\n" +
                 "What Is OfferSRkh?\n" +
                 "OfferSRkh is an app that provides all kinds of best offers coming from top Indian Online stores like: Amazon, Flipkart, Paytm, PhonePe, Myntra, Zomato, Oyo, Hotstar, Godaddy and many more."+"\n\n"+
                 "Fully Indian App. We need your support.";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, GET_ADS_SETTINGS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        Ads ads = new Ads(object.getString("banner"),object.getString("interstitial"));
                        View view = findViewById(R.id.adView1);
                        View view1 = findViewById(R.id.adView);
                        mAdView = new AdView(getApplicationContext());
                        mAdView1 = new AdView(getApplicationContext());
                        ((RelativeLayout) view).addView(mAdView);
                        ((RelativeLayout) view1).addView(mAdView1);
                        mAdView.setAdSize(AdSize.BANNER);
                        mAdView1.setAdSize(AdSize.BANNER);
                        mAdView.setAdUnitId(ads.getBannerAd());
                        mAdView1.setAdUnitId(ads.getBannerAd());
                        AdRequest adRequest1 = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest1);
                        mAdView1.loadAd(adRequest1);

                        MobileAds.initialize(getApplicationContext(),
                                "ca-app-pub-3940256099942544~3347511713");

                        interstitialAd = new InterstitialAd(getApplicationContext());
                        interstitialAd.setAdUnitId(ads.getInterstitial_Ad());
                        interstitialAd.loadAd(new AdRequest.Builder().build());

                     //   Toast.makeText(FullNewsActivity.this, ads.getBannerAd(), Toast.LENGTH_SHORT).show();
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

        cardView = findViewById(R.id.linkCard);
        btn_TITilE = findViewById(R.id.link_btn_text);
        manageBlinkEffect();


        txt_head = findViewById(R.id.news_Head);


        //Share Buttons
        save_offline = findViewById(R.id.save_offline);
        sharebtn1 = findViewById(R.id.simpleShare);
        whatsappshare2 = findViewById(R.id.wsShare);
        instashare3 = findViewById(R.id.instashare);
        fbshare4 = findViewById(R.id.fb);
        twittershare5 = findViewById(R.id.twitter);
        imageView = findViewById(R.id.nphoto);
        txt_head = findViewById(R.id.news_Head);
        share_on_gmail = findViewById(R.id.mail);

  //      webView = findViewById(R.id.webView_for_content);
       txt_content= findViewById(R.id.webView_for_content);
     //  txt_content = findViewById(R.id.n_content);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

       loadData();
        insertData();
        final Intent intent = getIntent();
        final String img = intent.getStringExtra("image");
        Glide.with(imageView.getContext()).load(IMAGE_URL+img).into(imageView);


    }
    private void insertData() {
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");

            ProgressDialog progressDialog = new ProgressDialog(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, VIEW_COUNT+id,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("Success")) {
                            }else{
    }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id",id);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
    }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
        private void loadData() {
            final Intent intent = getIntent();

            if(intent == null){
                Intent getIntent = getIntent();
                String id =  getIntent.getStringExtra("id");
                Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            }

            img = intent.getStringExtra("image");
            String txt_head1 = intent.getStringExtra("heading");
            String date1 = intent.getStringExtra("date");
            String time1 = intent.getStringExtra("time");
            String txt_content1 = intent.getStringExtra("content");
            String url = intent.getStringExtra("url");
            String btn_URL= intent.getStringExtra("btn_url");
            String btn_Name = intent.getStringExtra("btn_name");



            //txt_content.loadUrl("google.com");
                if(btn_Name != null) {
                    cardView.setVisibility(View.VISIBLE);
                    btn_TITilE.setText(btn_Name);
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(btn_URL);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                }
            assert btn_Name != null;
            if(btn_Name.isEmpty() || btn_TITilE.getText().toString().equals("null")){
                    cardView.setVisibility(View.GONE);
                }

            assert txt_content1 != null;
            if (txt_content1.equals("null") && url != null){
                Intent intent1 = new Intent(this, OpenwebActivity.class);
                intent1.putExtra("url",url);
                startActivity(intent1);
            }else {
                txt_content.loadDataWithBaseURL((String) null, "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/Roboto-Regular.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>" + txt_content1 + "</body></html>", "text/html", "UTF-8", (String) null);
            }

            time.setText(time1);
            date.setText(date1);
            txt_head.setText(Html.fromHtml(txt_head1));

            share_on_gmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.google.android.gm");
                    shareIntent.setType("text/plain");
                    String shareBody = txt_head.getText().toString();
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,  shareBody + "\n\n"
                            + share_body);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                    try {
                        startActivity(shareIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(FullNewsActivity.this, "not been installed", Toast.LENGTH_SHORT).show();
                        //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                    }
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            });
             sharebtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareBody = txt_head.getText().toString();
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            shareBody + "\n\n"+share_body);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            });

            whatsappshare2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.whatsapp");
                    shareIntent.setType("text/plain");
                    String shareBody = txt_head.getText().toString();
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,  shareBody + "\n\n"
                            + share_body);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                    try {
                        startActivity(shareIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(FullNewsActivity.this, "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();
                        //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                    }
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            });


            instashare3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.instagram.android");
                    shareIntent.setType("text/plain");
                    String shareBody = txt_head.getText().toString();
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            shareBody + "\n\n" + share_body);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                    try {
                        startActivity(shareIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(FullNewsActivity.this, "Instagram have not been installed", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            });
            fbshare4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.facebook.orca");
                    shareIntent.setType("text/plain");
                    String shareBody = txt_head.getText().toString();
                   // String contentShare = txt_content.getText().toString();
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            shareBody + "\n\n" + share_body);

                    try {
                        startActivity(shareIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(FullNewsActivity.this, "Facebook have not been installed", Toast.LENGTH_SHORT).show();
                        //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                    }
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            });

            twittershare5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.twitter.android");
                    shareIntent.setType("text/plain");
                    String shareBody = txt_head.getText().toString();
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            shareBody + "\n\n" + share_body);
                    try {
                        startActivity(shareIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(FullNewsActivity.this, "Twitter have not been installed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @SuppressLint("WrongConstant")
    private void manageBlinkEffect() {
        ObjectAnimator anim = ObjectAnimator.ofInt(btn_TITilE, "backgroundColor", Color.DKGRAY, Color.RED,
                Color.BLUE);
        anim.setDuration(2500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
    }

}
