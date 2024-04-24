package com.example.RecipeManager.controller;

import com.example.RecipeManager.Model.Event;
import com.example.RecipeManager.Service.EventService;
import com.example.RecipeManager.Service.EventServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        EventServiceProxy proxy = new EventServiceProxy(eventService);
        return proxy.getAllEvents();
    }
}