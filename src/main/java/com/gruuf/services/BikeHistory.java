package com.gruuf.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.model.BikeEventStatus;

import java.util.List;

public class BikeHistory extends Reindexable<BikeEvent> {

    public BikeHistory(Class<BikeEvent> type) {
        super(type);
    }

    public List<BikeEvent> listByBike(Bike bike) {
        return filter("bike =", bike)
                .filter("status =", BikeEventStatus.NEW)
                .order("-registerDate")
                .list();
    }

    public List<EventType> listEventTypes() {
        return ObjectifyService
                .ofy()
                .load()
                .type(EventType.class)
                .list();
    }

    public Key<EventType> putEventType(EventType event) {
        return ObjectifyService
                .ofy()
                .save()
                .entity(event)
                .now();
    }

    public EventType getEventType(String eventTypeId) {
        return ObjectifyService
                .ofy()
                .load()
                .type(EventType.class)
                .id(eventTypeId)
                .now();
    }
}
