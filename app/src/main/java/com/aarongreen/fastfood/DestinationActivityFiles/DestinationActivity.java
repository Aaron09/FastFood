package com.aarongreen.fastfood.DestinationActivityFiles;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aarongreen.fastfood.DestinationActivityFiles.DestinationComparators.DestinationDistanceComparator;
import com.aarongreen.fastfood.DestinationActivityFiles.DestinationComparators.DestinationPriceComparator;
import com.aarongreen.fastfood.DestinationActivityFiles.DestinationComparators.DestinationRatingComparator;
import com.aarongreen.fastfood.MainActivityFiles.MainActivity;
import com.aarongreen.fastfood.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by AaronGreen on 4/8/17.
 *
 * The Activity which lists Destinations for the users to view. The destinations are
 * presented in a recycler view.
 */

public class DestinationActivity extends AppCompatActivity {

    TextView ratingFilterTextView;
    TextView distanceFilterTextView;
    TextView priceFilterTextView;
    ArrayList<Destination> destinations;
    Location currentLocation;
    DestinationAdapter destinationAdapter;

    /**
     * Connects the UI elements, sets the Recycler view and adapter, and initializes a
     * DestinationAsyncTask to get the destinations from either the Google Places API or the
     * Amazon S3 database
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        ratingFilterTextView = (TextView) findViewById(R.id.ratingFilterTextView);
        distanceFilterTextView = (TextView) findViewById(R.id.distanceFilterTextView);
        priceFilterTextView = (TextView) findViewById(R.id.priceFilterTextView);

        Intent intent = getIntent();
        currentLocation = intent.getParcelableExtra(MainActivity.LOCATION_KEY);
        MainActivity.REQUEST_TYPE requestType =
                (MainActivity.REQUEST_TYPE) intent.getSerializableExtra(MainActivity.REQUEST_TYPE_KEY);

        if (currentLocation == null) {
            Toast.makeText(this, DestinationRetriever.FAILURE_MESSAGE, Toast.LENGTH_LONG);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        destinations = new ArrayList<>();
        destinationAdapter = new DestinationAdapter(currentLocation, destinations);
        recyclerView.setAdapter(destinationAdapter);

        try {
            URL url = new URL(DestinationRetriever.BASE_GOOGLE_URL);
            new DestinationAsyncTask(
                    this, destinationAdapter, currentLocation, requestType, destinations).execute(url);
        } catch (MalformedURLException e) {
            Toast.makeText(this, "Bad URL.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sorts the destinations by rating and bolds the rating filter text view
     *
     * @param v the current view
     */
    public void ratingFilterClick(View v) {
        Typeface currentTypeFace = ratingFilterTextView.getTypeface();
        if (currentTypeFace.equals(Typeface.DEFAULT)) {
            ratingFilterTextView.setTypeface(Typeface.DEFAULT_BOLD);
            priceFilterTextView.setTypeface(Typeface.DEFAULT);
            distanceFilterTextView.setTypeface(Typeface.DEFAULT);

            Collections.sort(destinations, new DestinationRatingComparator());
            destinationAdapter.notifyDataSetChanged();
        } else {
            ratingFilterTextView.setTypeface(Typeface.DEFAULT);
        }
    }

    /**
     * Sorts the destinations by distance and bolds the distance filter text view
     *
     * @param v the current view
     */
    public void distanceFilterClick(View v) {
        Typeface currentTypeFace = distanceFilterTextView.getTypeface();
        if (currentTypeFace.equals(Typeface.DEFAULT)) {
            distanceFilterTextView.setTypeface(Typeface.DEFAULT_BOLD);
            priceFilterTextView.setTypeface(Typeface.DEFAULT);
            ratingFilterTextView.setTypeface(Typeface.DEFAULT);

            Collections.sort(destinations, new DestinationDistanceComparator(currentLocation));
            destinationAdapter.notifyDataSetChanged();
        } else {
            distanceFilterTextView.setTypeface(Typeface.DEFAULT);
        }
    }

    /**
     * Sorts the destinations by price and bolds the price filter text view
     *
     * @param v the current view
     */
    public void priceFilterClick(View v) {
        Typeface currentTypeFace = priceFilterTextView.getTypeface();
        if (currentTypeFace.equals(Typeface.DEFAULT)) {
            Collections.sort(destinations, new DestinationPriceComparator());
            destinationAdapter.notifyDataSetChanged();

            priceFilterTextView.setTypeface(Typeface.DEFAULT_BOLD);
            ratingFilterTextView.setTypeface(Typeface.DEFAULT);
            distanceFilterTextView.setTypeface(Typeface.DEFAULT);

            Collections.sort(destinations, new DestinationPriceComparator());
            destinationAdapter.notifyDataSetChanged();
        } else {
            priceFilterTextView.setTypeface(Typeface.DEFAULT);
        }
    }
}
