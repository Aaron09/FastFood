package com.aarongreen.fastfood.ReviewActivityFiles;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aarongreen.fastfood.CustomListenerFiles.FirebaseReviewListener;
import com.aarongreen.fastfood.DestinationActivityFiles.Destination;
import com.aarongreen.fastfood.DestinationActivityFiles.DestinationAdapter;
import com.aarongreen.fastfood.Photo;
import com.aarongreen.fastfood.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AaronGreen on 4/9/17.
 *
 * The activity showing the details and reviews of the selected Destination
 */

public class ReviewActivity extends AppCompatActivity {

    private Destination chosenDestination;
    private Location userLocation;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView ratingTextView;
    private TextView distanceTextView;
    private TextView addressTextView;
    private ImageView destinationImageView;

    /**
     * Connects the UI elements, sets up the recycler view with a ReviewAdapter, and fetches
     * reviews for the particular destination from the Firebase database.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        chosenDestination = intent.getParcelableExtra(DestinationAdapter.DESTINATION_KEY);
        userLocation = intent.getParcelableExtra(DestinationAdapter.LOCATION_KEY);

        View destinationView = findViewById(R.id.destination_item);
        nameTextView = (TextView) destinationView.findViewById(R.id.nameTextView);
        priceTextView = (TextView) destinationView.findViewById(R.id.priceTextView);
        ratingTextView = (TextView) destinationView.findViewById(R.id.ratingTextView);
        distanceTextView = (TextView) destinationView.findViewById(R.id.distanceTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        destinationImageView = (ImageView) findViewById(R.id.destination_image);

        nameTextView.setText(chosenDestination.getName());
        ratingTextView.setText(Destination.RATING_PREFIX +
                chosenDestination.getRating() + Destination.RATING_SUFFIX);
        priceTextView.setText(Destination.PRICE_PREFIX + chosenDestination.getPriceDollars());

        double roundedDistance = Destination.roundDistance(chosenDestination.getDistance(userLocation));
        distanceTextView.setText(roundedDistance + Destination.DISTANCE_SUFFIX);

        if (chosenDestination.getFirstImageURL().contains(Photo.DEFAULT_REFERENCE)) {
            Picasso.with(destinationImageView.getContext()).load(Photo.DEFAULT_BACKGROUND)
                    .into(destinationImageView);
        } else {
            Picasso.with(destinationImageView.getContext()).load(
                    chosenDestination.getFirstImageURL()).into(destinationImageView);
        }

        addressTextView.setText(chosenDestination.getVicinity());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        final ArrayList<Review> reviews = new ArrayList<>();
        final ReviewAdapter reviewAdapter = new ReviewAdapter(this, reviews, chosenDestination);
        recyclerView.setAdapter(reviewAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(chosenDestination.getLocationIdentifier());

        databaseReference.addChildEventListener(new FirebaseReviewListener(reviews, reviewAdapter));
    }

    /**
     * Launches the ReviewAlertDialog activity allowing the user to leave a review
     *
     * @param v the current view
     */
    public void leaveReviewClick(View v) {
        Intent intent = new Intent(v.getContext(), ReviewAlertDialog.class);
        startActivityForResult(intent, ReviewAlertDialog.SUBMIT_REQUEST_CODE);
    }

    /**
     *
     * @param requestCode the request code returned from the activity
     * @param resultCode the result code returned from the activity
     * @param intent the intent returned from the activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ReviewAlertDialog.SUBMIT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String receivedReview = intent.getStringExtra(ReviewAlertDialog.REVIEW_KEY);

                Review newReview = new Review(receivedReview,
                        chosenDestination.getLocationIdentifier());

                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference(chosenDestination.getLocationIdentifier())
                        .child(Long.toString(newReview.getTimeCreated()));

                databaseReference.setValue(newReview);
            }
        }
    }

    /**
     * Allows the user to open the address in Google Maps in the app or on Chrome
     *
     * @param view the current view
     */
    public void openMapsClick(View view) {
        String googleMapsString = "http://maps.google.co.in/maps?q="
                + chosenDestination.getVicinity();
        Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsString));
        startActivity(mapsIntent);
    }
}
