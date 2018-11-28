package com.example.diyaa.yifymovies;

/**
 * Created by Diyaa on 3/8/2018.
 */

public class YtsMovieDetails {
    private String mMovieTitle;
    private String mMovieSummary;
    private double mMovieRating;
    private String mMovieImageMedium;
    private String mMovieImageLarge;
    private String mMovieUrl;
    private int mMovieYear;
    private String mGenre;
    private String mUrl720p;
    private String mUrl1080p;
    private String mUrl3d;


    public YtsMovieDetails(String movieTitle, String movieSummary
            , double movieRating, String movieImageMedium, String movieImageLarge
            , String moviesUrl, int movieYear, String genre, String url720p, String url1080p, String url3d) {

        mMovieTitle = movieTitle;
        mMovieSummary = movieSummary;
        mMovieRating = movieRating;
        mMovieImageMedium = movieImageMedium;
        mMovieImageLarge = movieImageLarge;
        mMovieUrl = moviesUrl;
        mMovieYear = movieYear;
        mGenre = genre;
        mUrl720p = url720p;
        mUrl1080p = url1080p;
        mUrl3d = url3d;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public String getMovieSummary() {
        return mMovieSummary;
    }

    public double getMovieRating() {
        return mMovieRating;
    }

    public String getMovieImageMeduim() {
        return mMovieImageMedium;
    }

    public String getMovieImageLarge() {
        return mMovieImageLarge;
    }

    public String getMoviesUrl() {
        return mMovieUrl;
    }

    public int getmMovieYear() {
        return mMovieYear;
    }

    public String getmGenre() {
        return mGenre;
    }

    public String getmUrl720p() {
        return mUrl720p;
    }

    public String getmUrl1080p() {
        return mUrl1080p;
    }

    public String getmUrl3d() {
        return mUrl3d;
    }

}



