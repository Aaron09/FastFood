package com.aarongreen.fastfood;

import android.location.Location;

import com.aarongreen.fastfood.DestinationActivityFiles.Destination;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by AaronGreen on 5/1/17.
 *
 * Tests for calculating distance using latitude and longitude
 */

public class DistanceCalculationTests {

    Destination testDestination;

    @Before
    public void setUp() throws Exception {
        testDestination = new Destination();
    }

    @Test
    public void calculateBasicDistanceTest() throws Exception{
        final double EXPECTED_DISTANCE = 1727.4396271613878;
        final double STARTING_LATITUDE = 100.0;
        final double STARTING_LONGITUDE = 150.0;
        final double ENDING_LATITUDE = 125.0;
        final double ENDING_LONGITUDE = 98.45;
        double distance = testDestination.calculateDistance(STARTING_LATITUDE, STARTING_LONGITUDE,
                ENDING_LATITUDE, ENDING_LONGITUDE);

        assertEquals(EXPECTED_DISTANCE, distance);
    }

    @Test
    public void calculateNoDistance() throws Exception {
        final double EXPECTED_DISTANCE = 0.0;
        final double STARTING_LATITUDE = 100.0;
        final double STARTING_LONGITUDE = 150.0;
        final double ENDING_LATITUDE = 100.0;
        final double ENDING_LONGITUDE = 150.0;

        double distance = testDestination.calculateDistance(STARTING_LATITUDE, STARTING_LONGITUDE,
                ENDING_LATITUDE, ENDING_LONGITUDE);

        assertEquals(EXPECTED_DISTANCE, distance);
    }

    @Test
    public void calculateNegativeCoordinateDistance() throws Exception {
        final double EXPECTED_DISTANCE = 11055.613613832878;
        final double STARTING_LATITUDE = 100.0;
        final double STARTING_LONGITUDE = 150.0;
        final double ENDING_LATITUDE = -100.0;
        final double ENDING_LONGITUDE = -90.0;

        double distance = testDestination.calculateDistance(STARTING_LATITUDE, STARTING_LONGITUDE,
                ENDING_LATITUDE, ENDING_LONGITUDE);

        assertEquals(EXPECTED_DISTANCE, distance);
    }
}
