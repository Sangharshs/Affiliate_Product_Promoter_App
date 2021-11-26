package com.webapp.offerSRkh;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        final ProgressBar progressBar = findViewById(R.id.pbr);
        final WebView webView = findViewById(R.id.privacy_policy_load);
                   webView.setVisibility(View.INVISIBLE);
                   webView.setWebViewClient(new WebViewClient());
                   webView.getSettings().setJavaScriptEnabled(true);
                   webView.setWebChromeClient(new WebChromeClient());
                   webView.setWebViewClient(new WebViewClient(){

                       @Override
                       public void onPageStarted(WebView view, String url, Bitmap favicon) {
                           super.onPageStarted(view, url, favicon);
                       }

                       @Override
                       public void onPageFinished(WebView view, String url) {
                           super.onPageFinished(view, url);
                           progressBar.setVisibility(View.GONE);
                           webView.setVisibility(View.VISIBLE);
                       }
                   });
                   String privacy = getIntent().getStringExtra("privacy");
                   String disclaimer = getIntent().getStringExtra("disclaimer");

                   if(privacy != null) {
                       webView.loadUrl(getString(R.string.privacy_policy_url));
                   }
                   else{
                       webView.loadUrl(getString(R.string.disclaimer_url));
                   }
    }
}