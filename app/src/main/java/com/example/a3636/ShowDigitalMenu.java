package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ShowDigitalMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_digital_menu);
        WebView WVDigitalMenu = findViewById(R.id.WVDigitalMenu);
        Bundle urlReceived = this.getIntent().getExtras();
        String url = urlReceived.getString("url");
        WVDigitalMenu.loadUrl(url);
    }
}