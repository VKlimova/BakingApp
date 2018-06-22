package com.amargodigits.bakingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.amargodigits.bakingapp.model.Step;
import java.util.ArrayList;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;
/**
 * StepListAdapter is backed by an ArrayList of {@link Step} objects which populate
 * the GridView
 */
public class StepListAdapter extends ArrayAdapter<Step> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();
    public StepListAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView recTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder holder = null;
            Step currentRec = getItem(position);
            if (convertView == null) {
                // If it's not recycled, initialize some attributes
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                convertView = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.recTitle = (TextView) convertView.findViewById(R.id.step_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.recTitle.setText(currentRec.getShortDescription());
        }
        catch (Exception e) {
            Log.i(LOG_TAG, "Step List Adapter Exception "+ e.toString());
            throw new RuntimeException(e);
        }
        return convertView;
    }
}