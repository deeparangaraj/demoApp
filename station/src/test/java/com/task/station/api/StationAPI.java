package com.task.station.api;

import com.task.station.util.*;
import java.util.List;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StationAPI 
{
	
	
	public static List<Station>  getAllHDStation()
	{
		Response response = given().
 							when().
 								get("http://localhost:8090/api/stations/hd").
 							then().
 								statusCode(200).
 							extract().response();
		List<Station> stationList = response.jsonPath().getList("stations", Station.class);
		return stationList;
		
	}
	
	public static Station getStationById(String id)
	{
		Response response = given().
							when().
								get("http://localhost:8090/api/station/" + id).
							then().
								statusCode(200).
							extract().response();
		
		System.out.println(response.asString());
		
		Station station = response.as(Station.class);
		return station;
	}
	
	
	public static String addStation(Station s)
	{
		String stationId = given().
							contentType("application/json").
							body(s.toJsonString()).
						when().
							post("http://localhost:8090/api/station").
						then().
							assertThat().
							statusCode(200).
						extract().path("stationId");
		return stationId;
	}
	
	public static int deleteStation(String id)
	{
		Response response = given().
							when().
								delete("http://localhost:8090/api/station/" + id).
							then().
								assertThat().
								statusCode(204).
							extract().response();
		return response.getStatusCode();
	}
	
	public static Station updateStation(Station s)
	{
		Response response = given().
				contentType("application/json").
				body(s.toJsonString()).
			when().
				put("http://localhost:8090/api/station/" + s.stationId).
			then().
				assertThat().
				statusCode(200).
			extract().response();
				
		Station station = response.as(Station.class);
		
		return station;
			
	}
	
	public static Station findStationByName(String name)
	{
		Response response = given().
				accept(ContentType.JSON).
				param("name", name).
		when().
			get("http://localhost:8090/api/station/find").
		then().
			assertThat().
			statusCode(200).extract().response();
		
		List<Station> stationList = response.jsonPath().getList("stations",Station.class);
		return stationList.get(0);
	}

	
}
