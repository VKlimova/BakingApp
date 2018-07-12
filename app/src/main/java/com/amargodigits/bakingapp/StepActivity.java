package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amargodigits.bakingapp.utils.NavigationUtils;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/*
StepActivity shows one step of recipe for one-pane mode
 */
public class StepActivity extends AppCompatActivity {

    public Context mContext;
    Toolbar mToolbar;
    Button nextStepBtn, prevStepBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mContext = getApplicationContext();

        Intent intent = getIntent();
        String stepDescr = intent.getStringExtra("stepDescr");
        final String recName = intent.getStringExtra("recName");
        String stepThumbUrl = intent.getStringExtra("stepThumbUrl");
        String stepVideoUrl = intent.getStringExtra("stepVideoUrl");
        final String stepId = intent.getStringExtra("stepId");

        mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(recName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepFragment stepFragment = (StepFragment) fragmentManager.findFragmentById(R.id.fragment_step);
        stepFragment.setStepDetails(stepDescr, stepVideoUrl, stepThumbUrl);

        nextStepBtn = (Button) findViewById(R.id.btnNextStep);
        prevStepBtn = (Button) findViewById(R.id.btnPrevStep);

        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch
                Log.i(LOG_TAG, "StepActivity .onClickNextStep (mContext,  recId)\n recId=" + stepId);
                NavigationUtils.onClickNextStep(mContext, recName, stepId);
            }
        });
        prevStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch
                Log.i(LOG_TAG, "StepActivity .onClickPrevStep (mContext,  recId)\n recId=" + stepId);
                NavigationUtils.onClickPrevStep(mContext, recName, stepId);
            }
        });
    }
}
