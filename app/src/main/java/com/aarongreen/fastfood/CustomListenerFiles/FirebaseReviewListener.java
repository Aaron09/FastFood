package com.aarongreen.fastfood.CustomListenerFiles;

import com.aarongreen.fastfood.ReviewActivityFiles.Review;
import com.aarongreen.fastfood.ReviewActivityFiles.ReviewAdapter;
import com.aarongreen.fastfood.ReviewActivityFiles.ReviewVoteComparator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AaronGreen on 4/17/17.
 *
 * A listener to firebase to receive new reviews
 */

public class FirebaseReviewListener implements ChildEventListener {

    private List<Review> reviews;
    private ReviewAdapter reviewAdapter;

    public FirebaseReviewListener(List<Review> reviews, ReviewAdapter reviewAdapter) {
        this.reviews = reviews;
        this.reviewAdapter = reviewAdapter;
    }

    /**
     * This is called when any user adds a new review of a restaurant. We parse the data
     * received from the snapshot, build a review object from it, and add it to our list of reviews
     * in the ReviewAdapter.
     *
     * @param dataSnapshot a snapshot of the data of the new review
     * @param s the previous child of the new data
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        HashMap<String, Object> reviewData = (HashMap) dataSnapshot.getValue();

        String reviewText = (String) reviewData.get(Review.REVIEW_TEXT);
        long timeCreated = (long) reviewData.get(Review.TIME_CREATED);
        String parentRestaurantID = (String) reviewData.get(Review.PARENT_RESTAURANT_ID);

        HashMap<String, String> upvoteMap = (HashMap) reviewData.get(Review.UPVOTES);
        HashMap<String, String> downvoteMap = (HashMap) reviewData.get(Review.DOWNVOTES);

        List<String> upvoteList = (List) ((upvoteMap == null) ? new ArrayList<>() :
                new ArrayList<>(upvoteMap.values()));
        List<String> downvoteList = (List) ((downvoteMap == null) ? new ArrayList<>() :
                new ArrayList<>(downvoteMap.values()));

        Review review = new Review(reviewText, timeCreated, upvoteList,
                downvoteList, parentRestaurantID);

        reviews.add(review);
        Collections.sort(reviews, new ReviewVoteComparator());
        reviewAdapter.notifyDataSetChanged();;
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        /*
         Removing reviews is not yet implemented. Implementation will go here when this
         is to be added.
         */
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {/* Unusd */}

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {/* Unused */}

    @Override
    public void onCancelled(DatabaseError databaseError) {/* Unused */}
}
