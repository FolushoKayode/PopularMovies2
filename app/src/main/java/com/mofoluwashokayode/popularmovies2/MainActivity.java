package com.mofoluwashokayode.popularmovies2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mofoluwashokayode.popularmovies2.movie_model.Movies;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private static final String MOVIE_KEY = "movie";
    private static final String DETAIL_MOVIE_FRAGMENT_TAG = "detailMovieFragmentTag";
    private boolean mTowpane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTowpane = findViewById(R.id.container_detail) != null;


    }

    @Override
    public void onItemSelected(Movies movie) {
        if (mTowpane) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MOVIE_KEY, movie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_detail, fragment, DETAIL_MOVIE_FRAGMENT_TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MOVIE_KEY, movie);
            startActivity(intent);
        }
    }
}
