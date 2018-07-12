package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.amargodigits.bakingapp.utils.NetworkUtils;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/*
Detail Activity shows the ingredients and list of steps
 */

public class DetailActivity extends AppCompatActivity {

    public static RecyclerView rRecyclerView;
    public static TextView ingredientsTV;
    public Context mContext;
    Toolbar mToolbar;
    String recName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext = getApplicationContext();
        rRecyclerView = (RecyclerView) findViewById(R.id.step_grid_view);
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
            Log.i(LOG_TAG, "Loading Ingredients data exception: " + e.toString());
            throw new RuntimeException(e);
        }
    }

}
