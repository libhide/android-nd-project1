package com.ratik.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ratik.popularmovies.R;
import com.ratik.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ratik on 01/02/16.
 */
public class MoviesGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movies;

    public MoviesGridAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.movies_grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.posterImageView = (ImageView) convertView.findViewById(R.id.posterImageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(movies.get(position).getPoster())
                .into(viewHolder.posterImageView);

        return convertView;
    }

    private static class ViewHolder {
        ImageView posterImageView;
    }
}
