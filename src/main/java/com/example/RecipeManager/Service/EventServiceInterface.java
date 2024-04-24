package com.example.RecipeManager.Service;

import com.example.RecipeManager.Model.Event;
import java.util.List;

public interface EventServiceInterface {
    List<Event> getAllEvents();
    Event createEvent(Event event);
}