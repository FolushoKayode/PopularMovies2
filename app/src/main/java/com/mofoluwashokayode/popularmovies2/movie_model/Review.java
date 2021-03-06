package com.mofoluwashokayode.popularmovies2.movie_model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
    private final String reviewId;
    private final String author;
    private final String content;

    public Review(String reviewId, String author, String content) {
        this.reviewId = reviewId;
        this.author = author;
        this.content = content;
    }

    public Review(Parcel in) {
        reviewId = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reviewId);
        parcel.writeString(author);
        parcel.writeString(content);
    }
}
