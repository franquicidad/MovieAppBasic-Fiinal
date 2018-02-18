package com.example.mac.movieappbasic;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;

import com.example.mac.movieappbasic.JsonUtils.JsonParsingMovie;
import com.example.mac.movieappbasic.Model.Movie;

import java.util.ArrayList;

/**
 * Created by mac on 8/02/18.
 */

public class MovieAsynctaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private String mUrl;

    public MovieAsynctaskLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        ArrayList<Movie> listMovie = null;
        listMovie = JsonParsingMovie.fetchMovieAppData(mUrl);
        return listMovie;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
