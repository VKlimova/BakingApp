package com.amargodigits.bakingapp.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.amargodigits.bakingapp.RecipeActivity;
import com.amargodigits.bakingapp.StepActivity;
import com.amargodigits.bakingapp.StepFragment;
import com.amargodigits.bakingapp.model.Recipe;
import com.amargodigits.bakingapp.model.Step;
import java.util.ListIterator;

import static com.amargodigits.bakingapp.ListFragment.mStepList;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;
import static com.amargodigits.bakingapp.MainActivity.mRecipeList;

public class NavigationUtils {

    public static void onClickNextRecipe (Context mContext, String recId){
        int intRecId;
        try {
        intRecId = (Integer) Integer.parseInt(recId);
        ListIterator<Recipe> it = mRecipeList.listIterator(intRecId);
        Recipe item;
        if (it.hasNext()) {
            item = it.next();
                String nextRecId = item.getId();
                String nextRecName = item.getName();
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("recId", nextRecId);
                intent.putExtra("recName", nextRecName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Log.i(LOG_TAG, "Opening next recipe exception: " + e.toString());
                    throw new RuntimeException(e);
                }
        }
        }
        catch (Exception e) {
            Log.i(LOG_TAG, "NavigationUtils onClickNextRecipe Exception " + e.toString());
        }
        return ;
    }

    public static void onClickPrevRecipe (Context mContext, String recId){
        int intRecId;
        intRecId = (Integer) Integer.parseInt(recId);
        ListIterator<Recipe> it = mRecipeList.listIterator(intRecId-1);
        Recipe item;
        if (it.hasPrevious()) {
            item= it.previous();
            String prevRecId = item.getId();
            String prevRecName = item.getName();
            Intent intent = new Intent(mContext, RecipeActivity.class);
            intent.putExtra("recId", prevRecId);
            intent.putExtra("recName", prevRecName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
                Log.i(LOG_TAG, "Opening recipe details exception: " + e.toString());
                throw new RuntimeException(e);
            }
        }
        return ;
    }

// For recipe Steps

    public static void onClickNextStep (Context mContext, String recName, String stepId){
        int intStepId;
        try {
            intStepId = (Integer) Integer.parseInt(stepId);
            // incrementing intStepId because steps numbering is zero-based
            intStepId = intStepId + 1;
            ListIterator<Step> it = mStepList.listIterator(intStepId);
            Step item;
            if (it.hasNext()) {
                item = it.next();
                String nextStepId = item.getId();
                String nextStepDescr = item.getDescription();
                String stepThumbUrl = item.getThumbnailUrl();
                String stepVideoUrl = item.getVideoUrl();
                Intent intent = new Intent(mContext, StepActivity.class);
                intent.putExtra("stepId", nextStepId);
                intent.putExtra("stepDescr", nextStepDescr);
                intent.putExtra("recName", recName);
                intent.putExtra("stepThumbUrl", stepThumbUrl);
                intent.putExtra("stepVideoUrl", stepVideoUrl);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Log.i(LOG_TAG, "Opening stepId exception: " + e.toString());
                    throw new RuntimeException(e);
                }
            }
        }
        catch (Exception e) {
            Log.i(LOG_TAG, "NavigationUtils onClickNextStep Exception " + e.toString());
        }
        return ;
    }

    public static void onClickPrevStep (Context mContext, String recName, String stepId){
        int intStepId;
        intStepId = (Integer) Integer.parseInt(stepId);
        ListIterator<Step> it = mStepList.listIterator(intStepId);
        Step item;
        if (it.hasPrevious()) {
            item= it.previous();
            String prevStepId = item.getId();
            String prevStepDescr = item.getDescription();
            String stepThumbUrl = item.getThumbnailUrl();
            String stepVideoUrl = item.getVideoUrl();
            Intent intent = new Intent(mContext, StepActivity.class);
            intent.putExtra("stepId", prevStepId);
            intent.putExtra("stepDescr", prevStepDescr);
            intent.putExtra("recName", recName);
            intent.putExtra("stepThumbUrl", stepThumbUrl);
            intent.putExtra("stepVideoUrl", stepVideoUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
                Log.i(LOG_TAG, "Opening recipe details exception: " + e.toString());
                throw new RuntimeException(e);
            }
        }
        return ;
    }
}
