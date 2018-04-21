package com.example.mac.movieappbasic;

import android.support.v4.content.AsyncTaskLoader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.mac.movieappbasic.Model.Movie;
import com.example.mac.movieappbasic.Moviedata.MovieContract;

import java.util.ArrayList;

/**
 * Created by mac on 16/04/18.
 */

public class FavoriteAsynckTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    Context mContext;

    ArrayList<Movie> favList=new ArrayList<>();

    public FavoriteAsynckTaskLoader(Context context) {
        super(context);
        mContext=context;
    }


    @Override
    public ArrayList<Movie> loadInBackground() {
        Cursor cursor= mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,new String[]{
                        MovieContract.MovieEntry._ID,
                        MovieContract.MovieEntry.MOVIE_IMAGE,
                        MovieContract.MovieEntry.MOVIE_NAME,
                        MovieContract.MovieEntry.OVERVIEW,
                        MovieContract.MovieEntry.RATING,
                        MovieContract.MovieEntry.RELEASE_DATE},
                null,
                null,
                null);


        while (cursor.moveToNext()){
            int movieId= Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID)));
            String movieImage=cursor.getString(Integer.parseInt(String.valueOf(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_IMAGE))));
            String movieName=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_NAME));
            String movieOverview=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.OVERVIEW));
            Double movieRating= Double.valueOf(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RATING)));
            String movieRelease=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RELEASE_DATE));


            Movie favMovie= new Movie(movieName,movieId,movieImage,movieRating,movieOverview,movieRelease);
            favList.add(favMovie);



        }



        return favList;
    }
}
