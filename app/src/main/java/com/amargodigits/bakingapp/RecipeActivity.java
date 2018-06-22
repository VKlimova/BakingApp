package com.amargodigits.bakingapp;
// this activity contains two fragments for master-detail view:
// On tablet:
// left fragment with recipe steps list and right fragment with step details
// On phone:
// only the list of steps


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

public class RecipeActivity extends AppCompatActivity implements ListFragment.OnStepListener {
    Toolbar mToolbar;
    String recName;
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        // Determine if two-pane or single-pane display
        if (findViewById(R.id.fragment_step) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
        Intent intent = getIntent();
        recName = intent.getStringExtra("recName");
        mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(recName);
    }

/*
onStep is called when clicked on step
 */
    @Override
    public void onStep(String stepDescr, String stepVideoUrl, String stepThumbUrl) {
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepFragment stepFragment = (StepFragment) fragmentManager.findFragmentById(R.id.fragment_step);
            stepFragment.setStepDetails(stepDescr, stepVideoUrl, stepThumbUrl);
        } else {
            Intent intent = new Intent(getApplicationContext(), StepActivity.class);
            intent.putExtra("stepDescr", stepDescr);
            intent.putExtra("recName", recName);
            intent.putExtra("stepThumbUrl", stepThumbUrl);
            intent.putExtra("stepVideoUrl", stepVideoUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                getApplicationContext().startActivity(intent);
            } catch (Exception e) {
                Log.i(LOG_TAG, "Opening stepId exception: " + e.toString());
                throw new RuntimeException(e);
            }
        }
    }
}
