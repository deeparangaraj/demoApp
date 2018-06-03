package com.task.station.service;

import com.task.station.domain.Station;
import com.task.station.error.StationNotFoundException;
import com.task.station.repository.StationRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService implements IStationService {

    private static final Logger LOGGER = LogManager.getLogger(StationService.class);

    private StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Station create(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public List<Station> findByName(String name) {
        return stationRepository.findByName(name);
    }

    @Override
    public Station findById(String stationId) {
        return stationRepository.findOne(stationId);
    }

    @Override
    public Station update(Station station) throws StationNotFoundException {
        if (!stationRepository.exists(station.getStationId())) {
            throw new StationNotFoundException("Station with Id :" + station.getStationId() + " not found.");
        }
        stationRepository.save(station);
        return station;
    }

    @Override
    public void delete(String stationId) throws StationNotFoundException {
        try {
            stationRepository.delete(stationId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Station with Id :" + stationId + " not found.", e);
            throw new StationNotFoundException("Station with Id :" + stationId + " not found.", e);
        }
    }

    /**
     * {@link IStationService#getHdEnabled()}
     */
    @Override
    public List<Station> getHdEnabled() {
        return stationRepository.findHdEnabled();
    }
}
