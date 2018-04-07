package com.example.mac.movieappbasic;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mac.movieappbasic.Moviedata.MovieContract;

/**
 * Created by mac on 22/03/18.
 */

public class Favorites extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_layout);



        Cursor cursor= getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,new String[]{
                        MovieContract.MovieEntry._ID,
                        MovieContract.MovieEntry.MOVIE_IMAGE,
                        MovieContract.MovieEntry.MOVIE_NAME,
                        MovieContract.MovieEntry.OVERVIEW,
                        MovieContract.MovieEntry.RATING,
                        MovieContract.MovieEntry.RELEASE_DATE
                },
                null ,
                null ,
                null );
        startManagingCursor(cursor);


    }
}
