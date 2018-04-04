package com.example.mac.movieappbasic;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 8/02/18.
 */

public class MovieDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,TrailersAdapter.ItemClickListener {

    public static final String BASE = "https://api.themoviedb.org/3/movie/";
    public static final String VIDEO = "videos?";

    private static final String TAG = MovieDetail.class.getSimpleName();
    ImageView imageDetail;
    TextView titleDetail;
    TextView releaseDate;
    TextView ratingDetail;
    TextView review;
    TextView overview;
    Button favButton;
    RecyclerView TrailerRv;
    static String moviesReview;
    TrailersAdapter trailersAdapter;
    Movie selectedMovie=null;
    long movieDbId;
    private static final int DETAILS_LOADER_ID=10;
    SQLiteDatabase mDb;

    Movie movie;

    static String youTubeKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);

        Intent intent = getIntent();

        final Movie movie = getIntent().getExtras().getParcelable("MOVIE_OBJECT");


        String movieName = movie.getMovieName();
        Log.e(TAG, "This is the movie name:--------->");
        String poster = movie.getPoster_path();
        int id = movie.getMovie_ID();
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

        review = findViewById(R.id.review);
        review.setText(moviesReview);

        overview = findViewById(R.id.text_movie_overview);
        overview.setText(overview1);


        TrailerRv = (RecyclerView) findViewById(R.id.trailers_rv);


        TrailerRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));




                Toast.makeText(getBaseContext(), "Movie successfully added to favorites", Toast.LENGTH_LONG).show();




        makeTrailer(id);
        makeReview(id);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.detail_favorite,menu);

        final Movie movie = getIntent().getExtras().getParcelable("MOVIE_OBJECT");

        int movie_id=movie.getMovie_ID();



        Uri uri= MovieContract.MovieEntry.CONTENT_URI;
        String [] projection={MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.MOVIE_ID};
        String [] selection= {MovieContract.MovieEntry.MOVIE_ID+ "=?"};
        String[] selectionArgs={String.valueOf(movie_id)};


        Cursor cursor=getContentResolver().query( uri,projection, String.valueOf(selection),selectionArgs,null);

        int containerCursor=cursor.getCount();

        int cursorMovieId=cursor.getInt(Integer.parseInt(MovieContract.MovieEntry.MOVIE_ID));
        MenuItem favoriteIcon= menu.getItem(0);

        if(containerCursor == 0) {
            favoriteIcon.setIcon(R.drawable.ic_favorite_border);
        }else {
            favoriteIcon.setIcon(R.drawable.ic_favorite);
        }
        return true;



        }

//        MenuItem favoriteIcon=menu.getItem(0);
//        if(movieDbId != -1){
//            favoriteIcon.setIcon(R.drawable.ic_favorite);
//        }else{
//            favoriteIcon.setIcon(R.drawable.ic_favorite_border);
//        }

