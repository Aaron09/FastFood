package com.aarongreen.fastfood.MainActivityFiles;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aarongreen.fastfood.DestinationActivityFiles.DestinationActivity;
import com.aarongreen.fastfood.R;

/**
 * The MainActivity of the application, allowing the user to click a button and then opening
 * the intent showing the nearby destinations / restaurants
 */
public class MainActivity extends AppCompatActivity {

    public static final String LOCATION_KEY = "LOCATION";
    public static final String REQUEST_TYPE_KEY = "REQUEST_TYPE";

    public enum REQUEST_TYPE {
        RESTAURANTS, BARS, DELIVERY
    };

    private static final String SECURITY_EXCEPTION_TAG = "SEC";
    private static final String SECURITY_EXCEPTION_DESCRIPTION = "Security Exception Occured.";
    private static final int REQUEST_CODE = 0;

    private Button retrieveRestaurantsButton;
    private Button retrieveBarsButton;
    private Button retrieveDeliveryLocationsButton;
    private LocationManager locationManager;
    private Location currentLocation;

    /**
     *
     * onCreate connects the UI elements, then checks to see if the Android APK version of the
     * device requires permissions requesting. If so, the request is made, then the button is
     * configured. Otherwise the button is immediately configured.
     *
     * @param savedInstanceState the current saved instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrieveRestaurantsButton = (Button) findViewById(R.id.retrieveRestaurantsButton);
        retrieveBarsButton = (Button) findViewById(R.id.retrieveBarsButton);
        retrieveDeliveryLocationsButton = (Button) findViewById(R.id.retrieveDeliveryButton);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // checks the SDK of the android device and requests permissions if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // get the necessary permissions from the user
            requestPermissions(new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            }, REQUEST_CODE);
        } else {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    /**
     *
     * This occurs when the APK of the Android device requires permission granting for accessing
     * the location.
     *
     * @param requestCode the request code of the location accessing permission request
     * @param permissions the list of permissions requested
     * @param grantResults the success status of the permissions request
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        final int MIN_RESULTS_SIZE = 0;
        final int PERMISSION_GRANTED_INDEX = 0;
        if (requestCode == REQUEST_CODE && grantResults.length > MIN_RESULTS_SIZE &&
                grantResults[PERMISSION_GRANTED_INDEX] == PackageManager.PERMISSION_GRANTED) {
            try {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), SECURITY_EXCEPTION_DESCRIPTION,
                        Toast.LENGTH_LONG);
            }
        }
    }

    /**
     * Starts the DestinationActivity passing the location and restaurant request type in the intent
     *
     * @param view the current view
     */
    public void retrieveRestaurantsClick(View view) {
        Intent intent = new Intent(view.getContext(), DestinationActivity.class);
        intent.putExtra(LOCATION_KEY, currentLocation);
        intent.putExtra(REQUEST_TYPE_KEY, REQUEST_TYPE.RESTAURANTS);
        view.getContext().startActivity(intent);
    }

    /**
     * Starts the DestinationActivity passing the location and bars request type in the intent
     *
     * @param view the current view
     */
    public void retrieveBarsClick(View view) {
        Intent intent = new Intent(view.getContext(), DestinationActivity.class);
        intent.putExtra(LOCATION_KEY, currentLocation);
        intent.putExtra(REQUEST_TYPE_KEY, REQUEST_TYPE.BARS);
        view.getContext().startActivity(intent);
    }

    /**
     * Starts the DestinationActivity passing the location and delivery request type in the intent
     *
     * @param view the current view
     */
    public void retrieveDeliveryClick(View view) {
        Intent intent = new Intent(view.getContext(), DestinationActivity.class);
        intent.putExtra(LOCATION_KEY, currentLocation);
        intent.putExtra(REQUEST_TYPE_KEY, REQUEST_TYPE.DELIVERY);
        view.getContext().startActivity(intent);
    }
}
