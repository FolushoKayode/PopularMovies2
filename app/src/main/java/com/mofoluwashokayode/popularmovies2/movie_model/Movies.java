package com.mofoluwashokayode.popularmovies2.movie_model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {

        @Override
        public Movies createFromParcel(Parcel parcel) {
            return new Movies(parcel);
        }

        @Override
        public Movies[] newArray(int i) {
            return new Movies[i];
        }
    };
    private final int movieId;
    private final String title;
    private final String posterPath;
    private final String overview;
    private final String voteAverage;
    private final String releaseDate;
    private String movieRuntime;

    public Movies(int movieId, String title, String posterPath, String overview, String voteAverage, String releaseDate, String movieRuntime) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.movieRuntime = movieRuntime;
    }

    private Movies(Parcel parcel) {
        movieId = parcel.readInt();
        title = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        voteAverage = parcel.readString();
        releaseDate = parcel.readString();
        movieRuntime = parcel.readString();
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieRuntime() {
        return movieRuntime;
    }

    public void setMovieRuntime(String movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieId);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);
        parcel.writeString(movieRuntime);
    }
}
