package com.hruparomangmail.songbookx.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hruparomangmail.songbookx.R;

public class AboutActivity extends AppCompatActivity {
    LinearLayout visit_website_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        visit_website_layout = (LinearLayout) findViewById(R.id.visit_website_action);
        visit_website_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://apps-8aba9.firebaseapp.com/"));
                startActivity(browserIntent);
            }
        });
    }
}
