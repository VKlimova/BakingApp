package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Step;
import com.amargodigits.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

public class StepActivity extends AppCompatActivity {

    //    public static ArrayList<Recipe> mRecipeList = new ArrayList<>();
//    public static ArrayList<Step> mStepList = new ArrayList<>();
//    public static ArrayList<Ingredient> mIngredientList = new ArrayList<>();
//    public static StepListAdapter rAdapter;
//    public static GridView rGridview;
    public static TextView stepTV, stepVideo, stepThumb;
    public Context mContext;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mContext = getApplicationContext();
//        rGridview = (GridView) findViewById(R.id.step_grid_view);
        stepTV = (TextView) findViewById(R.id.step_text_view);
        stepVideo = (TextView) findViewById(R.id.step_video_view);
        stepThumb = (TextView) findViewById(R.id.step_thumb);
        Intent intent = getIntent();
        String stepId = intent.getStringExtra("stepId");
        String stepName = intent.getStringExtra("stepName");
        String recName = intent.getStringExtra("recName");
        String stepThumbUrl = intent.getStringExtra("stepThumbUrl");
        String stepVideoUrl = intent.getStringExtra("stepVideoUrl");

        mToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(recName);
        stepTV.setText(stepName);
        stepVideo.setText(stepVideoUrl);
        stepThumb.setText(stepThumbUrl);

        Log.i(LOG_TAG, "StepActivity, stepId : " + stepId + " stepName = " + stepName);
//        try {
//            NetworkUtils.LoadStepTask mAsyncTasc = new NetworkUtils.LoadStepTask(getApplicationContext());
//            mAsyncTasc.execute(recId);
//        } catch (Exception e) {
//            Log.i(LOG_TAG, "Loading Steps data exception: " + e.toString());
//        }
//
//        try {
//            NetworkUtils.LoadIngredientTask mAsyncTasc = new NetworkUtils.LoadIngredientTask(getApplicationContext());
//            mAsyncTasc.execute(recId);
//        } catch (Exception e) {
//            Log.i(LOG_TAG, "Loading Steps data exception: " + e.toString());
//        }


        Log.i(LOG_TAG, "StepActivity, main thread ");

//        rGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Step item = (Step) adapterView.getItemAtPosition(position);
//                String recId = item.getId();
//                Intent intent = new Intent(mContext, DetailActivity.class);
//                intent.putExtra("RecId", recId);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                try {
//                    mContext.startActivity(intent);
//                } catch (Exception e) {
//                    Log.i(LOG_TAG, "Opening movie details exception: " + e.toString());
//                }
//
////                Thoast.make(view, "Name="+ item.getName() + ", ID=" + item.getName(), Toast.LENGTH_LONG)
////                        .setAction("Action", null).show();
////               Intent browserIntent = new Intent(Intent.ACTION_VIEW, "recId"));
////                startActivity(browserIntent);
//            }
//        });

    }

//    public static void doStepView(Context tContext) {
//        Log.i(LOG_TAG, "doGridView");
//        Log.i(LOG_TAG, "mRecipieList.size = " + mStepList.size());
//        Log.i(LOG_TAG, "tContext = " + tContext);
//        try {
//            //     Log.i(LOG_TAG, "  doGridView before   mAdapter.getCount=" + mAdapter.getCount() );
//            rAdapter = new StepListAdapter(tContext, R.layout.recipe_item_layout, mStepList);
//            Log.i(LOG_TAG, "  doGridView after   mAdapter.getCount=" + rAdapter.getCount());
//        } catch (Exception e) {
//            Log.i(LOG_TAG, "Exception  mAdapter = new BakingListAdapter(tContext, R.layout.grid_item_layout, mRecipeList); = " + e.toString());
//        }
//        rAdapter.notifyDataSetChanged();
//
//        try {
//            Log.i(LOG_TAG, "  mGridview.getCount before " + rGridview.getCount());
//            rGridview.setAdapter(StepActivity.rAdapter);
//            Log.i(LOG_TAG, "  mGridview.getCount after " + rGridview.getCount());
//        } catch (Exception e) {
//            Log.i(LOG_TAG, "Exception  MainActivity.mGridview.setAdapter(MainActivity.mAdapter) = " + e.toString());
//        }
//
//        rAdapter.notifyDataSetChanged();
//        Log.i(LOG_TAG, "  doGridView before return   mAdapter.getCount=" + rAdapter.getCount());
//        Log.i(LOG_TAG, "  doGridView before return   mGridview.getCount=" + rGridview.getCount());
//
//        return;
//    }

//    public static void doIngredientView(Context tContext) {
//        String ingredients = "";
//
//        Log.i(LOG_TAG, "doIngredientView");
//        Log.i(LOG_TAG, "mIngredientList.size = " + mIngredientList.size());
//        ingredients = ingredients.concat("Ingredients. ");
//        for (int j = 0; (j < mIngredientList.size()); j++) {
//            if (j > 0) {
//                ingredients = ingredients.concat("; ");
//            }
//            ingredients = ingredients.concat(mIngredientList.get(j).getIngredient())
//                    .concat(": ").concat(mIngredientList.get(j).getQuantity()).concat(" ")
//                    .concat(mIngredientList.get(j).getMeasure());
//        }
//        ingredientsTV.setText(ingredients);
//        return;
//    }


}
