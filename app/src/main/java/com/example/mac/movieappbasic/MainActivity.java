package com.example.mac.movieappbasic;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.movieappbasic.JsonUtils.JsonParsingMovie;
import com.example.mac.movieappbasic.Model.Movie;
import com.example.mac.movieappbasic.Moviedata.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private static final String TOP_MOVIE = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    private static final String MERGED_TOP_MOVIE = (TOP_MOVIE + BuildConfig.API_KEY);
    private static final int NUM_LIST_ITEMS = 100;
    private static final int MOVIE_LOADER_ID = 1;
    private final String MERGED_BASE_URL = (BASE_URL + BuildConfig.API_KEY);
    GridLayoutManager gridLayoutManager;
    ArrayList<Movie> adapterArrayList;
    GridView gridView;
    String sortMode = "popular";
    private MovieAdapter mMovieAdapter;
    private RecyclerView mMovieList;
    private RecyclerView mfavoriteList;
    private MovieFavoritesAdapter mMovieFavoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieList = findViewById(R.id.rv_movies);
        gridView = findViewById(R.id.gridview_layout);
        mfavoriteList=findViewById(R.id.rv_favorites);

        gridLayoutManager = new GridLayoutManager(this, 2);
        mMovieList.setLayoutManager(gridLayoutManager);
        mMovieList.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this, NUM_LIST_ITEMS, new MovieAdapter.GridItemClickListener() {

            @Override
            public void onGridItemClick(Movie movie) {

                Intent intentDetailActivity = new Intent(getBaseContext(), MovieDetail.class);

                intentDetailActivity.putExtra("MOVIE_OBJECT", movie);

                startActivity(intentDetailActivity);


            }
        });

        if(sortMode.equals("favorites")){
            mMovieFavoritesAdapter=new MovieFavoritesAdapter(this,adapterArrayList);
            mfavoriteList.setAdapter(mMovieFavoritesAdapter);
            adapterArrayList=new ArrayList<>();
            mMovieFavoritesAdapter.FavoritesAddAll(adapterArrayList);
        }else{
            mMovieList.setAdapter(mMovieAdapter);
            adapterArrayList = new ArrayList<>();
            mMovieAdapter.addAll(adapterArrayList);
        }


        LoaderManager loaderManager = getSupportLoaderManager();

        loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);

    }


    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {


        switch (sortMode){
            case "favorites":
                return new FavoriteAsynckTaskLoader(this);
            case "popular":
                return new MovieAsynctaskLoader(this,MERGED_BASE_URL);
            case "topRated":
                return new MovieAsynctaskLoader(this,MERGED_TOP_MOVIE);

                default:
                    return new MovieAsynctaskLoader(this,MERGED_BASE_URL);
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

        if (sortMode.equals("favorites")) {
            mMovieFavoritesAdapter=new MovieFavoritesAdapter(this,data);
            mfavoriteList.setAdapter(mMovieFavoritesAdapter);
            mMovieFavoritesAdapter.FavoritesAddAll(data);
        } else {
            mMovieAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.favorites:

                sortMode="favorites";
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                Intent favIntent= new Intent(this,Favorites.class);
                startActivity(favIntent);
                return true;

            case R.id.popular:
                sortMode = "popular";
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                return true;
            case R.id.top_rated:
                sortMode = "topRated";
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}
//ok.

//jfdjfjd