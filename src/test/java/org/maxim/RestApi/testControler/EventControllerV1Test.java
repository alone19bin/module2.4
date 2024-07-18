package org.maxim.RestApi.testControler;


import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maxim.RestApi.Controller.EventControllerV1;
import org.maxim.RestApi.DTO.EventDTO;
import org.maxim.RestApi.service.EventService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class EventControllerV1Test {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventControllerV1 eventController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testGetAllEvents() throws Exception {
        when(request.getRequestURI()).thenReturn("/events/");
        List<EventDTO> events = Collections.singletonList(new EventDTO());
        when(eventService.getAll()).thenReturn(events);

        eventController.doGet(request, response);


    }

    @Test
    public void testGetEventById() throws Exception {
        when(request.getRequestURI()).thenReturn("/events/1");
        EventDTO event = new EventDTO();
        event.setId(1);
        when(eventService.getEvent(1)).thenReturn(event);

        eventController.doGet(request, response);


    }
}



