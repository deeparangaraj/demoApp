package com.task.station.api;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class VerifyStationAPIs 
{
	
	@Test
	public void getHDStations()
	{
		given().
		when().
			get("http://localhost:8090/api/stations/hd").
		then().
			assertThat().
			statusCode(200);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void addHDStation()
	{
		// When resource is created on the server side, the returning http status code must 
		// be 201 instead of 200
		JSONObject station = new JSONObject();
		station.put("stationName","Austin");
		station.put("callSign","start");
		station.put("hdEnabled","true");
		
		given().
			contentType("application/json").
			body(station.toString()).
		when().
			post("http://localhost:8090/api/station").
		then().
			assertThat().
			statusCode(200);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findHDStation()
	{
		
		JSONObject station = new JSONObject();
		station.put("stationName","Houston");
		station.put("callSign","start");
		station.put("hdEnabled",true);
		
		String stationID = given().
			contentType("application/json").
			body(station.toString()).
		when().
			post("http://localhost:8090/api/station").
		then().
			assertThat().
			statusCode(200).
		extract().
			path("stationId");
		
		given().
			contentType("application/json").
			body(station.toString()).
		when().
			get("http://localhost:8090/api/station/find?name=\"" + stationID + "\"").
		then().
			assertThat().
			statusCode(200);
			
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void updateHDStation()
	{
		JSONObject station = new JSONObject();
		station.put("stationName","Houston");
		station.put("callSign","start");
		station.put("hdEnabled",true);
		
		String stationID = given().
			contentType("application/json").
			body(station.toString()).
		when().
			post("http://localhost:8090/api/station").
		then().
			assertThat().
			statusCode(200).
		extract().
			path("stationId");
		
		System.out.println("stationID : " + stationID);
		
		//For the above posted station update hdEnabled field and verify if updated
		station.clear();
		station.put("stationName","Houston");
		station.put("callSign","start");
		station.put("hdEnabled",false);
		
		Boolean hdEnabled = given().
			contentType("application/json").
			body(station.toString()).
		when().
			put("http://localhost:8090/api/station/" + stationID).
		then().
			assertThat().
			statusCode(200).
		extract().
			path("hdEnabled");
		
		Assert.assertFalse(hdEnabled);
			

		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void deleteHDStation()
	{
		
		JSONObject station = new JSONObject();
		station.put("stationName","Houston");
		station.put("callSign","start");
		station.put("hdEnabled",true);
		
		String stationID = given().
			contentType("application/json").
			body(station.toString()).
		when().
			post("http://localhost:8090/api/station").
		then().
			assertThat().
			statusCode(200).
		extract().
			path("stationId");
		
		System.out.println("stationID : " + stationID);
		
		//For the above posted station update hdEnabled field and verify if updated
		station.clear();
		station.put("stationName","Houston");
		station.put("callSign","start");
		station.put("hdEnabled",false);
		
		given().
			contentType("application/json").
			body(station.toString()).
		when().
			delete("http://localhost:8090/api/station/" + stationID).
		then().
			assertThat().
			statusCode(204);
		
		given().
			contentType("application/json").
			body(station.toString()).
		when().
			get("http://localhost:8090/api/station/" + stationID).
		then().
			assertThat().
			statusCode(404);
		
		
	}

}
