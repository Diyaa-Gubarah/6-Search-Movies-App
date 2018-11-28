package com.example.diyaa.yifymovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<YtsMovieDetails>> {

    private static final int YTS_MOVIES_LOADER_ID = 0;

    private EditText mSearch;

    static Spinner mQualitySpinner;
    static Spinner mGenreSpinner;
    static Spinner mSortBySpinner;

    private TextView mTitle;

    private RecyclerView mRecyclerView;
    private YtsRecycleViewAdapter mAdapterRecycle;
    private List<YtsMovieDetails> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*initialize Loader*/
        if (getSupportLoaderManager().getLoader(YTS_MOVIES_LOADER_ID) != null) {

            getSupportLoaderManager().initLoader(YTS_MOVIES_LOADER_ID, null, this);
        }

        mSearch = (EditText) findViewById(R.id.search_field);
        mQualitySpinner = (Spinner) findViewById(R.id.quality_spinner);
        mGenreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        mSortBySpinner = (Spinner) findViewById(R.id.sort_by_spinner);
        mTitle = (TextView) findViewById(R.id.title_textview);

        /*This For Recycle View*/

        mArrayList = new ArrayList<YtsMovieDetails>();
        // Get a handle to the RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapterRecycle = new YtsRecycleViewAdapter(getApplicationContext(), mArrayList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapterRecycle);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        // Give the RecyclerView a default GridLayout manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, gridColumnCount));
        initialize();

    }

    /*This Method Use For Define Spinner*/
    public void initialize() {
        ArrayAdapter<CharSequence> qualityAdapter = ArrayAdapter.createFromResource(this,
                R.array.quality_array, android.R.layout.simple_spinner_item);

        qualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mQualitySpinner.setAdapter(qualityAdapter);

        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);

        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenreSpinner.setAdapter(genreAdapter);

        ArrayAdapter<CharSequence> sortByAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_array, android.R.layout.simple_spinner_item);

        sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortBySpinner.setAdapter(sortByAdapter);
    }


    /*This Method Call When Search Button Clicked!*/
    public void getResult(View v) {
        /*Get Text From EditText*/
        String mSearchText = mSearch.getText().toString();

        /*We Hide KeyBoard After We Press Search Button*/
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        /*Get Internet State & Fetch Data From Server If There's Connection*/
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        /*if We Have Internet Connection Then Get Data Else Notice User*/
        if (networkInfo != null && networkInfo.isConnected() && mSearchText.length() != 0) {

            mTitle.setText(R.string.wating);
            /*Set the Loader and Pass Data Thay Want Loaded*/
            Bundle bundle = new Bundle();
            bundle.putString("SEARCH_QUERY", mSearchText);
            getSupportLoaderManager().restartLoader(YTS_MOVIES_LOADER_ID, bundle, this);

        } else {
            if (mSearchText.length() == 0) {
                mTitle.setText(R.string.editText_emputy);
            } else {
                mTitle.setText(R.string.no_internet_message);
            }
        }

    }

    static String getQualitySpinnerItem() {
        return mQualitySpinner.getSelectedItem().toString();
    }

    static String getGenreSpinnerItem() {
        return mGenreSpinner.getSelectedItem().toString();
    }

    static String getSortBySpinnerItem() {
        return mSortBySpinner.getSelectedItem().toString();
    }


    /*Make New Object From Loader & Pass The Activity & Data Want To Load*/
    @Override
    public Loader<List<YtsMovieDetails>> onCreateLoader(int id, Bundle args) {
        return new YtsAsyncTaskLoader(this, args.getString("SEARCH_QUERY"));
    }

    /*Get The Data From Loader And Update UI But First Delete Last Adapter Data*/
    @Override
    public void onLoadFinished(Loader<List<YtsMovieDetails>> loader, List<YtsMovieDetails> data) {
        mArrayList.clear();
        if (data != null && !data.isEmpty()) {
            mTitle.setText(R.string.after_load_text);
            mAdapterRecycle = new YtsRecycleViewAdapter(getApplicationContext(), data);
            mRecyclerView.setAdapter(mAdapterRecycle);
        } else {
            mTitle.setText(R.string.loader_data_empty);
        }
    }

    /*Clear Adapter When Loader Reset*/
    @Override
    public void onLoaderReset(Loader<List<YtsMovieDetails>> loader) {
        // Loader reset, so we can clear out our existing data.
        mArrayList.clear();
    }

}



