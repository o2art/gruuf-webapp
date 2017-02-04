package com.gruuf.model;

import com.gruuf.web.actions.bike.BikeMetadataOption;

import java.util.ArrayList;
import java.util.List;

public class BikeDetails {

    private Bike bike;
    private User currentUser;
    private List<BikeEventDescriptor> events;
    private Long mileage;

    public static BikeDetails create(Bike bike) {
        return new BikeDetails(bike);
    }

    public BikeDetails(Bike bike) {
        this.bike = bike;
    }

    public BikeDetails withUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public BikeDetails withHistory(List<BikeEvent> bikeEvents) {
        events = new ArrayList<>();
        for (BikeEvent event : bikeEvents) {
            events.add(new BikeEventDescriptor(event));
        }
        return this;
    }

    public BikeDetails withMileage(Long mileage) {
        this.mileage = mileage;
        return this;
    }

    public Bike getBike() {
        return bike;
    }

    public BikeMetadataOption getMetadata() {
        return new BikeMetadataOption(bike.getBikeMetadata());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<BikeEventDescriptor> getEvents() {
        return events;
    }

    public Long getMileage() {
        return mileage;
    }

    @Override
    public String toString() {
        return "BikeDetails{" +
                "bike=" + bike +
                ", currentUser=" + currentUser +
                ", events=" + events +
                ", mileage=" + mileage +
                '}';
    }
}
