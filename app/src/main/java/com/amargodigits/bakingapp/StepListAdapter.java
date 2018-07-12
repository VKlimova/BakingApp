package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

//import com.amargodigits.bakingapp.model.Recipe;
import com.amargodigits.bakingapp.model.Step;

import java.util.ArrayList;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/**
 * StepListAdapter is backed by an ArrayList of {@link Step} objects which populate
 * the GridView
 */
public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList mStepList = new ArrayList();

    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = null;
        try {
            v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_item_layout, parent, false);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Step list adapter onCreateViewHolder exception:" + e.toString());
        }
        return new StepListAdapter.ViewHolder(v);
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public StepListAdapter(Context myContext, ArrayList<Step> myDataset) {
        mStepList = myDataset;
        mContext = myContext;
    }

    @Override
    public void onBindViewHolder(StepListAdapter.ViewHolder holder, int position) {
        final Step item = (Step) mStepList.get(holder.getAdapterPosition());
        String text = item.getShortDescription();
        holder.recTitle.setText(text);
        holder.recTitle.setOnClickListener(new AdapterView.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   final ListFragment.OnStepListener rStepListener;
                                                   rStepListener = (ListFragment.OnStepListener) mContext;
                                                   String stepDescr = item.getDescription();
                                                   String stepThumbUrl = item.getThumbnailUrl();
                                                   String stepVideoUrl = item.getVideoUrl();
                                                   String stepId = item.getId();
                                                   try {
                                                       rStepListener.onStep(stepDescr, stepVideoUrl, stepThumbUrl, stepId);
                                                   } catch (Exception e) {
                                                       Log.i(LOG_TAG, "ListFragment mStepListener.onStep(stepDescr, stepVideoUrl, stepThumbUrl) Exception:\n "
                                                               + e.toString());
                                                       throw new RuntimeException(e);
                                                   }
                                               }
                                           }
        );
    }

    @Override
    public int getItemCount() {
        int size = mStepList.size();
        return size;
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recTitle;

        ViewHolder(TextView v) {
            super(v);
            recTitle = v;
        }
    }
}