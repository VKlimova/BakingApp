package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.amargodigits.bakingapp.model.Recipe;
import com.amargodigits.bakingapp.utils.NetworkUtils.LoadRecipiesTask;
import java.util.ArrayList;

/*
Main activity shows the list of recipes
 */

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "Baking Log";
    public static ArrayList<Recipe> mRecipeList = new ArrayList<>();
    public static BakingListAdapter mAdapter;
    public static GridView mGridview;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        mContext= getApplicationContext();
        mGridview = (GridView) findViewById(R.id.baking_grid_view);
        try {
            LoadRecipiesTask mAsyncTasc = new LoadRecipiesTask(getApplicationContext());
            mAsyncTasc.execute();
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading data exception: " + e.toString());
            throw new RuntimeException(e);
        }
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Recipe item = (Recipe) adapterView.getItemAtPosition(position);
                String recId = item.getId();
                String recName = item.getName();
                Log.i(LOG_TAG, "MainActivity, recId : " + recId);
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("recId", recId);
                intent.putExtra("recName", recName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Log.i(LOG_TAG, "Opening recipe details exception: " + e.toString());
                    throw new RuntimeException(e);
                }
            }
        });

    }
    public static void doGridView(Context tContext) {
        try {
            mAdapter = new BakingListAdapter(tContext, R.layout.grid_item_layout, mRecipeList);
        }
        catch (Exception e)
        {
            Log.i(LOG_TAG, "Exception  mAdapter = new BakingListAdapter(tContext, R.layout.grid_item_layout, mRecipeList); = " + e.toString());
            throw new RuntimeException(e);
        }
        mAdapter.notifyDataSetChanged();
        try {
           mGridview.setAdapter(MainActivity.mAdapter);
        }
        catch (Exception e)
        {
            Log.i(LOG_TAG, "Exception  MainActivity.mGridview.setAdapter(MainActivity.mAdapter) = " + e.toString());
            throw new RuntimeException(e);
        }
        mAdapter.notifyDataSetChanged();

        }


}
