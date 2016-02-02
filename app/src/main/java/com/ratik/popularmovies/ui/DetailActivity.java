package com.ratik.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ratik.popularmovies.R;
import com.ratik.popularmovies.data.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Ratik on 02/02/16.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(MainActivity.MOVIE_DATA);

        // Set the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(movie.getTitle());
        }

        // Set up views
        final ProgressBar mainProgressBar = (ProgressBar) findViewById(R.id.mainProgressBar);
        ImageView posterImageView = (ImageView) findViewById(R.id.posterImageView);
        final TextView overviewTextView = (TextView) findViewById(R.id.overviewTextView);
        TextView releaseDateTextView = (TextView) findViewById(R.id.releaseDateTextView);
        TextView voteAverageTextView = (TextView) findViewById(R.id.voteAverageTextView);

        // Fill in data
        Picasso.with(this).load(movie.getPoster()).into(posterImageView, new Callback() {
            @Override
            public void onSuccess() {
                mainProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                // Unimplemented
            }
        });
        overviewTextView.setText(movie.getPlot());
        releaseDateTextView.setText(movie.getReleaseDate());
        voteAverageTextView.setText(String.format(getString(R.string.vote_average_placeholder),
                movie.getVoteAverage()));
    }
}
