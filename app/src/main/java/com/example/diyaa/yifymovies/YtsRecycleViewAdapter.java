package com.example.diyaa.yifymovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.util.List;

/**
 * Created by Diyaa on 3/12/2018.
 */

public class YtsRecycleViewAdapter extends RecyclerView.Adapter<YtsRecycleViewAdapter.YtsViewHolder> {

    private List<YtsMovieDetails> mYtsMovieDetailsList;
    private Context mContext;


    public YtsRecycleViewAdapter(Context context, List<YtsMovieDetails> mYtsMovieDetailsList) {
        this.mYtsMovieDetailsList = mYtsMovieDetailsList;
        this.mContext = context;
    }

    @Override
    public YtsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_result_item, parent, false);
        return new YtsViewHolder(mContext, mItemView, this);
    }

    @Override
    public void onBindViewHolder(YtsViewHolder holder, int position) {
        YtsMovieDetails mCurrent = mYtsMovieDetailsList.get(position);

        holder.ytsRatingTextView.setText("" + mCurrent.getMovieRating());
        holder.ytsDateTextView.setText("" + mCurrent.getmMovieYear());

        Glide.with(mContext)
                .load(mCurrent.getMovieImageMeduim())
                .thumbnail(0.5f)
                .crossFade()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ytsImageView);
    }

    @Override
    public int getItemCount() {
        return mYtsMovieDetailsList.size();
    }


    /*Yts View Holder Inner Class*/
    public class YtsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        static final String EXTRA_TITLE = "com.example.diyaa.yifymovies.extra.title";
        static final String EXTRA_RATING = "com.example.diyaa.yifymovies.extra.rating";
        static final String EXTRA_SUMMARY = "com.example.diyaa.yifymovies.extra.summary";
        static final String EXTRA_GENRE = "com.example.diyaa.yifymovies.extra.genre";
        static final String EXTRA_YEAR = "com.example.diyaa.yifymovies.extra.year";
        static final String EXTRA_MOVIES_URL_MAIN = "com.example.diyaa.yifymovies.extra.url_main";
        static final String EXTRA_MOVIES_URL_720P = "com.example.diyaa.yifymovies.extra.url_720p";
        static final String EXTRA_MOVIES_URL_1080P = "com.example.diyaa.yifymovies.extra.url_1080p";
        static final String EXTRA_MOVIES_URL_3D = "com.example.diyaa.yifymovies.extra.url_3d";
        static final String EXTRA_MOVIES_LARGE_BACKGROUND_IMAGE = "com.example.diyaa.yifymovies.extra.large_background_image";

        private final TextView ytsRatingTextView;
        private final TextView ytsDateTextView;
        private final ImageView ytsImageView;
        private final YtsRecycleViewAdapter mAdapter;

        private Context mContextViewHolder;

        public YtsViewHolder(Context context, View itemView, YtsRecycleViewAdapter adapter) {
            super(itemView);

            ytsRatingTextView = (TextView) itemView.findViewById(R.id.yifi_movie_rating_text_view);
            ytsDateTextView = (TextView) itemView.findViewById(R.id.yifi_movie_date_text_view);
            ytsImageView = (ImageView) itemView.findViewById(R.id.yifi_movie_image_view_medium);
            this.mAdapter = adapter;
            mContextViewHolder = context;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            YtsMovieDetails currentMovies = mYtsMovieDetailsList.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContextViewHolder, DetailsActivity.class);

            detailIntent.putExtra(EXTRA_TITLE, currentMovies.getMovieTitle());
            detailIntent.putExtra(EXTRA_RATING, currentMovies.getMovieRating());
            detailIntent.putExtra(EXTRA_SUMMARY, currentMovies.getMovieSummary());
            detailIntent.putExtra(EXTRA_MOVIES_URL_MAIN, currentMovies.getMoviesUrl());
            detailIntent.putExtra(EXTRA_YEAR, currentMovies.getmMovieYear());
            detailIntent.putExtra(EXTRA_GENRE, currentMovies.getmGenre());
            detailIntent.putExtra(EXTRA_MOVIES_URL_720P, currentMovies.getmUrl720p());
            detailIntent.putExtra(EXTRA_MOVIES_URL_1080P, currentMovies.getmUrl1080p());
            detailIntent.putExtra(EXTRA_MOVIES_URL_3D, currentMovies.getmUrl3d());
            detailIntent.putExtra(EXTRA_MOVIES_LARGE_BACKGROUND_IMAGE, currentMovies.getMovieImageLarge());

            detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContextViewHolder.startActivity(detailIntent);
        }
    }
}
