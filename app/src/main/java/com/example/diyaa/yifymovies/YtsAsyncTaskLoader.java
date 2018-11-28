package com.example.diyaa.yifymovies;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Diyaa on 3/11/2018.
 */

public class YtsAsyncTaskLoader extends AsyncTaskLoader<List<YtsMovieDetails>> {
    /*Create The Query So We Can Passed To Loader Using Constructor*/
    private String mSearchInput;

    public YtsAsyncTaskLoader(Context context, String searchInput) {
        super(context);
        mSearchInput = searchInput;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();

    }

    @Override
    public List<YtsMovieDetails> loadInBackground() {
        if (mSearchInput == null) {
            return null;
        }
        List<YtsMovieDetails> list = NetworkUtils.getMoviesList(mSearchInput);
        return list;
    }

}
