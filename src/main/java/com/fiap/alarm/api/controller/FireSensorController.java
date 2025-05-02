
package com.fiap.alarm.api.controller;

import com.fiap.alarm.domain.model.FireSensor;
import com.fiap.alarm.domain.service.FireService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fire/sensorssss")
public class FireSensorController {
    private final FireService fireService;
    public FireSensorController(FireService fireService) { this.fireService = fireService; }

    @PostMapping
    public FireSensor createSensor(@RequestBody FireSensor sensor) { return fireService.addSensor(sensor); }

    @GetMapping
    public List<FireSensor> listSensors() { return fireService.getSensors(); }

    @GetMapping("/{id}")
    public FireSensor getSensor(@PathVariable String id) { return fireService.getSensorById(id).orElseThrow(); }

    @PutMapping("/{id}/activate")
    public FireSensor activateSensor(@PathVariable String id, @RequestParam(defaultValue = "true") boolean active) {
        return fireService.activateSensor(id, active);
    }
    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable String id) { fireService.removeSensor(id); }
}