package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Step;
import com.amargodigits.bakingapp.utils.NetworkUtils;
import java.util.ArrayList;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/*
Detail Activity shows the list of recipes
 */

public class DetailActivity extends AppCompatActivity {

    public static GridView rGridview;
    public static TextView ingredientsTV;
    public Context mContext;
    Toolbar mToolbar;
    String recName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext = getApplicationContext();
        rGridview = (GridView) findViewById(R.id.step_grid_view);
        ingredientsTV = (TextView) findViewById(R.id.ingredients_text_view);
        Intent intent = getIntent();
        String recId = intent.getStringExtra("recId");
         recName = intent.getStringExtra("recName");
        mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(recName);
        try {
            NetworkUtils.LoadStepTask mAsyncTasc = new NetworkUtils.LoadStepTask(getApplicationContext());
            mAsyncTasc.execute(recId);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading Steps data exception: " + e.toString());
            throw new RuntimeException(e);
        }

        try {
            NetworkUtils.LoadIngredientTask mAsyncTasc = new NetworkUtils.LoadIngredientTask(getApplicationContext());
            mAsyncTasc.execute(recId);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading Steps data exception: " + e.toString());
            throw new RuntimeException(e);
        }


        Log.i(LOG_TAG, "DetailActivity, main thread after AsyncTAsc");

        rGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Step item = (Step) adapterView.getItemAtPosition(position);
                String stepId = item.getId();
                String stepName = item.getShortDescription();
                String stepDescr=item.getDescription();
                String stepThumbUrl=item.getThumbnailUrl();
                String stepVideoUrl=item.getVideoUrl();
                Intent intent = new Intent(mContext, StepActivity.class);
                intent.putExtra("stepId", stepId);
                intent.putExtra("stepName", stepName);
                intent.putExtra("stepDescr", stepDescr);
                intent.putExtra("recName", recName);
                intent.putExtra("stepThumbUrl",stepThumbUrl);
                intent.putExtra("stepVideoUrl", stepVideoUrl);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Log.i(LOG_TAG, "Opening stepId exception: " + e.toString());
                    throw new RuntimeException(e);
                }

            }
        });

    }

}
