package com.task.station;

import com.task.station.db.StationDB;
import com.task.station.api.StationAPI;
import java.util.List;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import com.task.station.util.*;

public class StationAppIntegrationTests 
{
	
	String stationId = null;
	Station station = null;
	
	//@Test
	public void addStation()
	{
		station = new Station();
		station.stationName = "Tylor";
		station.callSign = "na"; 
		station.hdEnabled = false;
		stationId = StationAPI.addStation(station);
		assertNotNull(stationId);
		boolean found = StationDB.verifyStation(stationId);
		assertTrue(found, "Added station with ID " + stationId + " not found in the DB");
				
	}
	
	//@Test
	public void getStationById()
	{
		Station stationFromDB = StationDB.getStationById(stationId);
		Station stationFromAPI = StationAPI.getStationById(stationId);
			
		assertEquals(stationFromAPI.toString(),stationFromDB.toString());
				
	}
	
	//@Test
	public void updateStation()
	{
		Station updateStation = new Station();
		updateStation.stationId = stationId;
		updateStation.stationName = "Tylor";
		updateStation.callSign = "na";
		updateStation.hdEnabled = true;
		
		Station updatedStationInfoFromAPI = StationAPI.updateStation(updateStation);
		assertTrue(updatedStationInfoFromAPI.hdEnabled, "Error updating hdEnabled");
		
		Station updatedStationInfoFromDB = StationDB.getStationById(stationId);
		
		assertEquals(updatedStationInfoFromAPI.toString(),updatedStationInfoFromDB.toString());
		
	}
	
	
	
	//@Test
	public void getAllHDStations() 
	{
		List<Station> stationsFromDB =  StationDB.getAllHDStation();
		List<Station> stationsFromAPI = StationAPI.getAllHDStation();
		
		System.out.println("stationsFromDB Size " + stationsFromDB.size());
		System.out.println("stationsFromAPI" + stationsFromAPI.size());
		
		assertEquals(stationsFromDB.size(),stationsFromAPI.size());
		
		for ( int i = 0 ; i < stationsFromAPI.size(); i++ )
		{
			assertEquals(stationsFromAPI.get(i).toString(),stationsFromDB.get(i).toString());
		}

	}
	
	@Test
	public void findStationByName()
	{
		
		Station stationFromAPI = StationAPI.findStationByName(station.stationName);
		Station stationFromDB = StationDB.findStationByName(station.stationName);
		
		assertEquals(stationFromAPI.toString(),stationFromDB.toString());
	}
	
	
	//@Test
	public void deleteStation()
	{
		int responseCode = StationAPI.deleteStation(stationId);
		assertEquals(204, responseCode);
		boolean found = StationDB.verifyStation(stationId);
		assertFalse(found, "Added station with ID " + stationId + "found in the DB");
	}	

	
}
