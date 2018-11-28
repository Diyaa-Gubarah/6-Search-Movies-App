package com.example.diyaa.yifymovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Diyaa on 3/13/2018.
 */

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView mTitle = findViewById(R.id.yifi_movie_details_title_text_view);
        TextView mRating = findViewById(R.id.yifi_movie_details_rating_text_view);
        TextView mSummary = findViewById(R.id.yifi_movie_details_summary_text_view);
        TextView mYear = findViewById(R.id.yifi_movie_details_date_text_view);
        TextView mGenre = findViewById(R.id.yifi_movie_details_genre_text_view);
        ImageView mDetailsMediumImage = findViewById(R.id.yifi_movie_details_image_view_medium);

        Button button720p = findViewById(R.id.button_720p);
        Button button1080p = findViewById(R.id.button_1080p);
        Button button3d = findViewById(R.id.button_3d);

        FloatingActionButton mDetailsFAB = findViewById(R.id.details_fab);

        button720p.setOnClickListener(this);
        button1080p.setOnClickListener(this);
        button3d.setOnClickListener(this);
        mDetailsFAB.setOnClickListener(this);

        mTitle.setText(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_TITLE));
        mGenre.setText(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_GENRE));
        mYear.setText(""+ getIntent().getIntExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_YEAR, 2017));
        mRating.setText("" + getIntent().getDoubleExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_RATING, 0.0));
        mSummary.setText(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_SUMMARY));

        Glide.with(DetailsActivity.this)
                .load(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_MOVIES_LARGE_BACKGROUND_IMAGE))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .crossFade()
                .centerCrop()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mDetailsMediumImage);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.details_fab:
                Uri webpage = Uri.parse(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_MOVIES_URL_MAIN));
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;

            case R.id.button_720p:
                Uri url720p = Uri.parse(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_MOVIES_URL_720P));
                intent = new Intent(Intent.ACTION_VIEW, url720p);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;

            case R.id.button_1080p:
                Uri url1080p = Uri.parse(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_MOVIES_URL_1080P));
                intent = new Intent(Intent.ACTION_VIEW, url1080p);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;

            case R.id.button_3d:
                Uri url3d = Uri.parse(getIntent().getStringExtra(YtsRecycleViewAdapter.YtsViewHolder.EXTRA_MOVIES_URL_3D));
                intent = new Intent(Intent.ACTION_VIEW, url3d);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
        }

    }
}
