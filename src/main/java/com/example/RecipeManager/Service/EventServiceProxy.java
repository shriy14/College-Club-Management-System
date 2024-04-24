package com.example.RecipeManager.Service;

import com.example.RecipeManager.Model.Event;
import java.util.List;

public class EventServiceProxy implements EventServiceInterface {
    private EventService eventService;

    public EventServiceProxy(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.createEvent(event);
    }
}