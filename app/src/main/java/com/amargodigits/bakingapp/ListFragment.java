package com.amargodigits.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Step;
import com.amargodigits.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

public class ListFragment extends Fragment {

    //    public static ArrayList<Recipe> mRecipeList = new ArrayList<>();
    public static ArrayList<Step> mStepList = new ArrayList<>();
    public static ArrayList<Ingredient> mIngredientList = new ArrayList<>();
    public static StepListAdapter rAdapter;
    public static GridView rGridview;
    public static TextView ingredientsTV;
    public Context mContext;
    Toolbar mToolbar;
    String recName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     //   super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_steps_list);
        View rootView =
                inflater.inflate(R.layout.fragment_steps_list, container, false);
        mContext = getContext();
        rGridview = (GridView) rootView.findViewById(R.id.step_grid_view);
        ingredientsTV = (TextView) rootView.findViewById(R.id.ingredients_text_view);
        Intent intent = getActivity().getIntent();
        String recId = intent.getStringExtra("recId");
         recName = intent.getStringExtra("recName");
        mToolbar = (Toolbar) rootView.findViewById(R.id.menu_toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(recName);

        Log.i(LOG_TAG, "ListFragment, recId : " + recId + " recName = " + recName);
        try {
            NetworkUtils.LoadStepTask mAsyncTasc = new NetworkUtils.LoadStepTask(getContext());
            mAsyncTasc.execute(recId);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading Steps data exception: " + e.toString());
        }

        try {
            NetworkUtils.LoadIngredientTask mAsyncTasc = new NetworkUtils.LoadIngredientTask(getContext());
            mAsyncTasc.execute(recId);
        } catch (Exception e) {
            Log.i(LOG_TAG, "ListFragment: Loading Steps data exception: " + e.toString());
        }


        Log.i(LOG_TAG, "ListFragment, main thread after AsyncTAsc");

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
                }

//                Thoast.make(view, "Name="+ item.getName() + ", ID=" + item.getName(), Toast.LENGTH_LONG)
//                        .setAction("Action", null).show();
//               Intent browserIntent = new Intent(Intent.ACTION_VIEW, "recId"));
//                startActivity(browserIntent);
            }
        });

        Log.i(LOG_TAG, "ListFragment, main thread before return");

        return rootView;
    }

    public static void doStepView(Context tContext) {
        Log.i(LOG_TAG, "doGridView");
        Log.i(LOG_TAG, "mRecipieList.size = " + mStepList.size());
        Log.i(LOG_TAG, "tContext = " + tContext);
        try {
            //     Log.i(LOG_TAG, "  doGridView before   mAdapter.getCount=" + mAdapter.getCount() );
            rAdapter = new StepListAdapter(tContext, R.layout.recipe_item_layout, mStepList);
            Log.i(LOG_TAG, "  doGridView after   mAdapter.getCount=" + rAdapter.getCount());
        } catch (Exception e) {
            Log.i(LOG_TAG, "Exception  mAdapter = new BakingListAdapter(tContext, R.layout.grid_item_layout, mRecipeList); = " + e.toString());
        }
        rAdapter.notifyDataSetChanged();

        try {
            Log.i(LOG_TAG, "  mGridview.getCount before " + rGridview.getCount());
            rGridview.setAdapter(ListFragment.rAdapter);
            Log.i(LOG_TAG, "  mGridview.getCount after " + rGridview.getCount());
        } catch (Exception e) {
            Log.i(LOG_TAG, "Exception  ListFragment.mGridview.setAdapter(ListFragment.mAdapter) = " + e.toString());
        }

        rAdapter.notifyDataSetChanged();
        Log.i(LOG_TAG, "  doGridView before return   mAdapter.getCount=" + rAdapter.getCount());
        Log.i(LOG_TAG, "  doGridView before return   mGridview.getCount=" + rGridview.getCount());

        return;
    }

    public static void doIngredientView(Context tContext) {
        String ingredients = "";

        Log.i(LOG_TAG, "doIngredientView");
        Log.i(LOG_TAG, "mIngredientList.size = " + mIngredientList.size());
        ingredients = ingredients.concat("Ingredients. ");
        for (int j = 0; (j < mIngredientList.size()); j++) {
            if (j > 0) {
                ingredients = ingredients.concat("; ");
            }
            ingredients = ingredients.concat(mIngredientList.get(j).getIngredient())
                    .concat(": ").concat(mIngredientList.get(j).getQuantity()).concat(" ")
                    .concat(mIngredientList.get(j).getMeasure());
        }
        ingredientsTV.setText(ingredients);
        return;
    }


}
