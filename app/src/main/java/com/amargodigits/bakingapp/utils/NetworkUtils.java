package com.amargodigits.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.amargodigits.bakingapp.R;
import com.amargodigits.bakingapp.model.Ingredient;
import com.amargodigits.bakingapp.model.Recipe;
import com.amargodigits.bakingapp.model.Step;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import static com.amargodigits.bakingapp.MainActivity.LOG_TAG;
import static com.amargodigits.bakingapp.MainActivity.doGridView;
import static com.amargodigits.bakingapp.MainActivity.mRecipeList;
import static com.amargodigits.bakingapp.ListFragment.doIngredientView;
import static com.amargodigits.bakingapp.ListFragment.mIngredientList;
import static com.amargodigits.bakingapp.ListFragment.mStepList;
import static com.amargodigits.bakingapp.ListFragment.doStepView;

public class NetworkUtils {
    /**
     * Builds the URL used to get Recipies list from the server.
     */
    public static URL buildRecipiesUrl() {
        Uri builtUri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json").buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * This method creates AsyncTask to make a Network request in background
     * To load recipies list
     */
    public static class LoadRecipiesTask extends AsyncTask<Integer, Void, ArrayList<Recipe>> {
        Context mContext;
        public LoadRecipiesTask(Context context) {
            mContext = context;
        }
        /**
         * This method make a Network request in background
         * Load recipes list
         * @return ArrayList<Recipe>
         */
        @Override
        protected ArrayList<Recipe> doInBackground(Integer... params) {
            String jsonResponse="";
            if (isOnline(mContext)) {
                try {
                    URL scheduleRequestUrl = NetworkUtils.buildRecipiesUrl();
                    String recipeResponse = NetworkUtils
                            .getResponseFromHttpUrl(scheduleRequestUrl);
                    try {

                         jsonResponse = recipeResponse;
                    } catch (Exception e)
                    {
                        Log.i(LOG_TAG, "LoadRecipiesTask doInBackground Exception " + e.toString());
                    }
                    ArrayList<Recipe> recipeList = new ArrayList<>();
                    recipeList= Json.getRecipeListStringsFromJson(jsonResponse);
                    return recipeList;
                } catch (Exception e) {
                    Log.i(LOG_TAG, R.string.error_message + e.toString());
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Recipe> result) {
            super.onPostExecute(result);
            mRecipeList = result;
            doGridView(mContext);
        }
    }

    /**
     * This method creates AsyncTask to make a Network request in background
     * To load step list
     */
    public static class LoadStepTask extends AsyncTask<String, Void, ArrayList<Step>> {
        Context mContext;
        public LoadStepTask(Context context) {
            mContext = context;
        }
        /**
         * This method make a Network request in background
         * Load reviews list to reviewList
         * @return Review[] -  the reviews  array
         */
        @Override
        protected ArrayList<Step> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
                }
                String recId = params[0];
            if (isOnline(mContext)) {
                try {
                    URL scheduleRequestUrl = NetworkUtils.buildRecipiesUrl();
                    String stepResponse = NetworkUtils
                            .getResponseFromHttpUrl(scheduleRequestUrl);
//                    Log.i(LOG_TAG, "recipeResponse " +recipeResponse );
                    ArrayList<Step> stepList = new ArrayList<>();
                    stepList= Json.getStepListStringsFromJson(stepResponse,recId);
                    return stepList;
                } catch (Exception e) {
                    Log.i(LOG_TAG, R.string.error_message + e.toString());
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Step> result) {
            super.onPostExecute(result);
            mStepList = result;
            doStepView(mContext);
        }
    }

    /**
     * This method creates AsyncTask to make a Network request in background
     * To load step list
     */
    public static class LoadIngredientTask extends AsyncTask<String, Void, ArrayList<Ingredient>> {
        Context mContext;
        public LoadIngredientTask(Context context) {
            mContext = context;
        }
        /**
         * This method make a Network request in background
         * Load ingredient list to
         * @return Ingredient[] -  the ingredient  array
         */
        @Override
        protected ArrayList<Ingredient> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String recId = params[0];
            if (isOnline(mContext)) {
                try {
                    URL scheduleRequestUrl = NetworkUtils.buildRecipiesUrl();
                    String jsonResponse = NetworkUtils
                            .getResponseFromHttpUrl(scheduleRequestUrl);
                    ArrayList<Ingredient> ingredientsList = new ArrayList<>();
                    ingredientsList=Json.getIngredientListStringsFromJson(jsonResponse,recId);
                    return ingredientsList;
                } catch (Exception e) {
                    Log.i(LOG_TAG, R.string.error_message + e.toString());
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Ingredient> result) {
            super.onPostExecute(result);
            mIngredientList = result;
            doIngredientView(mContext);
        }
    }

    /**
     * Checks if the device has network connection
     * @param tContext - context variable
     * @return true if the device is connected to network, otherwise returns false
     */
    public static boolean isOnline(Context tContext) {
        ConnectivityManager cm =
                (ConnectivityManager) tContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
