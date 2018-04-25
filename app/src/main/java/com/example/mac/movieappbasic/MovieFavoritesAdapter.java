package com.example.mac.movieappbasic;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.movieappbasic.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mac on 14/03/18.
 */

public class MovieFavoritesAdapter extends RecyclerView.Adapter<MovieFavoritesAdapter.MovieViewHolder>{

    private Context mContext;
    private Cursor mCursor;
    private int mNumberItems;

    public ArrayList<Movie> listMovie;

    ImageView favMovieImage;
    TextView favId;
    TextView movieName;
    TextView overview;
    TextView rating;
    TextView relDate;


    ArrayList<Movie> favArrayList;


    public MovieFavoritesAdapter(Context context,ArrayList<Movie> favArrayList) {
        this.favArrayList = favArrayList;
        this.mContext=context;
    }

    /** Constructor using the context and the db cursor
     *
     */


    @Override
    public MovieFavoritesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view= inflater.inflate(R.layout.favorites_layout,parent,false);

        return new MovieViewHolder(view);
    }

    public void FavoritesAddAll(ArrayList<Movie> movies){
        favArrayList.clear();

        favArrayList.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MovieFavoritesAdapter.MovieViewHolder holder, int position) {

        Movie movieOnBind=favArrayList.get(position);


        favId.setText(movieOnBind.getMovie_ID());
        movieName.setText(movieOnBind.getMovieName());
        overview.setText(movieOnBind.getOverview());
        rating.setText(String.valueOf(movieOnBind.getVoteAverage()));
        relDate.setText(movieOnBind.getReleaseDate());

        String favMovieIm=movieOnBind.getPoster_path();

        Picasso.with(mContext).load(favMovieIm).into(favMovieImage);



    }

    @Override
    public int getItemCount() {
        return favArrayList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        public MovieViewHolder(View itemView) {
            super(itemView);

            favMovieImage=(ImageView)itemView.findViewById(R.id.favoriteImage);
            favId=(TextView)itemView.findViewById(R.id.movie_id);
            movieName=(TextView)itemView.findViewById(R.id.favorite_movie_name);
            overview=(TextView)itemView.findViewById(R.id.favorite_overview);
            rating=(TextView)itemView.findViewById(R.id.favorite_rating);
            relDate=(TextView)itemView.findViewById(R.id.favorite_r_date);

        }
    }


}
