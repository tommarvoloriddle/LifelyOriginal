package com.example.saki.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class Track extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        //buttons
        Button hospital,police , maps;

        hospital = (Button) findViewById(R.id.locatehospitals);
        police = (Button) findViewById(R.id.locateps);
        maps = (Button) findViewById(R.id.surf);


        if (hospital != null) {
            hospital.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    webView = (WebView) findViewById(R.id.trackweb);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebChromeClient(new WebChromeClient());
                    webView.loadUrl("https://www.google.co.in/maps/search/hospitals+near+me/");

                }
            });
        }

        if (police != null) {
            police.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    webView = (WebView) findViewById(R.id.trackweb);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebChromeClient(new WebChromeClient());
                    webView.loadUrl("https://www.google.co.in/maps/search/police+station+near+me/");

                }
            });
        }

        if (maps != null) {
            maps.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    webView = (WebView) findViewById(R.id.trackweb);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebChromeClient(new WebChromeClient());
                    webView.loadUrl("https://www.bing.com/mapspreview");

                }
            });
        }

    }
}
