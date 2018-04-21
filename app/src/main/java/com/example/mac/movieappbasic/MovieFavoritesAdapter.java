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

import java.util.ArrayList;

/**
 * Created by mac on 14/03/18.
 */

public class MovieFavoritesAdapter extends RecyclerView.Adapter<MovieFavoritesAdapter.MovieViewHolder>{

    private Context mContext;
    private Cursor mCursor;
    private int mNumberItems;

    ImageView favMovieImage;
    TextView favId;
    TextView movieName;
    TextView overview;
    TextView rating;
    TextView relDate;


    ArrayList<Movie> favArrayList= new ArrayList<>();


    /** Constructor using the context and the db cursor
     *
     */
    public MovieFavoritesAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    @Override
    public MovieFavoritesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view= inflater.inflate(R.layout.favorites_layout,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieFavoritesAdapter.MovieViewHolder holder, int position) {



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
