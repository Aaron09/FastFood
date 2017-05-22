package com.aarongreen.fastfood.DestinationActivityFiles.DestinationComparators;

import com.aarongreen.fastfood.DestinationActivityFiles.Destination;

import java.util.Comparator;

/**
 * Created by AaronGreen on 4/22/17.
 *
 * Comparator for sorting a list of destinations by rating
 */

public class DestinationRatingComparator implements Comparator<Destination> {

    /**
     *
     * @param firstDestination the first destination being compared
     * @param secondDestination the second destination being compared
     * @return an integer representing the proper comparison to sort the list in decreasing order
     */
    @Override
    public int compare(Destination firstDestination, Destination secondDestination) {
        return ((Double) secondDestination.getRating()).compareTo(firstDestination.getRating());
    }
}
