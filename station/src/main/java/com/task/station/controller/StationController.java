package com.task.station.controller;

import com.task.station.controller.model.CreateUpdateStationRequest;
import com.task.station.controller.model.CreateStationResponse;
import com.task.station.controller.model.StationModel;
import com.task.station.controller.model.StationSearchResult;
import com.task.station.domain.Station;
import com.task.station.error.StationNotFoundException;
import com.task.station.service.IStationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StationController {
    private IStationService stationService;

    public StationController(IStationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping(value = "/station", consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create new station.", response = CreateStationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Station created.", response = CreateStationResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Input validation failed."),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Unexpected error occurred during the search.")
    })
    public ResponseEntity<CreateStationResponse> createStation(@Valid @RequestBody CreateUpdateStationRequest request) {
        Station station = new Station();
        station.setHdEnabled(request.getHdEnabled());
        station.setCallSign(request.getCallSign());
        station.setName(request.getStationName());
        station = stationService.create(station);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateStationResponse(station.getStationId()));
    }

    @DeleteMapping("/station/{id}")
    @ApiOperation(value = "Delete existing station.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Station deleted."),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Input validation failed."),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Specified station not found."),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Unexpected error occurred during the search.")
    })
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {
        try {
            stationService.delete(id);
        } catch (StationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/stations/hd", produces = {"application/json"})
    @ApiOperation(value = "Finds stations with enabled HD.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "List of stations", response = StationSearchResult.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Input validation failed."),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Unexpected error occurred during the search.")
    })
    public ResponseEntity<StationSearchResult> getHdEnabled() {
        List<Station> stations = stationService.getHdEnabled();
        List<StationModel> converted = stations
                .stream()
                .map((s) -> new StationModel(s.getStationId(), s.getName(), s.getCallSign(), s.getHdEnabled()))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StationSearchResult(converted.size(), converted));
    }

    @GetMapping(value = "/station/{id}", produces = {"application/json"})
    @ApiOperation(value = "Find station by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Requested station.", response = StationModel.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Input validation failed."),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Specified station not found."),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Unexpected error occurred during the search.")
    })
    public ResponseEntity<StationModel> getStationById(@PathVariable(name = "id") String id) {
        Station station = stationService.findById(id);
        if (station == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StationModel(station.getStationId(), station.getName(), station.getCallSign(), station.getHdEnabled()));
    }
    @GetMapping(value = "/station/find", produces = {"application/json"})
    @ApiOperation(value = "Find station by Name.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "List of stations.", response = StationSearchResult.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Input validation failed."),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Unexpected error occurred during the search.")
    })
    public ResponseEntity<StationSearchResult> findByName(@RequestParam(name = "name") @ApiParam(required = true) String name) {
        List<Station> found = stationService.findByName(name);
        List<StationModel> converted = found
                .stream()
                .map((s) -> new StationModel(s.getStationId(), s.getName(), s.getCallSign(), s.getHdEnabled()))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StationSearchResult(converted.size(), converted));
    }

    @PutMapping(value = "/station/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Update existing station.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Station updated.", response = StationModel.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Input validation failed."),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Specified station not found."),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Unexpected error occurred during the search.")
    })
    public ResponseEntity<StationModel> updateStation(@PathVariable(name = "id") String id,
                                                      @Valid @RequestBody CreateUpdateStationRequest request) {
        Station station =new Station(id, request.getStationName(), request.getCallSign(), request.getHdEnabled());
        try {
            station = stationService.update(station);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new StationModel(station.getStationId(), station.getName(), station.getCallSign(), station.getHdEnabled()));
        } catch (StationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
