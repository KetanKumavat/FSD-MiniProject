package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "venueid")
    private int venueId;

    @Column(name = "roomnumber")
    private int roomNumber;

    @Column(name = "floor_number")
    private int floorNumber;

    // Getters and Setters
    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}