//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.favorite_heart:
                if(movieDbId != -1){
                    item.setIcon(R.drawable.ic_favorite_border);
                    removeMovie(movieDbId);
                    movieDbId= -1;
                }else{
                    item.setIcon(R.drawable.ic_favorite);
                    movieDbId=addNewMovie(selectedMovie);
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private long addNewMovie(Movie movie) {

        ContentValues favoriteContent = new ContentValues();

        favoriteContent.put(MovieContract.MovieEntry.MOVIE_NAME, movie.getMovieName());
        favoriteContent.put(MovieContract.MovieEntry.MOVIE_ID, movie.getMovie_ID());
        favoriteContent.put(String.valueOf(MovieContract.MovieEntry.MOVIE_IMAGE), movie.getPoster_path());
        favoriteContent.put(String.valueOf(MovieContract.MovieEntry.RATING), movie.getVoteAverage());
        favoriteContent.put(MovieContract.MovieEntry.OVERVIEW, movie.getOverview());
        favoriteContent.put(MovieContract.MovieEntry.RELEASE_DATE, movie.getReleaseDate());

        Uri uri=getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, favoriteContent);
        String id2=uri.getPathSegments().get(1);
        return Long.parseLong(id2);

    }

    private void removeMovie(long id) {
        String stringId= Long.toString(id);
        Uri uri= MovieContract.MovieEntry.CONTENT_URI;
        uri= uri.buildUpon().appendPath(stringId).build();

        getContentResolver().delete(uri,null,null);
    }

    public void makeTrailer(int id){
        URL trailerUrl=JsonParsingMovie.buildTrailerUrl(id);
        if(JsonParsingMovie.hasInternetAccess(this)){
            new MovieTrailerAsyncTask().execute(trailerUrl);
        }

    }

    public void makeReview(int id){
        URL reviewUrl= JsonParsingMovie.buildReviewUrl(id);
        if(JsonParsingMovie.hasInternetAccess(this)){
            new ReviewQueryTask().execute(reviewUrl);

        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<Cursor>(this) {

            Cursor mTaskData=null;

            @Override
            protected void onStartLoading() {
                if(mTaskData !=null){
                    deliverResult(mTaskData);
                }else{
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String[] movieId = {Integer.toString(selectedMovie.getMovie_ID())};
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            MovieContract.MovieEntry._ID + " = ?",
                            movieId,
                            null
                    );
                }catch (Exception e){
                    Log.e(TAG,"Failed to load data");
                    e.printStackTrace();
                    return null;
                }

            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        if(data !=null && data.getCount()>0){
            data.moveToFirst();
            movieDbId= data.getInt(data.getColumnIndex(MovieContract.MovieEntry._ID));
        }else{
            movieDbId= -1;
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Trailer selectedTrailer= trailersAdapter.getItem(position);

        Intent youTube= new Intent(Intent.ACTION_VIEW,Uri.parse(selectedTrailer.getTrailerPath()));
        startActivity(youTube);

    }

    private class MovieTrailerAsyncTask extends AsyncTask<URL, Void, List<Trailer>> {


        @Override
        protected List<Trailer> doInBackground(URL... urls) {

            URL queryUrl = urls[0];
            String trailerQueryResult = null;
            List<Trailer> returnedTrailers = null;
            try {
                trailerQueryResult = JsonParsingMovie.makeHttpRequest(queryUrl);
            } catch (IOException e) {
                Log.e(TAG, "IOException" + queryUrl);
            }

            try {
                returnedTrailers = fetchMovieTrailerData(trailerQueryResult);
            } catch (JSONException e) {
                Log.e(TAG, "JsonException");
            }
            return returnedTrailers;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            trailersAdapter = new TrailersAdapter(getApplicationContext(), trailers);
            TrailerRv.setAdapter(trailersAdapter);
            trailersAdapter.setClickListener(MovieDetail.this);


        }

    }

    public class ReviewQueryTask extends AsyncTask<URL,Void,List<Review>>{

        @Override
        protected List<Review> doInBackground(URL... urls) {

            URL queryUrl=urls[0];
            String reviewQueryResult=null;
            List<Review> returnedReviews=null;

            try{
                reviewQueryResult=JsonParsingMovie.makeHttpRequest(queryUrl);
            }catch (IOException e){
                Log.e(TAG, "IOException"+queryUrl);
            }

            try {
                returnedReviews=parseReviewsJSON(reviewQueryResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return returnedReviews;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            super.onPostExecute(reviews);
        }
    }


    public static List<Review> parseReviewsJSON(String string) throws JSONException{
        List<Review> finalReviewsList = new ArrayList<Review>();
        JSONObject result = new JSONObject(string);

        if(result.has("results") && result.getJSONArray("results").length() != 0){
            JSONArray reviewsList = result.getJSONArray("results");

            for(int i = 0; i < reviewsList.length(); i++){
                JSONObject review = reviewsList.getJSONObject(i);


                String content = "N/A";
                if(review.has("content")){
                    if(!review.getString("content").equals("")){
                        content = review.getString("content");
                    }

                }
                finalReviewsList.add(new Review(content));
            }
        }

        return finalReviewsList;
    }

    public static List<Trailer> fetchMovieTrailerData(String string) throws JSONException {

        List<Trailer> videoList = new ArrayList<>();
        JSONObject result = new JSONObject(string);

        if (result.has("results")) {
            JSONArray trailersList = result.getJSONArray("results");

            if (trailersList.length() != 0) {
                for (int i = 0; i < trailersList.length(); i++) {
                    JSONObject trailer = trailersList.getJSONObject(i);

                    String trailerPath = "N/A";
                    if (trailer.has("key")) {
                        if (!trailer.getString("key").equals("")) {
                            trailerPath = trailer.getString("key");
                        }
                    }

                        String trailerName = "Trailer";
                        if (trailer.has("name")) {
                            if (!trailer.getString("name").equals("")) {
                                trailerName = trailer.getString("name");
                            }
                        }
                        videoList.add(new Trailer(trailerName, trailerPath));
                    }
                }
            }
            return videoList;
        }
    }
