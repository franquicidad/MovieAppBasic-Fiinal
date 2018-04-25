package com.example.mac.movieappbasic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewAdapter  extends ArrayAdapter<Review>{

    public ReviewAdapter( Context context, List<Review> reviews) {
        super(context, 0, reviews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Review review=getItem(position);

        convertView= LayoutInflater.from(getContext()).inflate(R.layout.review_view,parent,false);

        TextView author=convertView.findViewById(R.id.review_author);
        TextView revContent=convertView.findViewById(R.id.review_content);

        author.setText(review.getmAuthor());
        revContent.setText(review.getmContent());

        return convertView;
    }
}
