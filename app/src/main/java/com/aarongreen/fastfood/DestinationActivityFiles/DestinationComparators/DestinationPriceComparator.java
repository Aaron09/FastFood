package com.aarongreen.fastfood.DestinationActivityFiles.DestinationComparators;

import com.aarongreen.fastfood.DestinationActivityFiles.Destination;

import java.util.Comparator;

/**
 * Created by AaronGreen on 4/22/17.
 *
 * Comparator for sorting a list of destinations by price
 */

public class DestinationPriceComparator implements Comparator<Destination> {

    /**
     * Sorts destinations by increasing price level, placing places with price level 0.0 (N/A) at the
     * end of the list
     *
     * @param firstDestination the first destination being compared
     * @param secondDestination the second destination being compared
     * @return an integer representing the proper comparison to sort the destinations in
     * increasing order
     */
    @Override
    public int compare(Destination firstDestination, Destination secondDestination) {
        if (firstDestination.getPrice_level() == 0.0) {
            return 1;
        } else if (secondDestination.getPrice_level() == 0.0) {
            return -1;
        }

        return ((Double) firstDestination.getPrice_level())
                .compareTo(secondDestination.getPrice_level());
    }
}
