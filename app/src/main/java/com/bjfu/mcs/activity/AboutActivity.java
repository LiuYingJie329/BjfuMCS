package com.bjfu.mcs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bjfu.mcs.R;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.base.CheckPermissionsActivity;

public class AboutActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("关于MCS");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "分享MCS", Snackbar.LENGTH_LONG)
//                        .setAction("Action", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                                sharingIntent.setType("text/plain");
//                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
//                                sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name));
//                                startActivity(Intent.createChooser(sharingIntent, getString(R.string.app_name)));
//                            }
//                        }).show();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name));
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.app_name)));

            }
        });
    }
}
