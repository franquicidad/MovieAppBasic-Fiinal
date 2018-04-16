package com.example.mac.movieappbasic;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac on 14/03/18.
 */

public class MovieFavoritesAdapter extends RecyclerView.Adapter<MovieFavoritesAdapter.MovieViewHolder>{

    private Context mContext;
    private Cursor mCursor;


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
        return 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        public MovieViewHolder(View itemView) {


            super(itemView);
        }
    }
}
