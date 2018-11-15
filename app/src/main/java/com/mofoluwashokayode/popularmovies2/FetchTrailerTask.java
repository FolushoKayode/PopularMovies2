package com.mofoluwashokayode.popularmovies2;

import android.net.Uri;
import android.os.AsyncTask;

import com.mofoluwashokayode.popularmovies2.callbacks.TrailerCallback;
import com.mofoluwashokayode.popularmovies2.movie_model.Trailer;

import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchTrailerTask extends AsyncTask<Integer, Void, Trailer[]> {

    private final TrailerCallback trailerTaskCallback;

    public FetchTrailerTask(TrailerCallback trailerTaskCallback) {
        this.trailerTaskCallback = trailerTaskCallback;
    }

    @Override
    protected Trailer[] doInBackground(Integer... integers) {
        if (integers.length == 0) {
            return null;
        }

        final String BASE_URL = "https://api.themoviedb.org/3/movie/";
        final String TYPE = "videos";
        final String API_KEY = "api_key";

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(integers[0]))
                .appendEncodedPath(TYPE)
                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIEDB_API_KEY)
                .build();

        String jsonString = NetworkRequest.getJsonString(uri);

        try {
            return getTrailersFromJson(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Contract("null -> null")
    private Trailer[] getTrailersFromJson(String jsonString) throws JSONException {
        final String TRAILER_ID = "id";
        final String TRAILER_NAME = "name";
        final String TRAILER_KEY = "key";
        final String RESULT_ARRAY = "results";

        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULT_ARRAY);

        Trailer[] trailers = new Trailer[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            trailers[i] = new Trailer(
                    object.optString(TRAILER_ID),
                    object.getString(TRAILER_KEY),
                    object.getString(TRAILER_NAME)
            );
        }
        return trailers;
    }

    @Override
    protected void onPostExecute(Trailer[] trailers) {
        if (trailers != null) {
            trailerTaskCallback.updateAdapter(trailers);
        }
    }
}
