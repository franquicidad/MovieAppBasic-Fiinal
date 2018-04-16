package com.example.mac.movieappbasic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.movieappbasic.JsonUtils.JsonParsingMovie;
import com.example.mac.movieappbasic.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mac on 7/02/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.NumberViewHolder> {

    private static final String TAG = MovieAdapter.class.getName();
    private int mNumberItems;
    final private GridItemClickListener mOnClickListener;
    private Context mContext;
    ArrayList<Movie> arrayListAdapter = new ArrayList<>();


    public interface GridItemClickListener {
        void onGridItemClick(Movie movie);
    }

    public MovieAdapter(Context mContext, int mNumberItems, GridItemClickListener listener) {
        this.mNumberItems = mNumberItems;
        this.mOnClickListener = listener;
        this.mContext = mContext;
    }

    public void addAll(ArrayList<Movie> movies) {

        arrayListAdapter.clear();


        arrayListAdapter.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MovieAdapter.NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutDesignItem = R.layout.layout_design;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutDesignItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.NumberViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return arrayListAdapter.size();
    }


    /**
     *
     * CREATE THE VIEWHOLDER CLASS!!!!
     */

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mainImage;
        TextView movieText;

        public NumberViewHolder(View itemView) {
            super(itemView);

            mainImage = itemView.findViewById(R.id.cardview_image);
            movieText = itemView.findViewById(R.id.movie_textView);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

            Movie movie = arrayListAdapter.get(listIndex);
            String movieName = movie.getMovieName();
            Double movieAverage = movie.getVoteAverage();
            String movieReleaseDate = movie.getReleaseDate();
            String movieOverview = movie.getOverview();
            String posterPath = movie.getPoster_path();
            Log.e(TAG, "POSTER PATH--------->:");


            Picasso.with(mContext).load(posterPath).into(mainImage);


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Movie movie = arrayListAdapter.get(clickedPosition);


            mOnClickListener.onGridItemClick(movie);
        }
    }
}
