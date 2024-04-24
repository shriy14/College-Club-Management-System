package com.example.RecipeManager.controller;

import com.example.RecipeManager.Model.Event;
import com.example.RecipeManager.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private EventService eventService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "home";
    }

    
}