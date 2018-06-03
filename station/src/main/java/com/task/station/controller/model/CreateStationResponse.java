package com.task.station.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter @Service @AllArgsConstructor @NoArgsConstructor
public class CreateStationResponse {
    private String stationId;
}
