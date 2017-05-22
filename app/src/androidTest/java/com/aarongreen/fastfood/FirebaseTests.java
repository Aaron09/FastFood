package com.aarongreen.fastfood;

import android.util.Log;

import com.aarongreen.fastfood.CustomListenerFiles.LocalVoteListener;
import com.aarongreen.fastfood.ReviewActivityFiles.Review;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by AaronGreen on 4/17/17.
 *
 * Tests for firebase real-time database functionality
 */

public class FirebaseTests {

    private static final String REVIEW_TEXT = "Brian Truong's eatery is a fantastic establishment.";
    private static final String PARENT_RESTAURANT_ID = "Brian Truong's Eatery";
    private static final long TIME_CREATED = 738275823;
    private static final List<String> upvoteList = new ArrayList<>();
    private static final List<String> downvoteList = new ArrayList<>();
    private static final String UPVOTE_LIST = "upvoteList";
    private static final String DOWNVOTE_LIST = "downvoteList";

    @Before
    public void setUp() throws Exception {
        upvoteList.clear();
        downvoteList.clear();
        upvoteList.add("device1");
        upvoteList.add("device2");
        upvoteList.add("device3");
        downvoteList.add("device4");
        downvoteList.add("device5");
    }

    @Test
    public void writeReviewTest() throws Exception {
        Review review = new Review(REVIEW_TEXT, TIME_CREATED, upvoteList,
                downvoteList, PARENT_RESTAURANT_ID);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(review.getTimeCreated()));

        databaseReference.setValue(review);
    }

    @Test
    public void writeUpvoteTest() throws Exception {
        Review review = new Review(REVIEW_TEXT, TIME_CREATED, upvoteList,
                downvoteList, PARENT_RESTAURANT_ID);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(review.getTimeCreated()));

        databaseReference.setValue(review);

        final String DEVICE_ID = "7523957839";
        DatabaseReference upvotesReference = databaseReference.child(Review.UPVOTES);

        LocalVoteListener.updateUsersVote(upvotesReference, DEVICE_ID);
    }

    @Test
    public void writeDownvoteTest() throws Exception {
        Review review = new Review(REVIEW_TEXT, TIME_CREATED, upvoteList,
                downvoteList, PARENT_RESTAURANT_ID);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(review.getTimeCreated()));

        databaseReference.setValue(review);

        final String DEVICE_ID = "7523957839";
        DatabaseReference downvotesReference = databaseReference.child(Review.DOWNVOTES);

        LocalVoteListener.updateUsersVote(downvotesReference, DEVICE_ID);
    }

    @Test
    public void readReviewTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(TIME_CREATED));

        DatabaseReference restaurantReference = databaseReference.getParent();

        restaurantReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, Object> reviewData = (HashMap) dataSnapshot.getValue();

                Log.d("TAG", reviewData.toString());

                String reviewText = (String) reviewData.get(Review.REVIEW_TEXT);
                String parentRestaurantID = (String) reviewData.get(Review.PARENT_RESTAURANT_ID);
                long timeCreated = (long) reviewData.get(Review.TIME_CREATED);
                List<String> retrievedUpvotes = (List) reviewData.get(UPVOTE_LIST);
                List<String> retrievedDownvotes = (List) reviewData.get(DOWNVOTE_LIST);

                assertEquals(reviewText, REVIEW_TEXT);
                assertEquals(parentRestaurantID, PARENT_RESTAURANT_ID);
                assertEquals(timeCreated, TIME_CREATED);
                assertEquals(retrievedUpvotes, upvoteList);
                assertEquals(retrievedDownvotes, downvoteList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("TAG", "THIS WAS REMOVED");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onCancelled(DatabaseError databaseError) {/* Unused */}
        });
    }

    @Test
    public void readUpvoteTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(TIME_CREATED));

        final String DEVICE_ID = "7523957839";
        DatabaseReference upvotesReference = databaseReference.child(Review.UPVOTES);

        LocalVoteListener.updateUsersVote(upvotesReference, DEVICE_ID);

        databaseReference.child(Review.UPVOTES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String newUpvoteDeviceID = (String) dataSnapshot.getValue();
                assertEquals(newUpvoteDeviceID, DEVICE_ID);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {/* Unused */}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onCancelled(DatabaseError databaseError) {/* Unused */}
        });
    }

    @Test
    public void readDownvoteTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(TIME_CREATED));

        final String DEVICE_ID = "7523957839";
        DatabaseReference downvotesReference = databaseReference.child(Review.DOWNVOTES);

        LocalVoteListener.updateUsersVote(downvotesReference, DEVICE_ID);

        databaseReference.child(Review.DOWNVOTES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String newDownvoteDeviceID = (String) dataSnapshot.getValue();
                assertEquals(newDownvoteDeviceID, DEVICE_ID);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {/* Unused */}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onCancelled(DatabaseError databaseError) {/* Unused */}
        });
    }

    @Test
    public void deleteUpvoteTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(TIME_CREATED));

        final String DEVICE_ID = "7523957839";
        DatabaseReference upvotesReference = databaseReference.child(Review.UPVOTES);

        // add then remove vote
        LocalVoteListener.updateUsersVote(upvotesReference, DEVICE_ID);
        LocalVoteListener.updateUsersVote(upvotesReference, DEVICE_ID);

        databaseReference.child(Review.UPVOTES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // snapshot gives us the child that was removed
                String removedUpvoteDeviceID = (String) dataSnapshot.getValue();
                assertEquals(removedUpvoteDeviceID, DEVICE_ID);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onCancelled(DatabaseError databaseError) {/* Unused */}
        });
    }

    @Test
    public void deleteDownvoteTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(PARENT_RESTAURANT_ID)
                .child(Long.toString(TIME_CREATED));

        final String DEVICE_ID = "7523957839";
        DatabaseReference downvotesReference = databaseReference.child(Review.DOWNVOTES);

        // add then remove vote
        LocalVoteListener.updateUsersVote(downvotesReference, DEVICE_ID);
        LocalVoteListener.updateUsersVote(downvotesReference, DEVICE_ID);

        databaseReference.child(Review.DOWNVOTES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // snapshot gives us the child that was removed
                String removedDownvoteDeviceID = (String) dataSnapshot.getValue();
                assertEquals(removedDownvoteDeviceID, DEVICE_ID);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {/* Unused */}

            @Override
            public void onCancelled(DatabaseError databaseError) {/* Unused */}
        });
    }
}
