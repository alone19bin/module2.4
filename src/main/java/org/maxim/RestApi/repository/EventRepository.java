package org.maxim.RestApi.repository;

import org.maxim.RestApi.model.Event;

import java.util.List;

public interface EventRepository extends GenericRepository<Event, Integer> {
    Event create(Event event);
    List<Event> getAll();

}
