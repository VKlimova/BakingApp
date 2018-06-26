
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private int layoutResourceId;
    private ArrayList mRecipeList = new ArrayList();

    @Override
    public BakingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(LOG_TAG, "Baking list adapter onCreateViewHolder");
        TextView v = null;
        try {
             v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item_layout, parent, false);
        }
        catch (Exception e){
            Log.i(LOG_TAG, "Baking list adapter onCreateViewHolder exception:" + e.toString());
        }
        return new ViewHolder(v);
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public BakingListAdapter(Context myContext, ArrayList<Recipe> myDataset) {
        Log.i(LOG_TAG, "Baking list adapter constructor");
        mRecipeList = myDataset;
        mContext = myContext;
    }
    @Override
    public void onBindViewHolder(BakingListAdapter.ViewHolder holder, int position) {
//        = mRecipeList.get(holder.getAdapterPosition()).toString();
        final Recipe item = (Recipe) mRecipeList.get(holder.getAdapterPosition());
        String text = item.getName();
        Log.i(LOG_TAG, "Baking list adapter onBindViewHolder position=" + position + " text=" + text);

        holder.recTitle.setText(text);

holder.recTitle.setOnClickListener(new AdapterView.OnClickListener()
        { @Override
        public void onClick(View view) {

//                            Recipe item = (Recipe) adapterView.getItemAtPosition(position);
                String recId = item.getId();
                String recName = item.getName();
                Log.i(LOG_TAG, "BakingListAdapter, recId : " + recId);
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
//        Log.i(LOG_TAG, "Baking list adapter getItemCount = " + size );
        return size;
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recTitle;

        ViewHolder(TextView v) {
            super(v);
            Log.i(LOG_TAG, "Baking list adapter ViewHolder");
            recTitle = v;
        }
    }


//    public BakingListAdapter(Context context, int layoutResourceId, ArrayList data) {
//        super(context, layoutResourceId, data);
//        this.layoutResourceId = layoutResourceId;
//        this.mContext = context;
//        this.data = data;
//    }




//    @Override
//    // Create a new View for each item referenced by the Adapter
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        try {
//            ViewHolder holder = null;
//            Recipe currentRec = getItem(position);
//
//            if (convertView == null) {
//                // If it's not recycled, initialize some attributes
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//                convertView = inflater.inflate(layoutResourceId, parent, false);
//                holder = new ViewHolder();
//                holder.recTitle = (TextView) convertView.findViewById(R.id.recipe_name);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            holder.recTitle.setText(currentRec.getName());
//        }
//        catch (Exception e) {
//            Log.i(LOG_TAG, "Baking List Adapter Exception "+ e.toString());
//            throw new RuntimeException(e);
//        }
//        return convertView;
//    }


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