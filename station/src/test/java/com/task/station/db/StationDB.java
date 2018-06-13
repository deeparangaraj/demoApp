package com.task.station.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.task.station.util.*;

public class StationDB 
{

	
	
	public static List<Station> getAllHDStation()
	{
		ResultSet rs = DBConn.runSQLSelect("SELECT * FROM `station` WHERE hd_enabled=true");
		List<Station> stationList  = new ArrayList<Station>();
		try 
		{
				while(rs.next())
				{
					Station station = new Station();
				
					station.stationId =  rs.getString(1);
					station.stationName = rs.getString(2);
					station.callSign =  rs.getString(3);
					station.hdEnabled =  ( rs.getBoolean(4) == true) ?  true :  false;
					
					stationList.add(station);
				}
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stationList;
		
	}
	
	public static Station getStationById(String id)
	{
		ResultSet rs = DBConn.runSQLSelect("SELECT * FROM `station` WHERE station_id='" + id +"'");
		Station station = new Station();
		try 
		{
				while(rs.next())
				{
					station.stationId =  rs.getString(1);
					station.stationName = rs.getString(2);
					station.callSign =  rs.getString(3);
					station.hdEnabled =  ( rs.getBoolean(4) == true) ?  true :  false;
				}
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return station;
	}
	
	public static boolean verifyStation(String id)
	{
		
		
		ResultSet rs = DBConn.runSQLSelect("SELECT * FROM `station` WHERE station_id='" + id +"'");
		boolean found = false;
		try {
			found = (rs.next() == true) ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return found;
	}
	
	public static Station findStationByName(String name)
	{
		ResultSet rs = DBConn.runSQLSelect("SELECT * FROM `station` WHERE name='" + name +"'");
		Station station = new Station();
		try 
		{
				while(rs.next())
				{
					station.stationId =  rs.getString(1);
					station.stationName = rs.getString(2);
					station.callSign =  rs.getString(3);
					station.hdEnabled =  ( rs.getBoolean(4) == true) ?  true :  false;
				}
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return station;
	}
	
	

}
