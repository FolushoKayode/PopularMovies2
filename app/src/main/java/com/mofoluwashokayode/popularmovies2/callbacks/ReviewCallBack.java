package com.mofoluwashokayode.popularmovies2.callbacks;

import com.mofoluwashokayode.popularmovies2.movie_model.Review;

public interface ReviewCallBack {
    void updateAdapter(Review[] reviews);
}
