package com.ratik.popularmovies.data;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ratik on 31/01/16.
 */
public class Movie {
    private static final String TAG = Movie.class.getSimpleName();

    private String title;
    private String releaseDate;
    private String poster;
    private String voteAverage;
    private String plot;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressLint("SimpleDateFormat")
    public String getReleaseDate() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(releaseDate);
        } catch (ParseException e) {
            Log.e(TAG, "Exception caught: ", e);
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM yyyy");
        return fmtOut.format(date);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        String basePath = "http://image.tmdb.org/t/p";
        String posterWidth = "/w185";
        return basePath + posterWidth + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
