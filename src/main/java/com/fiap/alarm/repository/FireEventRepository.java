package com.fiap.alarm.repository;

import com.fiap.alarm.domain.model.FireEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FireEventRepository extends MongoRepository<FireEvent, String> {
    List<FireEvent> findBySensorId(String sensorId);
}