package com.example.mac.movieappbasic;

import android.content.AsyncTaskLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mac.movieappbasic.Moviedata.MovieContract;
import com.example.mac.movieappbasic.Moviedata.MovieDbHelper;

/**
 * Created by mac on 22/03/18.
 */

public class Favorites extends AppCompatActivity {


    MovieDbHelper mDbHelper;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_layout);



    }
}
