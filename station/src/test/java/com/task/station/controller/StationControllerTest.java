package com.task.station.controller;

import com.google.gson.Gson;
import com.task.station.controller.model.CreateUpdateStationRequest;
import com.task.station.domain.Station;
import com.task.station.error.StationNotFoundException;
import com.task.station.service.StationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StationController.class)
public class StationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StationService stationService;

    @Test
    public void createStationTest() throws Exception {
        Station station = new Station();
        station.setStationId("test-id");
        CreateUpdateStationRequest request = new CreateUpdateStationRequest("testName", "na", true);
        given(stationService.create(any())).willReturn(station);
        mvc.perform(post("/api/station")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationId", is(station.getStationId())));
    }

    @Test
    public void getStationByIdTest() throws Exception {
        Station station = new Station("test-id", "testName", "na", true);
        given(stationService.findById("test-id")).willReturn(station);
        mvc.perform(get("/api/station/test-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationId", is("test-id")))
                .andExpect(jsonPath("stationName", is("testName")))
                .andExpect(jsonPath("callSign", is("na")))
                .andExpect(jsonPath("hdEnabled", is(true)));
    }

    @Test
    public void getStationByIdNotFoundTest() throws Exception {
        mvc.perform(get("/api/station/test-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTest() throws Exception {
        doNothing().when(stationService).delete("test-id");
        mvc.perform(delete("/api/station/test-id"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTestNotFound() throws Exception {
        doThrow(new StationNotFoundException("station not found")).when(stationService).delete("test-id");
        mvc.perform(delete("/api/station/test-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getHdEnabledTest() throws Exception {
        Station station = new Station("test-id", "testName", "na", true);
        given(stationService.getHdEnabled()).willReturn(Collections.singletonList(station));
        mvc.perform(get("/api/stations/hd").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("count", is(1)))
                .andExpect(jsonPath("stations[0].hdEnabled", is(true)));
    }

    @Test
    public void findByNameTest() throws Exception {
        Station station = new Station("test-id", "testName", "na", true);
        given(stationService.findByName("testName")).willReturn(Collections.singletonList(station));
        mvc.perform(get("/api/station/find?name=testName").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("count", is(1)))
                .andExpect(jsonPath("stations[0].stationName", is(station.getName())));
    }

    @Test
    public void findByNameBadRequestTest() throws Exception {
        mvc.perform(get("/api/station/find").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateStationTest() throws Exception {
        Station station = new Station();
        station.setStationId("test-id");
        station.setName("testName");
        station.setCallSign("na");
        station.setHdEnabled(true);
        CreateUpdateStationRequest request = new CreateUpdateStationRequest("testName", "na", true);
        given(stationService.update(any())).willReturn(station);
        mvc.perform(put("/api/station/test-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationId", is(station.getStationId())));
    }

    @Test
    public void updateStationNotFoundTest() throws Exception {
        doThrow(new StationNotFoundException("station not found")).when(stationService).update(any());
        CreateUpdateStationRequest request = new CreateUpdateStationRequest("testName", "na", true);
        mvc.perform(put("/api/station/test-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request)))
                .andExpect(status().isNotFound());
    }

}
