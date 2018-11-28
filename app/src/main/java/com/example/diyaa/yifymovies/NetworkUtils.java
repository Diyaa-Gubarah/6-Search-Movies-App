package com.example.diyaa.yifymovies;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diyaa on 3/8/2018.
 */

public class NetworkUtils {
    private static final String LIST_MOVIES_BASE_URL = "https://yts.am/api/v2/list_movies.json?"; // Base URI for the Yts List Movies API
    private static final String QUERY_PARAM = "query_term"; // Parameter for the QUERY string
    private static final String QUALITY_PARAM = "quality"; // Parameter for the Quality string
    private static final String GENRE_PARAM = "genre"; // Parameter for the GENRE string
    private static final String SORT_BY_PARAM = "sort_by"; // Parameter for the SORT_BY string


    static List<YtsMovieDetails> getMoviesList(String searchInput) {

        HttpURLConnection urlConnection = null;
        String responseFromQuery = null;
        BufferedReader reader = null;
        try {

            Uri builtURI = Uri.parse(LIST_MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, searchInput)
                    .appendQueryParameter(QUALITY_PARAM, MainActivity.getQualitySpinnerItem())
                    .appendQueryParameter(GENRE_PARAM, MainActivity.getGenreSpinnerItem())
                    .appendQueryParameter(SORT_BY_PARAM, MainActivity.getSortBySpinnerItem())
                    .build();
            /*Convert your URI to a URL*/
            URL requestURL = new URL(builtURI.toString());
            Log.v("NetWorkUtils", "getMoviesList : URl =  " + builtURI);

            /*open the URL connection and make the request*/
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() != 200) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
             /*Read the response using an InputStream and a StringBuffer*/
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            responseFromQuery = buffer.toString();
            // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s

            List<YtsMovieDetails> YtsMovies = extractFeatureFromJson(responseFromQuery);

            // Return the list of {@link Earthquake}s
            return YtsMovies;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static List<YtsMovieDetails> extractFeatureFromJson(String ytsJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(ytsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<YtsMovieDetails> ytsMovieDetailsArrayList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject jsonObject = new JSONObject(ytsJSON);
            //*Get Json Data From Server*//*
            JSONObject details = jsonObject.getJSONObject("data");
            //*Get The JsonObject That Contain Data*//*
            JSONArray moviesDetails = details.getJSONArray("movies");
            // Create a JSONObject from the JSON response string


            // For each movies in the ytsmoviesArray, create an {@link Earthquake} object
            for (int i = 0; i < moviesDetails.length(); i++) {
                JSONObject movies = moviesDetails.getJSONObject(i);

                String title = movies.getString("title");
                double rating = movies.getDouble("rating");
                String summary = movies.getString("summary");
                String mediumImage = movies.getString("medium_cover_image");
                String largImage = movies.getString("large_cover_image");
                String url = movies.getString("url");
                int date = movies.getInt("year");

                 /*Get Genre Array & Get First Item*/
                JSONArray moviesGenre = movies.getJSONArray("genres");
                String genre = moviesGenre.getString(0);


                /*Get Torrent Array & get First & second & 3rd JsonObject That Contain The Url*/
                JSONArray moviesTorrentUrl = movies.getJSONArray("torrents");
                ArrayList<JSONObject> torrentItem = new ArrayList<>();

                for (int inx = 0; inx < moviesTorrentUrl.length(); inx++) {
                    torrentItem.add(moviesTorrentUrl.getJSONObject(inx));
                }

                String object720p = torrentItem.get(0).getString("url");
                String object1080p = torrentItem.get(1).getString("url");
                String object3d;

                /*Get 3rd Url For 3D if Exist Else add The Page Url To 3D Button*/
                if (moviesTorrentUrl.length() == 3) {
                    object3d = torrentItem.get(2).getString("url");
                } else {
                    object3d = movies.getString("url");
                }

                /*after We Collect All Details We Want To Use In Both Main & Details Activity We Pass it in Movies Details Object*/
                YtsMovieDetails ytsMovieDetails = new YtsMovieDetails(title, summary, rating, mediumImage
                        , largImage, url, date, genre, object720p, object1080p, object3d);

                // Add the new {@link MoviesDetails} to the list of ytsMovieDetailsArrayList.
                ytsMovieDetailsArrayList.add(ytsMovieDetails);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("NetWorkUtils", "Problem parsing the YiFiMovies JSON results", e);
        }

        // Return the list of ytsMovieDetails
        return ytsMovieDetailsArrayList;
    }

}


