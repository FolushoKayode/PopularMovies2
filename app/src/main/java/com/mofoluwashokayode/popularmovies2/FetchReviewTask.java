package com.mofoluwashokayode.popularmovies2;

import android.net.Uri;
import android.os.AsyncTask;

import com.mofoluwashokayode.popularmovies2.callbacks.ReviewCallBack;
import com.mofoluwashokayode.popularmovies2.movie_model.Review;

import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchReviewTask extends AsyncTask<Integer, Void, Review[]> {

    private final ReviewCallBack taskCallback;

    public FetchReviewTask(ReviewCallBack taskCallback) {
        this.taskCallback = taskCallback;
    }

    @Override
    protected Review[] doInBackground(Integer... integers) {
        if (integers.length == 0) {
            return null;
        }

        final String BASE_URL = "https://api.themoviedb.org/3/movie/";
        final String TYPE = "reviews";
        final String API_KEY = "api_key";

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(integers[0]))
                .appendEncodedPath(TYPE)
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIEDB_API_KEY)
                .build();

        String jsonString = NetworkRequest.getJsonString(uri);

        try {
            return getReviewsFromJson(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Contract("null -> null")
    private Review[] getReviewsFromJson(String jsonString) throws JSONException {
        final String REVIEW_ID = "id";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";
        final String TOTAL_PAGES = "total_pages";
        final String RESULT_ARRAY = "results";

        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        int pages = jsonObject.getInt(TOTAL_PAGES);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULT_ARRAY);
        Review[] reviews = new Review[jsonArray.length()];

        for (int i = 1; i <= pages; i++) {
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject object = jsonArray.getJSONObject(j);
                reviews[j] = new Review(
                        object.optString(REVIEW_ID),
                        object.getString(REVIEW_AUTHOR),
                        object.getString(REVIEW_CONTENT)
                );
            }
        }
        return reviews;
    }

    @Override
    protected void onPostExecute(Review[] reviews) {
        if (reviews != null) {
            taskCallback.updateAdapter(reviews);
        }
    }


}
