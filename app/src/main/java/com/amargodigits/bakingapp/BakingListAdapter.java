
package com.amargodigits.bakingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.amargodigits.bakingapp.model.Recipe;
import java.util.ArrayList;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;

/**
 * BakingListAdapter is backed by an ArrayList of {@link Recipe} objects which populate
 * the GridView in MenuActivity
 */

public class BakingListAdapter extends ArrayAdapter<Recipe> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public BakingListAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView recTitle;
    }

    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            ViewHolder holder = null;
            Recipe currentRec = getItem(position);

            if (convertView == null) {
                // If it's not recycled, initialize some attributes
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );;
                convertView = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.recTitle = (TextView) convertView.findViewById(R.id.recipe_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.recTitle.setText(currentRec.getName());
        }
        catch (Exception e) {
            Log.i(LOG_TAG, "Baking List Adapter Exception "+ e.toString());
            throw new RuntimeException(e);
        }
        return convertView;
    }
}