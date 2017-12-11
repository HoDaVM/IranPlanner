package com.iranplanner.tourism.iranplanner.ui.navigationDrawer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

public class AboutUsActivity extends StandardActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("درباره ما");

        init();
    }

    private void init() {
        findViewById(R.id.aboutUsWebSiteTv).setOnClickListener(this);
        findViewById(R.id.aboutUsInstagramIv).setOnClickListener(this);
        findViewById(R.id.aboutUsLinkedInIv).setOnClickListener(this);
        findViewById(R.id.aboutUsTelegramIv).setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void onClick(View view) {
        String url = "";
        Intent i = new Intent(Intent.ACTION_VIEW);

        switch (view.getId()) {
            case R.id.aboutUsInstagramIv:
                url = "https://www.instagram.com/iranplanner/";
                break;
            case R.id.aboutUsLinkedInIv:
                url = "https://www.linkedin.com/company/13306040/";
                break;
            case R.id.aboutUsTelegramIv:
                url = "https://t.me/Iranplanner";
                break;
            case R.id.aboutUsWebSiteTv:
                url = "https://www.iranplanner.com";
                break;
        }

        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
