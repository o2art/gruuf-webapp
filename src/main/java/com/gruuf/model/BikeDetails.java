package com.gruuf.model;

import com.gruuf.web.actions.bike.BikeMetadataOption;

import java.util.ArrayList;
import java.util.List;

public class BikeDetails {

    private Bike bike;
    private User currentUser;
    private List<BikeEventDescriptor> events;
    private Long mileage;
    private Long mth;

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

    public BikeDetails withHistory(UserLocale locale, List<BikeEvent> bikeEvents, Long currentMileage, Long currentMth) {
        mileage = currentMileage;
        mth = currentMth;

        events = new ArrayList<>();
        for (BikeEvent event : bikeEvents) {
            events.add(new BikeEventDescriptor(locale, event, currentMileage, currentMth));
        }
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

    public Long getMth() {
        return mth;
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
