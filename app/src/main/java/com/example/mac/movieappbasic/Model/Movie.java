package com.example.mac.movieappbasic.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mac on 7/02/18.
 */

public class Movie implements Parcelable{

    private String movieName;
    private int movie_ID;
    private String poster_path;
    private Double voteAverage;
    private String overview;
    private String releaseDate;

    public Movie(String movieName, int movie_ID, String poster_path, Double voteAverage, String overview, String releaseDate) {
        this.movieName = movieName;
        this.movie_ID=movie_ID;
        this.poster_path = poster_path;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;

    }


    protected Movie(Parcel in) {
        movieName = in.readString();
        movie_ID = in.readInt();
        poster_path = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getMovie_ID() {
        return movie_ID;
    }

    public void setMovie_ID(int movie_ID) {
        this.movie_ID = movie_ID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeInt(movie_ID);
        dest.writeString(poster_path);
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverage);
        }
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }
}
