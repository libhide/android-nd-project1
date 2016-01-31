package com.ratik.popularmovies.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ratik.popularmovies.R;
import com.ratik.popularmovies.adapters.SortOrderSpinnerAdapter;
import com.ratik.popularmovies.helpers.Constants;
import com.ratik.popularmovies.helpers.NetworkUtils;
import com.ratik.popularmovies.network.ApiClient;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private Spinner sortBySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar + Spinner setup
        initToolbar();

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
                ApiClient.getMovieData(Constants.ORDER_BY_POPULARITY);
                break;
            case 1:
                // Top-rated movies
                ApiClient.getMovieData(Constants.ORDER_BY_VOTES);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Not implemented
    }
}
