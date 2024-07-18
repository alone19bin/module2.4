package org.maxim.RestApi.DTO.Converter;

import org.maxim.RestApi.DTO.EventDTO;
import org.maxim.RestApi.model.Event;

public class EventConverter {
    public static EventDTO toDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setName(event.getName());
        eventDTO.setCreated(event.getCreated());
        eventDTO.setUser(UserConverter.toDTO(event.getUser()));
        eventDTO.setFile(FileConverter.toDTO(event.getUFile()));
        return eventDTO;
    }

    public static Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setCreated(eventDTO.getCreated());
        event.setUser(UserConverter.toEntity(eventDTO.getUser()));
        event.setUFile(FileConverter.toEntity(eventDTO.getFile()));
        return event;
    }
}

