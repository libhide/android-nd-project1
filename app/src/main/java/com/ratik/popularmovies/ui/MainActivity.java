package com.ratik.popularmovies.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ratik.popularmovies.Keys;
import com.ratik.popularmovies.R;
import com.ratik.popularmovies.adapters.MoviesGridAdapter;
import com.ratik.popularmovies.adapters.SortOrderSpinnerAdapter;
import com.ratik.popularmovies.data.Movie;
import com.ratik.popularmovies.helpers.Constants;
import com.ratik.popularmovies.helpers.ErrorUtils;
import com.ratik.popularmovies.helpers.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    // Constants
    private static final String TAG = MainActivity.class.getSimpleName();

    // Views
    private GridView moviesView;
    private Spinner sortBySpinner;

    // Data
    private ArrayList<Movie> movies;
    private MoviesGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        // Toolbar + Spinner setup
        initToolbar();

        // Views init
        moviesView = (GridView) findViewById(R.id.moviesView);
        adapter = new MoviesGridAdapter(MainActivity.this, movies);
        moviesView.setAdapter(adapter);
        moviesView.setOnItemClickListener(this);

        // .

        // Check if network is available
        if (NetworkUtils.isNetworkAvailable(this)) {
            // YES, do the network call!
            sortBySpinner.setSelection(0);
        } else {
            // NO, show toast
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View spinnerContainer = LayoutInflater.from(this).inflate(R.layout.toolbar_spinner,
                toolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.addView(spinnerContainer, lp);

        SortOrderSpinnerAdapter spinnerAdapter = new SortOrderSpinnerAdapter(this);
        spinnerAdapter.addItems(Arrays.asList(getResources().getStringArray(R.array.order_by_values)));

        sortBySpinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        sortBySpinner.setAdapter(spinnerAdapter);

        sortBySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Popular movies
                fetchData(Constants.ORDER_BY_POPULARITY);
                break;
            case 1:
                // Top-rated movies
                fetchData(Constants.ORDER_BY_VOTES);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Not implemented
    }

    // Network code
    private void fetchData(String orderBy) {
        String url = Constants.BASE_URL;
        url += "?sort_by=" + orderBy;
        url += "&";
        url += "api_key=" + Keys.API_KEY;

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
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        getMovieData(jsonData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        ErrorUtils.showGenericError(MainActivity.this);
                    }
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Exception caught:", e);
                }
            }
        });
    }

    // Populates movies data object
    private void getMovieData(String jsonData) throws JSONException {
        movies.clear();
        JSONObject moviesObject = new JSONObject(jsonData);
        JSONArray moviesArray = moviesObject.getJSONArray("results");
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setTitle(movieObject.getString(Constants.MOVIE_TITLE));
            movie.setReleaseDate(movieObject.getString(Constants.MOVIE_RELEASE_DATE));
            movie.setPoster(movieObject.getString(Constants.MOVIE_POSTER));
            movie.setVoteAverage(movieObject.getString(Constants.MOVIE_VOTE_AVERAGE));
            movie.setPlot(movieObject.getString(Constants.MOVIE_PLOT));

            movies.add(movie);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}
