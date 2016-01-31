package com.ratik.popularmovies.network;

import android.util.Log;

import com.ratik.popularmovies.helpers.Constants;
import com.ratik.popularmovies.resources.Keys;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ratik on 31/01/16.
 */
public class ApiClient {

    // Constants
    private static final String TAG = ApiClient.class.getSimpleName();

    public static void getMovieData(String orderBy) {
        String url = Constants.BASE_URL;
        url += "?sort_by=";
        url += orderBy;
        url += "&";
        url += "api_key=";
        url += Keys.API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Preventing failure by taking steps prior to the
                // network call
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Log JSON response
                Log.v(TAG, response.body().string());
            }
        });
    }
}
