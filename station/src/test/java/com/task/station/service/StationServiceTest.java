package com.task.station.service;

import com.task.station.domain.Station;
import com.task.station.error.StationNotFoundException;
import com.task.station.repository.StationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
public class StationServiceTest {

    @TestConfiguration
    static class StationServiceTestContextConfiguration {

        @Bean
        public IStationService stationService() {
            return new StationService(stationRepository);
        }

        @MockBean
        public StationRepository stationRepository;
    }

    @Autowired
    private IStationService stationService;

    @Autowired
    private StationRepository stationRepository;


    private Station station1, station2;

    @Before
    public void setUp() {
        station1 = new Station();
        station1.setStationId("test-id-1");
        station1.setCallSign("na");
        station1.setName("Test FM 105.3");
        station1.setHdEnabled(true);
        Mockito.when(stationRepository.save(any(Station.class))).thenReturn(station1);

        station2 = new Station();
        station2.setStationId("test-id-2");
        station2.setCallSign("na");
        station2.setName("Test FM 105.3");
        station2.setHdEnabled(false);

        Mockito.when(stationRepository.findByName(station1.getName())).thenReturn(Arrays.asList(station1, station2));
        Mockito.when(stationRepository.findOne("test-id-1")).thenReturn(station1);
        Mockito.when(stationRepository.exists("test-id-1")).thenReturn(true);
        Mockito.doThrow(new EmptyResultDataAccessException(0)).when(stationRepository).delete("test-wrong-id");
        Mockito.when(stationRepository.findHdEnabled()).thenReturn(Collections.singletonList(station1));

    }

    @Test
    public void createStationTest() {
        Station station = new Station();
        station = stationService.create(station);
        Assert.assertEquals(station1, station);
    }

    @Test
    public void findByNameTest() {
        List<Station> stations = stationService.findByName("Test FM 105.3");
        Assert.assertEquals(2, stations.size());
        Assert.assertEquals(station1, stations.get(0));
        Assert.assertEquals(station2, stations.get(1));
    }

    @Test
    public void findByIdTest() {
        Station station = stationService.findById("test-id-1");
        Assert.assertEquals(station1, station);
    }

    @Test
    public void updateStationTest() throws Exception {
        Station station = stationService.update(station1);
        Assert.assertEquals(station1, station);
    }

    @Test
    public void updateFailTest() {
        try {
            stationService.update(station2);
            Assert.assertTrue("update should throws StationNotFoundException", false);
        } catch (StationNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void deleteStationTest() throws Exception {
        stationService.delete("test-id-1");
    }

    @Test
    public void deleteStationFailTest() throws Exception {
        try {
            stationService.delete("test-wrong-id");
            Assert.assertTrue("delete should throws StationNotFoundException", false);
        } catch (StationNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getHdEnabledTest() {
        List<Station> stations = stationService.getHdEnabled();
        Assert.assertEquals(1, stations.size());
        Assert.assertEquals(station1, stations.get(0));
    }


}

