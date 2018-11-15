package com.mofoluwashokayode.popularmovies2.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.mofoluwashokayode.popularmovies2.database.AppDatabase;
import com.mofoluwashokayode.popularmovies2.database.MovieEntity;

public class AddFavouriteMovieViewModel extends AndroidViewModel {

    AppDatabase appDatabase;


    public AddFavouriteMovieViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
    }

    public void insertItem(MovieEntity movieEntity) {

        new BackgroundInsert(appDatabase).execute(movieEntity);
    }

    public void deleteItem(int movieID) {
        new BackgroundDelete(appDatabase).execute(movieID);
    }


    private static class BackgroundInsert extends AsyncTask<MovieEntity, Void, Void> {

        private AppDatabase db;

        BackgroundInsert(AppDatabase appDatabase) {

            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MovieEntity... params) {

            db.movieDAO().insertMovie(params[0]);

            return null;

        }
    }

    private static class BackgroundDelete extends AsyncTask<Integer, Void, Void> {

        private AppDatabase db;

        BackgroundDelete(AppDatabase appDatabase) {

            db = appDatabase;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            int position = integers[0];
            db.movieDAO().deleteMovieById(position);
            return null;
        }
    }

}
