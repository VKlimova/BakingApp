package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/*
StepActivity shows one step of recipe for one-pane mode
 */
public class StepActivity extends AppCompatActivity {

    public Context mContext;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mContext = getApplicationContext();

        Intent intent = getIntent();
        String stepDescr = intent.getStringExtra("stepDescr");
        String recName = intent.getStringExtra("recName");
        String stepThumbUrl = intent.getStringExtra("stepThumbUrl");
        String stepVideoUrl = intent.getStringExtra("stepVideoUrl");

        mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(recName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepFragment stepFragment = (StepFragment) fragmentManager.findFragmentById(R.id.fragment_step);
        stepFragment.setStepDetails(stepDescr, stepVideoUrl, stepThumbUrl);
    }
}
