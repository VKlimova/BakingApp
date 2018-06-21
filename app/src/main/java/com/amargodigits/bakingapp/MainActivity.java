package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.amargodigits.bakingapp.model.Recipe;
import com.amargodigits.bakingapp.utils.NetworkUtils.LoadRecipiesTask;


import java.util.ArrayList;

import static com.amargodigits.bakingapp.utils.Json.getRecipeListStringsFromJson;

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
        mContext= getApplicationContext();
        mGridview = (GridView) findViewById(R.id.baking_grid_view);
//        mAdapter = new BakingListAdapter(mContext, R.layout.grid_item_layout, mRecipeList);

        try {
            LoadRecipiesTask mAsyncTasc = new LoadRecipiesTask(getApplicationContext());
            mAsyncTasc.execute();
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading data exception: " + e.toString());
        }


        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Recipe item = (Recipe) adapterView.getItemAtPosition(position);
                String recId = item.getId();
                String recName = item.getName();
                Log.i(LOG_TAG, "MainActivity, recId : " + recId);
//                Intent intent = new Intent(mContext, DetailActivity.class);
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("recId", recId);
                intent.putExtra("recName", recName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Log.i(LOG_TAG, "Opening recipe details exception: " + e.toString());
                }

//                Thoast.make(view, "Name="+ item.getName() + ", ID=" + item.getName(), Toast.LENGTH_LONG)
//                        .setAction("Action", null).show();
//               Intent browserIntent = new Intent(Intent.ACTION_VIEW, "recId"));
//                startActivity(browserIntent);
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
        }
        mAdapter.notifyDataSetChanged();

        try {
           mGridview.setAdapter(MainActivity.mAdapter);
        }
        catch (Exception e)
        {
            Log.i(LOG_TAG, "Exception  MainActivity.mGridview.setAdapter(MainActivity.mAdapter) = " + e.toString());
        }

        mAdapter.notifyDataSetChanged();

            return;
        }


}
