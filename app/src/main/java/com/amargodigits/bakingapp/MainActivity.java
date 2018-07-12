package com.amargodigits.bakingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.amargodigits.bakingapp.model.Recipe;
import com.amargodigits.bakingapp.utils.NetworkUtils.LoadRecipiesTask;
import java.util.ArrayList;
import static com.amargodigits.bakingapp.BakingListAdapter.gridColumnsNumber;

/*
Main activity shows the list of recipes
 */

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "Baking Log";
    public static ArrayList<Recipe> mRecipeList = new ArrayList<>();
    public static RecyclerView mRecyclerView;
    public static BakingListAdapter mAdapter;
    public static GridLayoutManager mLayoutManager;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        mRecyclerView = (RecyclerView) findViewById(R.id.baking_grid_view);

        //TODO change spanCount
        mLayoutManager = new GridLayoutManager(mContext, 2);
        try {
            LoadRecipiesTask mAsyncTasc = new LoadRecipiesTask(getApplicationContext());
            mAsyncTasc.execute();
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading data exception: " + e.toString());
            throw new RuntimeException(e);
        }

    }

    public static void doGridView(Context tContext) {
        try {
            mAdapter = new BakingListAdapter(tContext, mRecipeList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(tContext, gridColumnsNumber(tContext));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


}
