package com.task.station.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @AllArgsConstructor
public class StationModel {
    private String stationId;
    private String stationName;
    private String callSign;
    private Boolean hdEnabled;
}
