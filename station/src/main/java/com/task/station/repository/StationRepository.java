package com.task.station.repository;

import com.task.station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Specifies methods used to obtain and modify station related information stored in DB
 */
@Repository
@Transactional
public interface StationRepository extends JpaRepository<Station, String> {

    /**
     * Finds stations by name
     * @param name - station name
     * @return A list of stations which name exact match with given name.
     * If no station is found, this method returns empty list.
     */
    List<Station> findByName(String name);

    /**
     * Finds station with HD enabled
     * @return A list of stations which hdEnabled param is true.
     * If no station is found, this method returns empty list.
     */
    List<Station> findHdEnabled();
}