package com.webapp.offerSRkh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.webapp.offerSRkh.Adapter.NewsAdapter;
import com.webapp.offerSRkh.Model.Ads;
import com.webapp.offerSRkh.Model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.GET_NEWS_URL;

public class NotificationdataActivity extends AppCompatActivity {
    public ImageView imageView;
    public TextView txt_head;
    public TextView txt_content;
    List<Ads> adsList = new ArrayList<>();
    NewsModel model;
    TextView date, time;
    ImageButton sharebtn1 , whatsappshare2, instashare3, fbshare4, twittershare5,save_offline;
    String img;
    List<NewsModel> savenewslist ;
    List<NewsModel> list = new ArrayList<>();

    ///Shared Pref
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Gson gson;
    private int position = 0;
    int matchedquestionposition;
    InterstitialAd interstitialAd;
    // private InterstitialAd interstitialAd;
    CardView cardView;
    TextView btn_TITilE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationdata);



        cardView = findViewById(R.id.linkCard);
        btn_TITilE = findViewById(R.id.link_btn_text);
     //   manageBlinkEffect();

        //getBookmarks();

        txt_head = findViewById(R.id.news_Head);
        txt_content = findViewById(R.id.n_content);

        //Share Buttons
        save_offline = findViewById(R.id.save_offline);
        sharebtn1 = findViewById(R.id.simpleShare);
        whatsappshare2 = findViewById(R.id.wsShare);
        instashare3 = findViewById(R.id.instashare);
        fbshare4 = findViewById(R.id.fb);
        twittershare5 = findViewById(R.id.twitter);
        imageView = findViewById(R.id.nphoto);
        txt_head = findViewById(R.id.news_Head);
        txt_content = findViewById(R.id.n_content);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.GET, GET_NEWS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String match_id = obj.getString("id");
                    txt_content.setText(obj.getString("content"));
                    Toast.makeText(NotificationdataActivity.this, match_id, Toast.LENGTH_SHORT).show();
                    if(match_id.equals(id)){
                        Toast.makeText(NotificationdataActivity.this, id+match_id+"Id Available", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NotificationdataActivity.this, id+"Id Not Available", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(NotificationdataActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    txt_content.setText(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt_content.setText(error.getMessage());
                Toast.makeText(NotificationdataActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

    private void loadNews() {


    }
}