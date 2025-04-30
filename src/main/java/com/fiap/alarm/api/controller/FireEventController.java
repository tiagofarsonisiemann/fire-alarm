package com.fiap.alarm.api.controller;

import com.fiap.alarm.domain.model.FireEvent;
import com.fiap.alarm.domain.service.FireService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fire/events")
public class FireEventController {
    private final FireService fireService;
    public FireEventController(FireService fireService) { this.fireService = fireService; }

    @GetMapping
    public List<FireEvent> listEvents() { return fireService.getEvents(); }
    @GetMapping("/sensor/{sensorId}")
    public List<FireEvent> listEventsForSensor(@PathVariable String sensorId) {
        return fireService.getEventsBySensor(sensorId);
    }
    @PostMapping
    public FireEvent createEvent(@RequestBody FireEvent event) {
        event.setStatus("ALERT");
        return fireService.addEvent(event);
    }
    @PutMapping("/{eventId}/resolve")
    public FireEvent resolveEvent(@PathVariable String eventId) {
        return fireService.resolveEvent(eventId);
    }
}
