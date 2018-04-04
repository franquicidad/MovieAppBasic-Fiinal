package com.example.mac.movieappbasic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mac on 2/04/18.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private List<Trailer> mData;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private  ItemClickListener mClickListener;

    public TrailersAdapter(Context context, List<Trailer> data) {
        this.mData = data;
        this.mContext = context;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.trailer_view,parent,false);
        return new TrailersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer=mData.get(position);
        String videoId=trailer.getTrailerRawPath();
        String videoUrl ="http://img.youtube.com/vi/"+videoId+"/0.jpg";

        Picasso.with(mContext).load(videoUrl).into(holder.video1);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Trailer getItem(int id){
        return mData.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView video1;

        public ViewHolder(View itemView) {
            super(itemView);
            video1=itemView.findViewById(R.id.trailer_item_video);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener !=null){
                mClickListener.onItemClick(v,getAdapterPosition());
            }

        }
    }
    void setClickListener(TrailersAdapter.ItemClickListener itemClickListener){
        this.mClickListener=itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
