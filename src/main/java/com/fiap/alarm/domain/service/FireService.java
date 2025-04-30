package com.fiap.alarm.domain.service;

import com.fiap.alarm.domain.model.FireEvent;
import com.fiap.alarm.domain.model.FireSensor;
import com.fiap.alarm.repository.FireEventRepository;
import com.fiap.alarm.repository.FireSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class FireService {
    @Autowired private FireSensorRepository sensorRepo;
    @Autowired private FireEventRepository eventRepo;

    // SENSORS
    public List<FireSensor> getSensors() { return sensorRepo.findAll(); }
    public Optional<FireSensor> getSensorById(String id) { return sensorRepo.findById(id); }
    public FireSensor addSensor(FireSensor sensor) { return sensorRepo.save(sensor); }
    public void removeSensor(String id) { sensorRepo.deleteById(id); }
    public FireSensor activateSensor(String id, boolean active) {
        FireSensor sensor = sensorRepo.findById(id).orElseThrow();
        sensor.setActive(active);
        return sensorRepo.save(sensor);
    }

    // EVENTS
    public List<FireEvent> getEvents() { return eventRepo.findAll(); }
    public List<FireEvent> getEventsBySensor(String sensorId) { return eventRepo.findBySensorId(sensorId); }
    public FireEvent addEvent(FireEvent event) {
        event.setTimestamp(Instant.now().toString());
        return eventRepo.save(event);
    }
    public FireEvent resolveEvent(String eventId) {
        FireEvent event = eventRepo.findById(eventId).orElseThrow();
        event.setStatus("RESOLVED");
        return eventRepo.save(event);
    }
}