package com.amargodigits.bakingapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Recipe;
import com.amargodigits.bakingapp.model.Step;

import java.util.ArrayList;

import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;


public class Json {
    /**
     * takes as the input raw Json string, fills in the recipieList[] array
     *
     * @param rawJsonStr - raw string with JSON data
     */
    public static ArrayList<Recipe> getRecipeListStringsFromJson(String rawJsonStr)
            throws JSONException {
  //      Log.i(LOG_TAG, "rawJsonStr=" + rawJsonStr);
        ArrayList<Recipe> recipeList = new ArrayList<>();

        try {
            JSONArray recipeJsonArr = new JSONArray(rawJsonStr);
            for (int i = 0; (i < recipeJsonArr.length()); i++) {
                /* Get the JSON object representing the movie */
                JSONObject recipeObj = recipeJsonArr.getJSONObject(i);
                recipeList.add(new Recipe(
                                recipeObj.getString("id"),
                                recipeObj.getString("name"),
                                recipeObj.getString("servings"),
                                recipeObj.getString("image")
                        )
                );
  //              Log.i("baking log", "importing " + recipeObj.getString("name"));
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Recipe:" + e.toString());
        }
        return recipeList;
    }


    /**
     * takes as the input raw Json string, fills in the ingredientList[] array
     *
     * @param rawJsonStr - raw string with JSON data
     */
    public static ArrayList<Ingredient> getIngredientListStringsFromJson(String rawJsonStr, String recipeId)
            throws JSONException {
        Log.i(LOG_TAG, "Importing ingredients...");
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        // find Recipe and add it's steps
        try {
            JSONArray recipeJsonArr = new JSONArray(rawJsonStr);
            for (int i = 0; (i < recipeJsonArr.length()); i++) {
                JSONObject recipeObj = recipeJsonArr.getJSONObject(i);
                if (recipeObj.getString("id").equals(recipeId)){
                    Log.i(LOG_TAG, "GOTCHA!!! " + recipeObj.getString("name"));
        try {
            JSONArray ingredientJsonArr = new JSONArray(recipeObj.getString("ingredients"));
            for (int j = 0; (j < ingredientJsonArr.length()); j++) {
                /* Get the JSON object representing the Ingredients */
                JSONObject ingredientObj = ingredientJsonArr.getJSONObject(j);
                ingredientList.add(new Ingredient(j+"",
                        recipeId,
                        ingredientObj.getString("quantity"),
                        ingredientObj.getString("measure"),
                        ingredientObj.getString("ingredient")
                        )
                );
                Log.i("baking log", "importing " + j + ". " + ingredientObj.getString("ingredient"));
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Ingredient:" + e.toString());
        }
                }
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Recipe:" + e.toString());
        }
        return ingredientList;
    }

    /**
     * takes as the input raw Json string, fills in the ingredientList[] array
     *
     * @param rawJsonStr - raw string with JSON data
     */
    public static ArrayList<Step> getStepListStringsFromJson(String rawJsonStr, String recipeId)
            throws JSONException {
        Log.i(LOG_TAG, "Starting getStepListStringsFromJson, id = " + recipeId);
        ArrayList<Step> stepList = new ArrayList<>();

        // find Recipe and add it's steps
        try {
            JSONArray recipeJsonArr = new JSONArray(rawJsonStr);
            for (int i = 0; (i < recipeJsonArr.length()); i++) {
                JSONObject recipeObj = recipeJsonArr.getJSONObject(i);
                if (recipeObj.getString("id").equals(recipeId)){
                    Log.i(LOG_TAG, "GOTCHA!!! " + recipeObj.getString("name"));
                    try {
                        JSONArray stepJsonArr = new JSONArray(recipeObj.getString("steps"));
                        for (int j = 0; (j < stepJsonArr.length()); j++) {
                            JSONObject stepObj = stepJsonArr.getJSONObject(j);
                            Log.i("baking log", "importing " + j + ". " + stepObj.getString("shortDescription"));
                            stepList.add(new Step(j+"",
                                            recipeId,
                                            stepObj.getString("shortDescription"),
                                            stepObj.getString("description"),
                                            stepObj.getString("videoURL"),
                                            stepObj.getString("thumbnailURL")
                                    )
                            );
                        }
                    } catch (Exception e) {
                        Log.i(LOG_TAG, "Catched JSon exception parcing Step:" + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Recipe:" + e.toString());
        }
       // eof find Recipe
        return stepList;
    }
}


