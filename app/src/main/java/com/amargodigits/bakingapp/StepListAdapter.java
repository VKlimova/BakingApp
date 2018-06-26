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
            Log.i(LOG_TAG, "Step list adapter onCreateViewHolder");
            TextView v = null;
            try {
                v = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recipe_item_layout, parent, false);
            }
            catch (Exception e){
                Log.i(LOG_TAG, "Step list adapter onCreateViewHolder exception:" + e.toString());
            }
            return new StepListAdapter.ViewHolder(v);
        }


    // Provide a suitable constructor (depends on the kind of dataset)
        public StepListAdapter(Context myContext, ArrayList<Step> myDataset) {
            Log.i(LOG_TAG, "Step list adapter constructor");
            mStepList = myDataset;
            mContext = myContext;
        }
        @Override
        public void onBindViewHolder(StepListAdapter.ViewHolder holder, int position) {
//        = mRecipeList.get(holder.getAdapterPosition()).toString();


            final Step item = (Step) mStepList.get(holder.getAdapterPosition());
            String text = item.getShortDescription();
            Log.i(LOG_TAG, "Step list adapter onBindViewHolder position=" + position + " text=" + text);

            holder.recTitle.setText(text);

            holder.recTitle.setOnClickListener(new AdapterView.OnClickListener()
                                               { @Override
                                               public void onClick(View view) {
                                                   final ListFragment.OnStepListener rStepListener;
                                                   rStepListener = (ListFragment.OnStepListener) mContext;
                String stepDescr = item.getDescription();
                String stepThumbUrl = item.getThumbnailUrl();
                String stepVideoUrl = item.getVideoUrl();
                Log.i(LOG_TAG, "StepListAdapter, stepDescr : " + stepDescr);
//
                try {
                    rStepListener.onStep(stepDescr, stepVideoUrl, stepThumbUrl);
                } catch (Exception e) {
                    Log.i(LOG_TAG, "ListFragment mStepListener.onStep(stepDescr, stepVideoUrl, stepThumbUrl) Exception:\n "
                            + e.toString());
                    throw new RuntimeException(e);
                }

//                                                   String stepId = item.getId();
//                                                   String stepName = item.getShortDescription();
//

//                                                   Intent intent = new Intent(mContext, RecipeActivity.class);
//                                                   intent.putExtra("stepId", stepId);
//                                                   intent.putExtra("stepName", stepName);
//                                                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                   try {
//                                                       mContext.startActivity(intent);
//                                                   } catch (Exception e) {
//                                                       Log.i(LOG_TAG, "Opening recipe details exception: " + e.toString());
//                                                       throw new RuntimeException(e);
//                                                   }
                                               }
                                               }


            );

        }

        @Override
        public int getItemCount() {
            int size = mStepList.size();
//        Log.i(LOG_TAG, "Baking list adapter getItemCount = " + size );
            return size;
        }

// Provide a reference to the views for each data item
public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView recTitle;

    ViewHolder(TextView v) {
        super(v);
        Log.i(LOG_TAG, "Step list adapter ViewHolder");
        recTitle = v;
    }
}
//        ArrayAdapter<Step> {
//    private Context mContext;
//    private int layoutResourceId;
//    private ArrayList data = new ArrayList();
//    public StepListAdapter(Context context, int layoutResourceId, ArrayList data) {
//        super(context, layoutResourceId, data);
//        this.layoutResourceId = layoutResourceId;
//        this.mContext = context;
//        this.data = data;
//    }
//
//    static class ViewHolder {
//        TextView recTitle;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        try {
//            ViewHolder holder = null;
//            Step currentRec = getItem(position);
//            if (convertView == null) {
//                // If it's not recycled, initialize some attributes
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//                convertView = inflater.inflate(layoutResourceId, parent, false);
//                holder = new ViewHolder();
//                holder.recTitle = (TextView) convertView.findViewById(R.id.step_name);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            holder.recTitle.setText(currentRec.getShortDescription());
//        }
//        catch (Exception e) {
//            Log.i(LOG_TAG, "Step List Adapter Exception "+ e.toString());
//            throw new RuntimeException(e);
//        }
//        return convertView;
//    }
}