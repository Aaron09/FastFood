package com.aarongreen.fastfood.ReviewActivityFiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aarongreen.fastfood.R;

/**
 * Created by AaronGreen on 4/9/17.
 *
 * Activity which takes the theme of an AlertDialog for leaving reviews
 * Specified as theme of AlertDialog in Android Manifest file
 */

public class ReviewAlertDialog extends Activity {

    public static final int SUBMIT_REQUEST_CODE = 200;
    public static final String REVIEW_KEY = "REVIEW";

    private EditText reviewEditText;

    /**
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_alert_dialog);

        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
    }

    /**
     * Cancels the ReviewAlertDialog, returning to the ReviewActivity
     *
     * @param v the current view
     */
    public void cancelReviewClick(View v) {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    /**
     * Submits the text in the edit text as a review and passes it back to the ReviewActivity
     *
     * @param v the current view
     */
    public void submitReviewClick(View v) {
        String review = reviewEditText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(REVIEW_KEY, review);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
