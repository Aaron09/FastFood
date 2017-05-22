package com.aarongreen.fastfood.DestinationActivityFiles.DestinationComparators;

import android.location.Location;

import com.aarongreen.fastfood.DestinationActivityFiles.Destination;

import java.util.Comparator;

/**
 * Created by AaronGreen on 4/22/17.
 *
 * Comparator for sorting the list of destinations by distance
 */

public class DestinationDistanceComparator implements Comparator<Destination> {

    private Location userLocation;

    public DestinationDistanceComparator(Location userLocation) {
        this.userLocation = userLocation;
    }

    /**
     *
     * @param firstDestination the first destination being compared
     * @param secondDestination the second destination being compared
     * @return an integer representing the proper comparison of destinations to sort the list in
     * increasing order
     */
    @Override
    public int compare(Destination firstDestination, Destination secondDestination) {
        return ((Double) firstDestination.getDistance(userLocation))
                .compareTo(secondDestination.getDistance(userLocation));
    }
}
