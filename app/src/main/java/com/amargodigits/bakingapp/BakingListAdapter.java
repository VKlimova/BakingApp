package com.amargodigits.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.amargodigits.bakingapp.model.Recipe;
import java.util.ArrayList;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/**
 * BakingListAdapter is backed by an ArrayList of {@link Recipe} objects which populate
 * the RecyclerView in MenuActivity
 */

public class BakingListAdapter extends RecyclerView.Adapter<BakingListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList mRecipeList = new ArrayList();

    @Override
    public BakingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = null;
        try {
            v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item_layout, parent, false);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Baking list adapter onCreateViewHolder exception:" + e.toString());
        }
        return new ViewHolder(v);
    }

    public BakingListAdapter(Context myContext, ArrayList<Recipe> myDataset) {
        mRecipeList = myDataset;
        mContext = myContext;
    }

    @Override
    public void onBindViewHolder(BakingListAdapter.ViewHolder holder, int position) {
        final Recipe item = (Recipe) mRecipeList.get(holder.getAdapterPosition());
        String text = item.getName();
        holder.recTitle.setText(text);
        holder.recTitle.setOnClickListener(new AdapterView.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   String recId = item.getId();
                                                   String recName = item.getName();
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
                                           }
        );
    }

    @Override
    public int getItemCount() {
        int size = mRecipeList.size();
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
    //
    // The example for this function is taken from stackOverflow
    // https://stackoverflow.com/a/38472370/8796408
    //
    public static int gridColumnsNumber(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }
}