package com.example.mac.movieappbasic;

/**
 * Created by mac on 3/04/18.
 */

public class Review {

    private String mAuthor;

    private String mContent;

    public Review(String mContent,String mAuthor) {
        this.mContent = mContent;
        this.mAuthor=mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
