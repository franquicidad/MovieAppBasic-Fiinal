package com.example.mac.movieappbasic;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.movieappbasic.JsonUtils.JsonParsingMovie;
import com.example.mac.movieappbasic.Model.Movie;
import com.example.mac.movieappbasic.Moviedata.MovieContract;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by mac on 8/02/18.
 */

public class MovieDetail extends AppCompatActivity {

    public static final String BASE ="https://api.themoviedb.org/3/movie/";
    public static final String VIDEO="videos?";

    private static final String TAG = MovieDetail.class.getSimpleName();
    ImageView imageDetail;
    TextView titleDetail;
    TextView releaseDate;
    TextView ratingDetail;
    TextView review;
    TextView overview;
    Button favButton;
    static String moviesReview;

    static String youTubeKey;

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

        review=findViewById(R.id.review);
        review.setText(moviesReview);

        overview = findViewById(R.id.text_movie_overview);
        overview.setText(overview1);

        favButton=findViewById(R.id.favorite_add_button);


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

            }
        });


    }

    private class MovieTrailerAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            if(strings.length <1 || strings[0] == null ){
                return null;
            }
            return fetchMovieTrailerData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    public static String extractTrailerData(String movieJson){
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        youTubeKey=null;
        try{

            JSONObject root= new JSONObject(movieJson);
            JSONArray jsonArray=root.getJSONArray("results");
            for(int i=0;i< jsonArray.length(); i++){
                JSONObject keyobject = jsonArray.getJSONObject(1);
                youTubeKey=keyobject.getString("key");

            }

        }catch (JSONException e){
            Log.e(TAG,"Throw an Exception in Extraction of the movie Data ");

        }

        return youTubeKey;

    }

    private static String extractReviewData(String movieJson){
        if (TextUtils.isEmpty(movieJson)){
            return null;
        }

        moviesReview=null;

        try{
            JSONObject root=new JSONObject(movieJson);
            JSONArray resultArr= root.getJSONArray("results");
            for(int i=0;i< resultArr.length();i++){
                JSONObject resObject =resultArr.getJSONObject(i);
                moviesReview=resObject.getString("content");

            }
        }catch (JSONException e){
            Log.e(TAG,"Throw an exception ");
        }

        return moviesReview;



    }
    public static String fetchMovieTrailerData(String requestUrl){
        URL url = JsonParsingMovie.makeUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = JsonParsingMovie.makeHttpRequest(url);

            Log.e("karim","result"+jsonResponse);

        } catch (IOException e) {
            Log.e("", "Throw an Exception in fetchMovieAppData", e);
        }
        return extractTrailerData(jsonResponse);


    }


}
