package com.webapp.offerSRkh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaSync;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.nonagon.transaction.omid.OmidMediaType;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;

import static com.webapp.offerSRkh.Apiconfig.CONTACT_US;
import static com.webapp.offerSRkh.Apiconfig.ERROR_REPORT;

public class ReportErrorActivity extends AppCompatActivity {
    EditText name_edittext, email_edittext, massage_edittext;
    Button submit;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_error);

        textView = findViewById(R.id.contact_form_title);
        name_edittext = findViewById(R.id.name);
        email_edittext = findViewById(R.id.email);
        massage_edittext = findViewById(R.id.massage);
        submit = findViewById(R.id.submit);


        Intent intent = getIntent();
        String contact = intent.getStringExtra("contact");
        String error   = intent.getStringExtra("error_report");

        if(contact != null) {
            contact.equals(getString(R.string.contact_us));
            textView.setText(getString(R.string.contact_us));
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(name_edittext !=null || email_edittext != null || massage_edittext !=null ) {
                        contactUs();
                    }else{
                        Toast.makeText(ReportErrorActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
         //   error.equals(getString(R.string.report_error));
            textView.setText(getString(R.string.report_error));
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(name_edittext !=null || email_edittext != null || massage_edittext !=null ) {
                        insertData();
                    }else{
                        Toast.makeText(ReportErrorActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }

    private void contactUs(){

        String name = name_edittext.getText().toString().trim();
        String email = email_edittext.getText().toString().trim();
        String massage = massage_edittext.getText().toString().trim();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait....");

        if(name.isEmpty()){
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(email.isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(massage.isEmpty()){
            Toast.makeText(this, "Please Enter Massage", Toast.LENGTH_SHORT).show();
            return;
        }else{
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CONTACT_US,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Success")){
                                Toast.makeText(ReportErrorActivity.this, "Done", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                finish();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(ReportErrorActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(ReportErrorActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ReportErrorActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();

                    params.put("name",name);
                    params.put("email",email);
                    params.put("massage",massage);


                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }

    private void insertData() {

        String name = name_edittext.getText().toString().trim();
        String email = email_edittext.getText().toString().trim();
        String massage = massage_edittext.getText().toString().trim();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait....");

        if(name.isEmpty()){
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(email.isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(massage.isEmpty()){
            Toast.makeText(this, "Please Enter Massage", Toast.LENGTH_SHORT).show();
            return;
        }else{
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ERROR_REPORT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Success")){
                                Toast.makeText(ReportErrorActivity.this, "Report Submit", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                finish();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(ReportErrorActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(ReportErrorActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ReportErrorActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();

                    params.put("name",name);
                    params.put("email",email);
                    params.put("massage",massage);


                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }
}