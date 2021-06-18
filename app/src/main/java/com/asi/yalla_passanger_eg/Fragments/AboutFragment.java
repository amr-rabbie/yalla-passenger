package com.asi.yalla_passanger_eg.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asi.yalla_passanger_eg.R;


public class AboutFragment extends Fragment {

    TextView url,play,fb,legal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_about, container, false);


        url=v.findViewById(R.id.url);
        play=v.findViewById(R.id.play);
        fb=v.findViewById(R.id.fb);
        legal=v.findViewById(R.id.legal);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://197.161.95.169:82/YallaSaudi/"));
                startActivity(browserIntent);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.taxicom"));
                startActivity(browserIntent);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Yalla-Taxi-2026543190976944"));
                startActivity(browserIntent);
            }
        });

        legal.setText("These Terms of Use (\"Terms\") govern your access or use, from within the Sudan and its territories and possessions, of the applications, websites, content, products, and services (the \"Services,\" as more fully defined below in Section 3) made available in the United States and its territories and possessions by Yalla Sudan, LLC and its parents, subsidiaries, representatives, affiliates, officers and directors (collectively, \"Yalla\"). PLEASE READ THESE TERMS CAREFULLY, AS THEY CONSTITUTE A LEGAL AGREEMENT BETWEEN YOU AND UBER. In these Terms, the words \"including\" and \"include\" mean \"including, but not limited to.\"\n" +
                "\n" +
                "By accessing or using the Services, you confirm your agreement to be bound by these Terms. If you do not agree to these Terms, you may not access or use the Services. These Terms expressly supersede prior agreements or arrangements with you. Uber may immediately terminate these Terms or any Services with respect to you, or generally cease offering or deny access to the Services or any portion thereof, at any time for any reason.");

        return v;
    }


}
