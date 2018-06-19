/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.amargodigits.bakingapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amargodigits.bakingapp.model.Recipe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
//        ImageView image;
    }

    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.i(LOG_TAG, "currentRec.getName() = " + position );
        try {
            ViewHolder holder = null;
            Recipe currentRec = getItem(position);

            if (convertView == null) {
                // If it's not recycled, initialize some attributes
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );;
                convertView = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.recTitle = (TextView) convertView.findViewById(R.id.recipe_name);
//            holder.image = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.recTitle.setText(currentRec.getName());
        }
        catch (Exception e)
        {Log.i(LOG_TAG, "Baking List Adapter Exception "+ e.toString());}

        return convertView;
    }

}