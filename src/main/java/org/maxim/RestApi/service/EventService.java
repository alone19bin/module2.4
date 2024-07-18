package org.maxim.RestApi.service;

import org.maxim.RestApi.DTO.Converter.EventConverter;
import org.maxim.RestApi.DTO.EventDTO;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.repository.EventRepository;
import org.maxim.RestApi.repository.hiber.HibernateEventRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class EventService {
    private final EventRepository eventRepository;

    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = EventConverter.toEntity(eventDTO);
        Event createdEvent = eventRepository.create(event);
        return EventConverter.toDTO(createdEvent);
    }

    public void deleteEvent(Integer id) {
        eventRepository.delete(id);
    }

    public EventDTO getEvent(Integer id) {
        Event event = eventRepository.get(id);
        return EventConverter.toDTO(event);
    }

    public List<EventDTO> getAll() {
        return eventRepository.getAll().stream()
                .map(EventConverter::toDTO)
                .collect(Collectors.toList());
    }
}

