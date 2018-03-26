package com.example.mac.movieappbasic;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.movieappbasic.Model.Movie;
import com.example.mac.movieappbasic.Moviedata.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by mac on 8/02/18.
 */

public class MovieDetail extends AppCompatActivity {

    private static final String TAG = MovieDetail.class.getSimpleName();
    ImageView imageDetail;
    TextView titleDetail;
    TextView releaseDate;
    TextView ratingDetail;
    TextView overview;
    Button favButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);

        Intent intent = getIntent();

        final Movie movie = getIntent().getExtras().getParcelable("MOVIE_OBJECT");


        String movieName = movie.getMovieName();
        Log.e(TAG,"This is the movie name:--------->");
        String poster = movie.getPoster_path();
        int id=movie.getMovie_ID();
        Log.e(TAG, "This is the movie ID:----------------------------->");
        Double voteAverage = movie.getVoteAverage();
        String overview1 = movie.getOverview();
        String releaseDate1 = movie.getReleaseDate();


        imageDetail = findViewById(R.id.imageMovie_detail_activity);

        Picasso.with(this).load(poster).into(imageDetail);

        titleDetail = findViewById(R.id.text_movie_name);
        titleDetail.setText(movieName);

        releaseDate = findViewById(R.id.text_movie_release_date);
        releaseDate.setText(releaseDate1);

        ratingDetail = findViewById(R.id.text_movie_rating);
        ratingDetail.setText(String.valueOf(voteAverage));

        overview = findViewById(R.id.text_movie_overview);
        overview.setText(overview1);

        favButton=findViewById(R.id.favorite_add_button);


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(favButton.isActivated()) {
                    String movieName = movie.getMovieName();
                    Log.e(TAG, "This is the movie name:--------->");
                    String poster = movie.getPoster_path();
                    int id = movie.getMovie_ID();
                    Log.e(TAG, "This is the movie ID:----------------------------->");
                    Double voteAverage = movie.getVoteAverage();
                    String overview1 = movie.getOverview();
                    String releaseDate1 = movie.getReleaseDate();

                    ContentValues favoriteContent = new ContentValues();

                    favoriteContent.put(MovieContract.MovieEntry.MOVIE_NAME, movieName);
                    favoriteContent.put(String.valueOf(MovieContract.MovieEntry.MOVIE_IMAGE), poster);
                    favoriteContent.put(String.valueOf(MovieContract.MovieEntry.RATING), voteAverage);
                    favoriteContent.put(MovieContract.MovieEntry.OVERVIEW, overview1);
                    favoriteContent.put(MovieContract.MovieEntry.RELEASE_DATE, releaseDate1);

                    getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, favoriteContent);

                    Toast.makeText(getBaseContext(), "Movie successfully added to favorites", Toast.LENGTH_LONG).show();
                }else{
                    
                }


            }
        });


    }
}
