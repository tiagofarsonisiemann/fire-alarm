package com.fiap.alarm.repository;

import com.fiap.alarm.domain.model.FireSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FireSensorRepository extends MongoRepository<FireSensor, String> { }