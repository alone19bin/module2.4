package org.maxim.RestApi.service;

import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.repository.EventRepository;
import org.maxim.RestApi.repository.hiber.HibernateEventRepositoryImpl;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }
    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
    }

    public Event createEvent(Event event) {
        return eventRepository.create(event);
    }

    public void deleteEvent(Integer id) {
        eventRepository.delete(id);
    }

    public Event getEvent(Integer id) {
        return eventRepository.get(id);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }

}
