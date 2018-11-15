package com.mofoluwashokayode.popularmovies2;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mofoluwashokayode.popularmovies2.callbacks.MyCallBack;
import com.mofoluwashokayode.popularmovies2.database.MovieEntity;
import com.mofoluwashokayode.popularmovies2.movie_model.Movies;
import com.mofoluwashokayode.popularmovies2.view_models.ReadMovieViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivityFragment extends Fragment {

    private static final String KEY_MOVIE_LIST = "movie_list";
    private static final String KEY_SELECTED_POSITION = "SELECTED_POSITION";
    private static final String SORT_ORDER = "sort_order";
    int index;
    private int itemPosition = GridView.INVALID_POSITION;
    private GridView gridViewMovie;
    private MovieAdapter movieAdapter;
    private ArrayList<Movies> movieList;
    private SharedPreferences preferences;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_MOVIE_LIST)) {
            movieList = new ArrayList<>();
        } else {
            movieList = savedInstanceState.getParcelableArrayList(KEY_MOVIE_LIST);
        }
        setHasOptionsMenu(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        movieAdapter = new MovieAdapter(getActivity(), movieList);
        gridViewMovie = view.findViewById(R.id.grid_view);
        // Inflate the layout for this fragment
        gridViewMovie.setAdapter(movieAdapter);
        gridViewMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movies movie = movieAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(movie);
                itemPosition = position;
            }
        });


        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_SELECTED_POSITION)) {
            itemPosition = savedInstanceState.getInt(KEY_SELECTED_POSITION);
        }


        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        index = gridViewMovie.getFirstVisiblePosition();
        gridViewMovie.smoothScrollToPosition(index);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    @Override
    public void onResume() {
        super.onResume();
        movieAdapter.notifyDataSetChanged();
    }

    private void updateMovie() {

        String sortingOrder = preferences.getString(SORT_ORDER,
                getString(R.string.sort_order_popular_value));

        if (!sortingOrder.equals(getString(R.string.sort_order_favorite_value))) {
            loadMovie(sortingOrder);
        } else {
            fetcFavouriteMovies();
        }

    }

    private void loadMovie(String sortingOrder) {
        FetchMovieTask moviesTask = new FetchMovieTask(new MyCallBack() {
            @Override
            public void updateAdapter(Movies[] movies) {
                if (movies != null) {
                    movieAdapter.clear();
                    Collections.addAll(movieList, movies);
                    movieAdapter.notifyDataSetChanged();
                    if (itemPosition != GridView.INVALID_POSITION) {
                        gridViewMovie.smoothScrollToPosition(itemPosition);
                    }
                }
            }
        });
        moviesTask.execute(sortingOrder);
    }

    private void fetcFavouriteMovies() {

        movieAdapter.clear();

        ReadMovieViewModel readMovieViewModel = ViewModelProviders.of(this).get(ReadMovieViewModel.class);
        readMovieViewModel.getMovies().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> movieEntities) {
                movieAdapter.clear();

                for (MovieEntity movieEntity : movieEntities) {
                    Movies movies = new Movies(movieEntity.getMovieId(), movieEntity.getTitle(),
                            movieEntity.getPosterPath(), movieEntity.getOverview(), movieEntity.getVoteAverage(),
                            movieEntity.getReleaseDate(), movieEntity.getMovieRuntime());
                    movieList.add(movies);
                }

                movieAdapter.notifyDataSetChanged();


            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_MOVIE_LIST, movieList);
        if (itemPosition != GridView.INVALID_POSITION) {
            outState.putInt(KEY_SELECTED_POSITION, itemPosition);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (id == R.id.action_most_popular) {
            setNewSortOrder(R.string.sort_order_popular_value);
            updateMovie();
            return true;
        } else if (id == R.id.action_top_rating) {
            setNewSortOrder(R.string.sort_order_rating_value);
            updateMovie();
            return true;
        } else if (id == R.id.action_favorite) {
            setNewSortOrder(R.string.sort_order_favorite_value);
            updateMovie();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNewSortOrder(int sortOrder) {
        String orderKey = getResources().getString(sortOrder);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SORT_ORDER, orderKey);
        editor.apply();
    }

    public interface Callback {
        void onItemSelected(Movies movie);
    }


}
