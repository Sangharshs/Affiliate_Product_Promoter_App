package com.webapp.offerSRkh.ui.info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.webapp.offerSRkh.PrivacyActivity;
import com.webapp.offerSRkh.R;
import com.webapp.offerSRkh.ReportErrorActivity;

public class infoFragment extends Fragment {

    ImageView applogo;
    LinearLayout disclaimer, privacy,share_app,about_P,rate_us,official_website,report_error,contact_us;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info,container,false);
        applogo = root.findViewById(R.id.app_logo);
        privacy = root.findViewById(R.id.privacy_Policy);
        about_P = root.findViewById(R.id.about_L);
        share_app = root.findViewById(R.id.share_APP);
        rate_us = root.findViewById(R.id.rate);
        contact_us = root.findViewById(R.id.contact_us);
        official_website = root.findViewById(R.id.website_url);
        disclaimer = root.findViewById(R.id.disclaimer);



        report_error = root.findViewById(R.id.report_error);
          privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), PrivacyActivity.class);
               intent.putExtra("privacy",getString(R.string.privacy_policy_url));
               startActivity(intent);
            }
        });
          disclaimer.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(view.getContext(), PrivacyActivity.class);
                  intent.putExtra("disclaimer",getString(R.string.privacy_policy_url));
                  startActivity(intent);
              }
          });

        about_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.about_popup, null, false),width,height, true);
                pw.showAtLocation(root.findViewById(R.id.fm), Gravity.CENTER, 0, 0); }
        });
        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(root.getContext().getApplicationContext(), PLAY_STORE_URL+root.getContext().getApplicationContext().getPackageName(), Toast.LENGTH_SHORT).show();
                String urlll = getString(R.string.play_store_link)+root.getContext().getApplicationContext().getPackageName();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        getString(R.string.app_name) + "\n\n" + urlll);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                Log.d("TAG", "The interstitial wasn't loaded yet.");


            }
        });
        rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(root.getContext().getApplicationContext(), PLAY_STORE_URL+root.getContext().getApplicationContext().getPackageName(), Toast.LENGTH_SHORT).show();
                String urlll = getString(R.string.play_store_link)+root.getContext().getApplicationContext().getPackageName();
                Uri uri = Uri.parse(urlll);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        official_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlll = "https://offersrkh.com/";
                Uri uri = Uri.parse("https://offersrkh.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        report_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getContext(), ReportErrorActivity.class);
                intent.putExtra("error_report",getString(R.string.report_error));
               startActivity(intent);
            }
        });

        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReportErrorActivity.class);
                intent.putExtra("contact",getString(R.string.contact_us));
                startActivity(intent);
            }
        });
        return root;
    }
}
