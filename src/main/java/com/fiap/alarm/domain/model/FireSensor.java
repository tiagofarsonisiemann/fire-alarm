package com.fiap.alarm.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "fire_sensors")
public class FireSensor {
    @Id
    private String id;
    private String location;
    @Builder.Default
    private boolean active = true;
}