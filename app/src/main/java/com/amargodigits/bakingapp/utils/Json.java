package com.amargodigits.bakingapp.utils;

import android.util.Log;
import org.json.JSONArray;
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
            throws RuntimeException {
        ArrayList<Recipe> recipeList = new ArrayList<>();

        try {
            JSONArray recipeJsonArr = new JSONArray(rawJsonStr);
            for (int i = 0; (i < recipeJsonArr.length()); i++) {
                /* Get the JSON object representing the recipe */
                JSONObject recipeObj = recipeJsonArr.getJSONObject(i);
                recipeList.add(new Recipe(
                                recipeObj.getString("id"),
                                recipeObj.getString("name"),
                                recipeObj.getString("servings"),
                                recipeObj.getString("image")
                        )
                );
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Recipe:" + e.toString());
            throw new RuntimeException(e);
        }
        return recipeList;
    }

    /**
     * takes as the input raw Json string, fills in the ingredientList[] array
     *
     * @param rawJsonStr - raw string with JSON data
     */
    public static ArrayList<Ingredient> getIngredientListStringsFromJson(String rawJsonStr, String recipeId)
            throws RuntimeException {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        // find Recipe and add it's steps
        try {
            JSONArray recipeJsonArr = new JSONArray(rawJsonStr);
            for (int i = 0; (i < recipeJsonArr.length()); i++) {
                JSONObject recipeObj = recipeJsonArr.getJSONObject(i);
                if (recipeObj.getString("id").equals(recipeId)){
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
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Ingredient:" + e.toString());
            throw new RuntimeException(e);
        }
                }
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Recipe:" + e.toString());
            throw new RuntimeException(e);
        }
        return ingredientList;
    }

    /**
     * takes as the input raw Json string, fills in the ingredientList[] array
     *
     * @param rawJsonStr - raw string with JSON data
     */
    public static ArrayList<Step> getStepListStringsFromJson(String rawJsonStr, String recipeId)
            throws RuntimeException {
        ArrayList<Step> stepList = new ArrayList<>();
        // find Recipe and add it's steps
        try {
            JSONArray recipeJsonArr = new JSONArray(rawJsonStr);
            for (int i = 0; (i < recipeJsonArr.length()); i++) {
                JSONObject recipeObj = recipeJsonArr.getJSONObject(i);
                if (recipeObj.getString("id").equals(recipeId)){
                    try {
                        JSONArray stepJsonArr = new JSONArray(recipeObj.getString("steps"));
                        for (int j = 0; (j < stepJsonArr.length()); j++) {
                            JSONObject stepObj = stepJsonArr.getJSONObject(j);
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
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Catched JSon exception parcing Recipe:" + e.toString());
            throw new RuntimeException(e);
        }
       // eof find Recipe
        return stepList;
    }
}


