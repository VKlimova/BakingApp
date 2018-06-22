package com.amargodigits.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Step;
import com.amargodigits.bakingapp.utils.NetworkUtils;
import java.util.ArrayList;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/*
ListFragment shows the list of steps for one recipe
 */
public class ListFragment extends Fragment {

    public static ArrayList<Step> mStepList = new ArrayList<>();
    public static ArrayList<Ingredient> mIngredientList = new ArrayList<>();
    public static StepListAdapter rAdapter;
    public static GridView rGridview;
    public static TextView ingredientsTV;
    public Context mContext;
    Toolbar mToolbar;
    static String recName;
    OnStepListener mStepListener;

    public interface OnStepListener {
        void onStep(String stepDescr, String stepVideoUrl, String thumbUrl);
    }
    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mStepListener = (OnStepListener) context;
        } catch (ClassCastException e) {
            Log.i(LOG_TAG, "ListFragment: must implement OnImageClickListener" );
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_steps_list, container, false);
        mContext = getContext();
        rGridview = (GridView) rootView.findViewById(R.id.step_grid_view);
        ingredientsTV = (TextView) rootView.findViewById(R.id.ingredients_text_view);
        Intent intent = getActivity().getIntent();
        String recId = intent.getStringExtra("recId");
        recName = intent.getStringExtra("recName");
        mToolbar = (Toolbar) rootView.findViewById(R.id.menu_toolbar);
        try {
            NetworkUtils.LoadStepTask mAsyncTasc = new NetworkUtils.LoadStepTask(getContext());
            mAsyncTasc.execute(recId);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Loading Steps data exception: " + e.toString());
            throw new RuntimeException(e);
        }
        try {
            NetworkUtils.LoadIngredientTask mAsyncTasc = new NetworkUtils.LoadIngredientTask(getContext());
            mAsyncTasc.execute(recId);
        } catch (Exception e) {
            Log.i(LOG_TAG, "ListFragment: Loading Steps data exception: " + e.toString());
            throw new RuntimeException(e);
        }
        rGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Step item = (Step) adapterView.getItemAtPosition(position);
                String stepDescr = item.getDescription();
                String stepThumbUrl = item.getThumbnailUrl();
                String stepVideoUrl = item.getVideoUrl();

                try {
                    mStepListener.onStep(stepDescr, stepVideoUrl, stepThumbUrl);
                } catch (Exception e) {
                    Log.i(LOG_TAG, "ListFragment mStepListener.onStep(stepDescr, stepVideoUrl, stepThumbUrl) Exception:\n "
                            + e.toString());
                    throw new RuntimeException(e);
                }
            }
        });
        return rootView;
    }

    public static void doStepView(Context tContext) {
        try {
            rAdapter = new StepListAdapter(tContext, R.layout.recipe_item_layout, mStepList);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Exception  mAdapter = new BakingListAdapter(tContext, R.layout.grid_item_layout, mRecipeList); = " + e.toString());
        }
        rAdapter.notifyDataSetChanged();

        try {
            rGridview.setAdapter(ListFragment.rAdapter);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Exception  ListFragment.mGridview.setAdapter(ListFragment.mAdapter) = " + e.toString());
            throw new RuntimeException(e);
        }

        rAdapter.notifyDataSetChanged();

        return;
    }

    public static void doIngredientView(Context tContext) {
        String ingredients = "";
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

        Context context = tContext;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        ComponentName thisWidget = new ComponentName(context, BakingWidgetProvider.class);
        remoteViews.setTextViewText(R.id.appwidget_title, recName);
        remoteViews.setTextViewText(R.id.appwidget_text, ingredients);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        return;
    }


}
