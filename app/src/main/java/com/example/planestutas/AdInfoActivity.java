package com.example.planestutas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdInfoActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_info);
        textView = (TextView) findViewById(R.id.adinfo_textview);
        Intent intent = getIntent();//谁跳过来的获取谁
        String title = intent.getStringExtra("title");
        textView.setText(title);
    }
}
