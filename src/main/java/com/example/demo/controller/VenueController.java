package com.example.demo.controller;

import com.example.demo.entity.Venue;
import com.example.demo.repository.VenueRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/venues")
@Tag(name = "Venue Controller", description = "Venue Controller")

public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @GetMapping("/{venueId}")
    public ResponseEntity<Venue> getVenueById(@PathVariable int venueId) {
        Optional<Venue> venue = venueRepository.findById(venueId);
        return venue.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Venue savedVenue = venueRepository.save(venue);
        return new ResponseEntity<>(savedVenue, HttpStatus.CREATED);
    }

    @PutMapping("/update/{venueId}")
    public ResponseEntity<Venue> updateVenue(@PathVariable int venueId, @RequestBody Venue venueDetails) {
        return venueRepository.findById(venueId)
                .map(existingVenue -> {
                    existingVenue.setRoomNumber(venueDetails.getRoomNumber());
                    existingVenue.setFloorNumber(venueDetails.getFloorNumber());
                    Venue updatedVenue = venueRepository.save(existingVenue);
                    return new ResponseEntity<>(updatedVenue, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/patch/{venueId}")
    public ResponseEntity<Venue> patchVenue(@PathVariable int venueId, @RequestBody Venue venueDetails) {
        return venueRepository.findById(venueId)
                .map(existingVenue -> {
                    if (venueDetails.getRoomNumber() != 0) {
                        existingVenue.setRoomNumber(venueDetails.getRoomNumber());
                    }
                    if (venueDetails.getFloorNumber() != 0) {
                        existingVenue.setFloorNumber(venueDetails.getFloorNumber());
                    }
                    Venue patchedVenue = venueRepository.save(existingVenue);
                    return new ResponseEntity<>(patchedVenue, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{venueId}")
    public ResponseEntity<Void> deleteVenue(@PathVariable int venueId) {
        if (venueRepository.existsById(venueId)) {
            venueRepository.deleteById(venueId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}