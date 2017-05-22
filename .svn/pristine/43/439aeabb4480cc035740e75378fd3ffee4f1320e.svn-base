package com.aarongreen.fastfood.ReviewActivityFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AaronGreen on 4/9/17.
 *
 * Class which holds the information about a single review, which is to be stored in the Firebase
 * Database
 */

public class Review {

    public static final String UPVOTES = "upvotes";
    public static final String DOWNVOTES = "downvotes";
    public static final String PARENT_RESTAURANT_ID = "parentRestaurantID";
    public static final String REVIEW_TEXT = "reviewText";
    public static final String TIME_CREATED = "timeCreated";

    private String reviewText;
    private long timeCreated;
    private String parentRestaurantID;
    private List<String> upvoteList;
    private List<String> downvoteList;

    /**
     * Used to build a new Review object
     *
     * @param reviewText the content of the new review
     */
    public Review(String reviewText, String parentRestaurantID) {
        this.reviewText = reviewText;
        this.timeCreated = System.currentTimeMillis();
        this.parentRestaurantID = parentRestaurantID;
        this.upvoteList = new ArrayList<>();
        this.downvoteList = new ArrayList<>();
    }

    /**
     * Used to build review objects when retrieved from the Firebase database
     *
     * @param reviewText the content of the review
     * @param timeCreated the time the review was initially created
     * @param parentRestaurantID the restaurant to which the review belongs
     */
    public Review(String reviewText, long timeCreated, List<String> upvoteList,
                  List<String> downvoteList, String parentRestaurantID) {
        this.reviewText = reviewText;
        this.timeCreated = timeCreated;
        this.upvoteList = upvoteList;
        this.downvoteList = downvoteList;
        this.parentRestaurantID = parentRestaurantID;
    }

    /**
     * Default constructor to allow Firebase uploads
     */
    public Review() {

    }

    /**
     * Review property access functions
     */

    public long getTimeCreated() {
        return timeCreated;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public long getVoteTotal() {
        long upvoteSize = (upvoteList == null) ? 0 : upvoteList.size();
        long downvoteSize = (downvoteList == null) ? 0 : downvoteList.size();
        return (upvoteSize - downvoteSize);
    }

    public String getParentRestaurantID() {
        return parentRestaurantID;
    }

    /**
     * Allowed functions for manipulating the upvoteList
     */

    public List<String> getUpvoteList() {
        return upvoteList;
    }

    public void setUpvoteList(List<String> upvoteList) {
        this.upvoteList = upvoteList;
    }

    public boolean appendToUpvoteList(String deviceID) {
        if (!upvoteList.contains(deviceID)) {
            upvoteList.add(deviceID);
            return true;
        }
        return false;
    }

    public boolean removeFromUpvoteList(String deviceID) {
        if (upvoteList.contains(deviceID)) {
            upvoteList.remove(deviceID);
            return true;
        }
        return false;
    }

    /**
     * Allowed functions for manipulating the downvoteList
     */

    public void setDownvoteList(List<String> downvoteList) {
        this.downvoteList = downvoteList;
    }

    public List<String> getDownvoteList() {
        return downvoteList;
    }

    public boolean appendToDownvoteList(String deviceID) {
        if (!downvoteList.contains(deviceID)) {
            downvoteList.add(deviceID);
            return true;
        }
        return false;
    }

    public boolean removeFromDownvoteList(String deviceID) {
        if (downvoteList.contains(deviceID)) {
            downvoteList.remove(deviceID);
            return true;
        }
        return false;
    }
}
