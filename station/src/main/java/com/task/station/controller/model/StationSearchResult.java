package com.task.station.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @AllArgsConstructor
public class StationSearchResult {

    private int count;
    List<StationModel> stations;


}
