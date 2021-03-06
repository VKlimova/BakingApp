package com.amargodigits.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Step;
import com.amargodigits.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/*
ListFragment shows the list of steps for one recipe
 */
public class ListFragment extends Fragment {

    public static ArrayList<Step> mStepList = new ArrayList<>();
    public static ArrayList<Ingredient> mIngredientList = new ArrayList<>();
    public static StepListAdapter rAdapter;
    public static RecyclerView rRecyclerView;
    public static GridLayoutManager rLayoutManager;
    public static TextView ingredientsTV;
    public Context mContext;
    Toolbar mToolbar;
    static String recName;
    OnStepListener mStepListener;

    public static interface OnStepListener {
        void onStep(String stepDescr, String stepVideoUrl, String thumbUrl, String stepId);
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
            Log.i(LOG_TAG, "ListFragment: must implement OnImageClickListener");
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_steps_list, container, false);
        mContext = getContext();
        rRecyclerView = (RecyclerView) rootView.findViewById(R.id.step_grid_view);
        rLayoutManager = new GridLayoutManager(mContext, 1);
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

        return rootView;
    }

    public static void doStepView(Context tContext) {
        try {
            rAdapter = new StepListAdapter(tContext, mStepList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        rRecyclerView.setHasFixedSize(true);
        rLayoutManager = new GridLayoutManager(tContext, 1);
        rRecyclerView.setLayoutManager(rLayoutManager);
        rRecyclerView.setAdapter(rAdapter);
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
        final String PREFS_NAME = "BakingAppWidget";
        SharedPreferences.Editor spEditor = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        spEditor.putString("recName", recName);
        spEditor.putString("ingredients", ingredients);
        spEditor.apply();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        ComponentName thisWidget = new ComponentName(context, BakingWidgetProvider.class);
        remoteViews.setTextViewText(R.id.appwidget_title, recName);
        remoteViews.setTextViewText(R.id.appwidget_text, ingredients);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }


}
