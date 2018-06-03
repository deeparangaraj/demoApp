package com.task.station.repository;

import com.task.station.domain.Station;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Tests for {@link StationRepository}
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class StationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void findByNameTest() {
        Station station = new Station();
        station.setHdEnabled(true);
        station.setName("Test FM 103.4");
        station.setCallSign("na");
        entityManager.persistAndFlush(station);
        List<Station> stations = stationRepository.findByName("Test FM 103.4");
        Assert.assertEquals(1, stations.size());
    }

    @Test
    public void findHdEnabledTest() {
        Station stationHd = new Station();
        stationHd.setHdEnabled(true);
        stationHd.setName("Test FM 103.4");
        stationHd.setCallSign("na");
        entityManager.persistAndFlush(stationHd);

        Station station = new Station();
        station.setHdEnabled(false);
        station.setName("Test FM 105.9");
        station.setCallSign("na");
        entityManager.persistAndFlush(station);
        List<Station> stations = stationRepository.findHdEnabled();
        Assert.assertEquals(1, stations.size());
        Assert.assertEquals(stationHd.getCallSign(), stations.get(0).getCallSign());
        Assert.assertEquals(stationHd.getName(), stations.get(0).getName());
        Assert.assertEquals(stationHd.getStationId(), stations.get(0).getStationId());
        Assert.assertTrue(stations.get(0).getHdEnabled());
    }
}
