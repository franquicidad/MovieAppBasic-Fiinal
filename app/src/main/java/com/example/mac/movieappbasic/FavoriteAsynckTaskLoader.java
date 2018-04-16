package com.example.mac.movieappbasic;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import com.example.mac.movieappbasic.Model.Movie;
import com.example.mac.movieappbasic.Moviedata.MovieContract;

import java.util.ArrayList;

/**
 * Created by mac on 16/04/18.
 */

public class FavoriteAsynckTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {


    public FavoriteAsynckTaskLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        Cursor cursor= getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,new String[]{
                        MovieContract.MovieEntry._ID,
                        MovieContract.MovieEntry.MOVIE_IMAGE,
                        MovieContract.MovieEntry.MOVIE_NAME,
                        MovieContract.MovieEntry.OVERVIEW,
                        MovieContract.MovieEntry.RATING,
                        MovieContract.MovieEntry.RELEASE_DATE},
                null,
                null,
                null);
        startManagingCursor(cursor);

        while (cursor.moveToNext()){
            String movieId=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
            int movieImage=cursor.getString(Integer.parseInt(String.valueOf(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_IMAGE)));
            String movieName=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_NAME));
            String movieOverview=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.OVERVIEW));
            String movieRating=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RATING));
            String movieRelease=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RELEASE_DATE));


            Movie favMovie= new Movie(movieName,movieId,movieImage,movieRating,movieOverview,movieRelease);
        }
        return null;
    }
}
