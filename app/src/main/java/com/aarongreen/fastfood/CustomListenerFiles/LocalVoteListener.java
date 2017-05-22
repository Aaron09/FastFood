package com.aarongreen.fastfood.CustomListenerFiles;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.aarongreen.fastfood.DestinationActivityFiles.Destination;
import com.aarongreen.fastfood.ReviewActivityFiles.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by AaronGreen on 4/16/17.
 *
 * A listener for local vote changes that will update firebase appropriately
 */

public class LocalVoteListener implements View.OnClickListener {

    private Destination currentDestination;
    private Review review;
    private Context context;
    private TextView voteTotalTextView;
    private String voteType;

    public LocalVoteListener(Destination currentDestination, Review review,
                             String voteType, TextView voteTotalTextView, Context context) {
        this.currentDestination = currentDestination;
        this.review = review;
        this.voteType = voteType;
        this.voteTotalTextView = voteTotalTextView;
        this.context = context;
    }

    /**
     * Gets the unique device ID and updates firebase with the appropriate change with an
     * action that occurred locally
     *
     * @param view the current view
     */
    @Override
    public void onClick(View view) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        final String deviceID = telephonyManager.getDeviceId();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(currentDestination.getLocationIdentifier())
                .child(Long.toString(review.getTimeCreated()))
                .child(voteType);

        updateUsersVote(databaseReference, deviceID);
    }

    /**
     * Either adds the user to the list in firebase or removes them if they were already there
     *
     * @param databaseReference reference to where the user's id would be in firebase
     * @param deviceID the unique id of the user's device
     */
    public static void updateUsersVote(final DatabaseReference databaseReference, final String deviceID) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean childExisted = false;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getValue().toString().equals(deviceID)) {
                        DatabaseReference childReference = databaseReference.child(child.getKey());
                        childReference.removeValue();
                        childExisted = true;
                    }
                }
                if (!childExisted) {
                    DatabaseReference pushReference = databaseReference.push();
                    pushReference.setValue(deviceID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // an error occurred while accessing Firebase
            }
        });
    }
}