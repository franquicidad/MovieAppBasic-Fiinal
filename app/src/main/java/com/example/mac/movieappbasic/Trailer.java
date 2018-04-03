package com.example.mac.movieappbasic;

/**
 * Created by mac on 2/04/18.
 */

public class Trailer {
    private static final String TRAILER_URL_BASE= "https://www.youtube.com/watch?v=";

    private String mName;
    private String mPath;

    public Trailer(String name, String path) {
        mName=name;
        mPath=path;
    }
    public String getTrailerPath(){
        return TRAILER_URL_BASE+mPath;
    }
    public String getTrailerRawPath(){
        return mPath;
    }
}
