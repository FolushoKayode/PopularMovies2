package com.mofoluwashokayode.popularmovies2.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mofoluwashokayode.popularmovies2.database.AppDatabase;
import com.mofoluwashokayode.popularmovies2.database.MovieEntity;

import java.util.List;

public class ReadMovieViewModel extends AndroidViewModel {

    AppDatabase database;

    private LiveData<List<MovieEntity>> movies;
    private LiveData<List<MovieEntity>> movie;

    public ReadMovieViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());

        movies = database.movieDAO().getAllMovies();

    }

    public LiveData<List<MovieEntity>> getMovies() {
        return movies;
    }

    public LiveData<List<MovieEntity>> getMovie(int movieId) {
        movie = database.movieDAO().loadMovieById(movieId);
        return movie;
    }

}
