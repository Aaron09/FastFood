package com.aarongreen.fastfood.DestinationActivityFiles;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.aarongreen.fastfood.Photo;
import com.aarongreen.fastfood.ReviewActivityFiles.Review;

import java.util.HashMap;
import java.util.List;

/**
 * Created by AaronGreen on 4/6/17.
 *
 * The class which holds the information received from the GooglePlaces API request for an
 * individual destination / restaurant.
 */

public class Destination implements Parcelable {

    /**
     * Class constants
     */
    public static final String RATING_PREFIX = "Rating: ";
    public static final String RATING_SUFFIX = "/5.0";
    public static final String PRICE_PREFIX = "Price: ";
    public static final String DISTANCE_SUFFIX = "mi";

    private static final String LOCATION = "location";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lng";

    /**
     * Class constants for determining price text
     */
    private static final double FIRST_PRICE_LEVEL = 1.0;
    private static final double SECOND_PRICE_LEVEL = 2.0;
    private static final double THIRD_PRICE_LEVEL = 3.0;
    private static final double FOURTH_PRICE_LEVEL = 4.0;
    private static final double FIFTH_PRICE_LEVEL = 5.0;

    private static final String DEFAULT_PRICE = "N/A";
    private static final String FIRST_PRICE_LEVEL_DOLLARS = "$1-$10";
    private static final String SECOND_PRICE_LEVEL_DOLLARS = "$10-$20";
    private static final String THIRD_PRICE_LEVEL_DOLLARS = "$20-$30";
    private static final String FOURTH_PRICE_LEVEL_DOLLARS = "$30-$40";
    private static final String FIFTH_PRICE_LEVEL_DOLLARS = "$40+";

    /**
     * Object properties
     */
    private HashMap<String, HashMap<String, Object>> geometry;
    private String name;
    private double price_level;
    private double rating;
    private String vicinity;
    private double distance;
    private List<Review> reviews;
    private HashMap<String, Object>[] photos;
    private String firstImageURL;

    /**
     * Default constructor for distance tests
     */
    public Destination() {

    }

    public double getLatitude() {
        return (Double) geometry.get(LOCATION).get(LATITUDE);
    }

    public double getLongitude() {
        return (Double) geometry.get(LOCATION).get(LONGITUDE);
    }

    public String getName() {
        return name;
    }

    public double getPrice_level() {
        return price_level;
    }

    public double getRating() {
        return rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    /**
     * Only for use when constructed from a parcel, will be null otherwise
     *
     * @return the URL of the first photo image from the google places api
     */
    public String getFirstImageURL() {
        return firstImageURL;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Appends a new review onto the destination's reviews
     *
     * @param newReview review to be added to the list
     */
    public void appendToReviews(Review newReview) {
        reviews.add(newReview);
    }

    /**
     * If there are no photos, it returns a photo object with the default values
     *
     * @return a photo object representing the first photo listed in the destination's json
     */
    public Photo getFirstPhoto() {
        if (photos == null) {
            return new Photo(Photo.DEFAULT_REFERENCE, Photo.DEFAULT_WIDTH, Photo.DEFAULT_HEIGHT);
        }

        if (photos.length > 0) {
            String photoReference = (String) photos[0].get(Photo.PHOTO_REFERENCE);
            double minWidth = (double) photos[0].get(Photo.WIDTH);
            double minHeight = (double) photos[0].get(Photo.HEIGHT);
            return new Photo(photoReference, minWidth, minHeight);
        }
        return null;
    }

    /**
     *
     * @param userLocation the user's current location
     * @return the distance in miles between the desired destination and the user's location
     */
    public double getDistance(Location userLocation) {
        return calculateDistance(this.getLatitude(), this.getLongitude(),
                userLocation.getLatitude(), userLocation.getLongitude());
    }

    /**
     * Solution found at:
     * http://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
     *
     * @param distance the distance to be rounded
     * @return the distance rounded to two decimal places
     */
    public static double roundDistance(double distance) {
        final double ROUNDING_FACTOR = 100;
        return (double) Math.round(distance * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }

    /**
     * Creates a unique identifier for the destination, removing the characters that are not
     * allowed to be part of a key in the Firebase database
     *
     * @return the unique identifier for the particular location
     */
    public String getLocationIdentifier() {
        return (name + getLatitude() + getLongitude()).replaceAll("[.#$]", "");
    }

    public String getPriceDollars() {
        if (price_level == FIRST_PRICE_LEVEL) {
            return FIRST_PRICE_LEVEL_DOLLARS;
        } else if (price_level == SECOND_PRICE_LEVEL) {
            return SECOND_PRICE_LEVEL_DOLLARS;
        } else if (price_level == THIRD_PRICE_LEVEL) {
            return THIRD_PRICE_LEVEL_DOLLARS;
        } else if (price_level == FOURTH_PRICE_LEVEL) {
            return FOURTH_PRICE_LEVEL_DOLLARS;
        } else if (price_level == FIFTH_PRICE_LEVEL) {
            return FIFTH_PRICE_LEVEL_DOLLARS;
        } else {
            return DEFAULT_PRICE;
        }
    }

    /**
     * Uses the Haversine formula for calculating the distance between two latitudinal and
     * longitudinal points
     *
     * The formula is described here: https://en.wikipedia.org/wiki/Haversine_formula
     *
     * @param startingLatitude the starting location's latitude
     * @param startingLongitude the starting location's longitude
     * @param endingLatitude the ending location's latitude
     * @param endingLongitude the ending location's longitude
     * @return the distance in miles between the starting and ending location
     */
    public double calculateDistance(double startingLatitude, double startingLongitude,
                                     double endingLatitude, double endingLongitude) {
        final int EARTH_RADIUS = 3959; // in miles

        double latitudeDifference = Math.toRadians(endingLatitude - startingLatitude);
        double longitudeDifference = Math.toRadians(endingLongitude - endingLongitude);

        double initialHaversineCalculation =
                Math.sin(latitudeDifference / 2) * Math.sin(latitudeDifference / 2) +
                Math.cos(Math.toRadians(startingLatitude)) * Math.cos(Math.toRadians(endingLatitude)) *
                Math.sin(longitudeDifference / 2) * Math.sin(longitudeDifference / 2);

        double finalHaversineCalculation = 2 * Math.atan2(Math.sqrt(initialHaversineCalculation),
                Math.sqrt(1 - initialHaversineCalculation));

        return EARTH_RADIUS * finalHaversineCalculation;
    }

    /**
     * The following methods allow the object to be used as a Parcel between activities
     */

    protected Destination(Parcel in) {
        this.name = in.readString();
        this.rating = in.readDouble();
        this.price_level = in.readDouble();
        this.geometry = in.readHashMap(HashMap.class.getClassLoader());
        this.vicinity = in.readString();
        this.firstImageURL = in.readString();
    }

    public static final Creator<Destination> CREATOR = new Creator<Destination>() {
        @Override
        public Destination createFromParcel(Parcel in) {
            return new Destination(in);
        }

        @Override
        public Destination[] newArray(int size) {
            return new Destination[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(rating);
        parcel.writeDouble(price_level);
        parcel.writeMap(geometry);
        parcel.writeString(vicinity);
        parcel.writeString(getFirstPhoto().getPhotoURL());
    }
}
