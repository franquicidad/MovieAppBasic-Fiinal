package com.example.mac.movieappbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.movieappbasic.Model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by mac on 8/02/18.
 */

public class MovieDetail extends AppCompatActivity {

    ImageView imageDetail;
    TextView titleDetail;
    TextView releaseDate;
    TextView ratingDetail;
    TextView overview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);

        Intent intent = getIntent();

        Movie movie = getIntent().getExtras().getParcelable("MOVIE_OBJECT");


        String movieName = movie.getMovieName();
        String poster = movie.getPoster_path();
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


    }
}
