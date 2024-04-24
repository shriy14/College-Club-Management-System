package com.example.RecipeManager.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @Column(name = "eventname")
    private String eventName;

    @Column(name = "clubname")
    private String clubName;

    @Column(name = "description")
    private String description;

    @Column(name = "loc")
    private String location;

    @Column(name = "type")
    private String type;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "registrationlink")
    private String registrationLink;

    @Lob
    @Column(name = "banner")
    private byte[] banner;


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getRegistrationLink() {
        return registrationLink;
    }

    public void setRegistrationLink(String registrationLink) {
        this.registrationLink = registrationLink;
    }

    public byte[] getBanner() {
        return banner;
    }

    public void setBanner(byte[] banner) {
        this.banner = banner;
    }
}