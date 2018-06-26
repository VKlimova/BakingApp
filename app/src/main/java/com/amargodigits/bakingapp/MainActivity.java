package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
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
        Log.i(LOG_TAG, "Main onCreate");
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "Main onCreate 1");
        mContext= getApplicationContext();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        Log.i(LOG_TAG, "Main onCreate 2");
        mRecyclerView = (RecyclerView) findViewById(R.id.baking_grid_view);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new GridLayoutManager(mContext, gridColumnsNumber(mContext));

        //TODO change spanCount
                mLayoutManager = new GridLayoutManager(mContext, 2);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new BakingListAdapter(mContext, mRecipeList);
//        mRecyclerView.setAdapter(mAdapter);
        Log.i(LOG_TAG, "Main onCreate 3");

        try {
            Log.i(LOG_TAG, "Main onCreate 5");
            LoadRecipiesTask mAsyncTasc = new LoadRecipiesTask(getApplicationContext());
            Log.i(LOG_TAG, "Main onCreate 6");
            mAsyncTasc.execute();
            Log.i(LOG_TAG, "Main onCreate 7");
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading data exception: " + e.toString());
            throw new RuntimeException(e);
        }
        Log.i(LOG_TAG, "Main onCreate 8");
//        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Recipe item = (Recipe) adapterView.getItemAtPosition(position);
//                String recId = item.getId();
//                String recName = item.getName();
//                Log.i(LOG_TAG, "MainActivity, recId : " + recId);
//                Intent intent = new Intent(mContext, RecipeActivity.class);
//                intent.putExtra("recId", recId);
//                intent.putExtra("recName", recName);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                try {
//                    mContext.startActivity(intent);
//                } catch (Exception e) {
//                    Log.i(LOG_TAG, "Opening recipe details exception: " + e.toString());
//                    throw new RuntimeException(e);
//                }
//            }
//        });

    }
    public static void doGridView(Context tContext) {
        try {
            mAdapter = new BakingListAdapter(tContext, mRecipeList);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(tContext, gridColumnsNumber(tContext));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        }


}
