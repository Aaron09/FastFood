package com.aarongreen.fastfood.DestinationActivityFiles;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aarongreen.fastfood.Photo;
import com.aarongreen.fastfood.R;
import com.aarongreen.fastfood.ReviewActivityFiles.ReviewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AaronGreen on 4/6/17.
 *
 * Adapter for the putting the Destination views into the Recycler View
 */

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    public static final String DESTINATION_KEY = "DESTINATION";
    public static final String LOCATION_KEY = "LOCATION";

    private ArrayList<Destination> destinations;
    private Location currentLocation;

    public DestinationAdapter(Location currentLocation, ArrayList<Destination> destinations) {
        this.destinations = destinations;
        this.currentLocation = currentLocation;
    }

    /**
     *
     * @param parent the parent ViewGroup to which the particular view belongs
     * @param viewType the type of the particular view
     * @return A ViewHolder containing the destination view
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View destinationItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.destination_item, parent, false);
        return new ViewHolder(destinationItem);
    }

    /**
     *
     * @param holder the ViewHolder containing the destination view
     * @param position the position of the destination in the list of destinations
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Destination destination = destinations.get(position);
        holder.nameTextView.setText(destination.getName());
        holder.ratingTextView.setText(Destination.RATING_PREFIX +
                destination.getRating() + Destination.RATING_SUFFIX);
        holder.priceTextView.setText(Destination.PRICE_PREFIX + destination.getPriceDollars());

        double roundedDistance = Destination.roundDistance(destination.getDistance(currentLocation));
        holder.distanceTextView.setText(roundedDistance + Destination.DISTANCE_SUFFIX);

        if (destination.getFirstPhoto().getPhotoURL().contains(Photo.DEFAULT_REFERENCE)) {
            Picasso.with(holder.destinationImageView.getContext()).load(Photo.DEFAULT_BACKGROUND)
                    .into(holder.destinationImageView);
        } else {
            Picasso.with(holder.destinationImageView.getContext()).load(
                    destination.getFirstPhoto().getPhotoURL()).into(holder.destinationImageView);
        }

        holder.destinationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                intent.putExtra(DESTINATION_KEY, destination);
                intent.putExtra(LOCATION_KEY, currentLocation);
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     *
     * @return the number of destinations
     */
    @Override
    public int getItemCount() {
        return destinations.size();
    }

    /**
     * A class to hold the UI information about the destination view it contains
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView ratingTextView;
        public TextView priceTextView;
        public TextView distanceTextView;
        public ImageView destinationImageView;
        public View destinationView;

        public ViewHolder(View destinationView) {
            super(destinationView);
            this.destinationView = destinationView;
            nameTextView = (TextView) destinationView.findViewById(R.id.nameTextView);
            ratingTextView = (TextView) destinationView.findViewById(R.id.ratingTextView);
            priceTextView = (TextView) destinationView.findViewById(R.id.priceTextView);
            distanceTextView = (TextView) destinationView.findViewById(R.id.distanceTextView);
            destinationImageView = (ImageView) destinationView.findViewById(R.id.destination_image);
        }
    }
}
