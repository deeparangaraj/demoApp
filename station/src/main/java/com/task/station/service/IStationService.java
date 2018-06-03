package com.task.station.service;

import com.task.station.domain.Station;
import com.task.station.error.StationNotFoundException;

import java.util.List;

public interface IStationService {

    /**
     * created new station
     * @param station object
     * @return saved station object
     */
    Station create(Station station);

    /**
     * finds stations by name
     * @param name of station
     * @return A list of stations which name exact match with given name.
     * If no station is found, this method returns empty list.
     */
    List<Station> findByName(String name);

    /**
     * finds station by id
     * @param stationId station id
     * @return station object
     */
    Station findById(String stationId);

    /**
     * updates station
     * @param station station object
     * @return updated object
     */
    Station update(Station station) throws StationNotFoundException;

    /**
     * deletes station by id
     * @param stationId station id
     */
    void delete(String stationId) throws StationNotFoundException;

    /**
     * finds stations with hd enabled
     * @return A list of hd enabled stations
     */
    List<Station> getHdEnabled();
}
