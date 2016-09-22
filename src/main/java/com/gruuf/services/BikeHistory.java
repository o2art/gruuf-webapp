package com.gruuf.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;

import java.util.List;

public class BikeHistory extends Reindexable<BikeEvent> {

    public List<BikeEvent> get(Bike bike) {
        return ObjectifyService
                .ofy()
                .load()
                .type(BikeEvent.class)
                .filter("bike =", bike)
                .order("-registerDate")
                .list();
    }

    public void register(BikeEvent event) {
        ObjectifyService
                .ofy()
                .save()
                .entity(event)
                .now();
    }

    public List<EventType> listEventTypes() {
        return ObjectifyService
                .ofy()
                .load()
                .type(EventType.class)
                .order("name")
                .list();
    }

    public Key<EventType> addEventType(EventType event) {
        return ObjectifyService
                .ofy()
                .save()
                .entity(event)
                .now();
    }

    @Override
    public void put(BikeEvent entity) {
        register(entity);
    }

    @Override
    public List<BikeEvent> list() {
        return ObjectifyService
                .ofy()
                .load()
                .type(BikeEvent.class)
                .list();
    }
}
