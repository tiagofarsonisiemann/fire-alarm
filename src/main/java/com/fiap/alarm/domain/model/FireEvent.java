package com.fiap.alarm.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "fire_events")
public class FireEvent {
    @Id
    private String id;
    private String sensorId;
    private String status; // ALERT ou RESOLVED
    private String description;
    private String timestamp; // ISO-8601
}