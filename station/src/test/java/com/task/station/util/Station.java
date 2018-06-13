package com.task.station.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Station 
{
	public String stationId;
	public String stationName;
	public String callSign;
	public boolean hdEnabled;
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String toJsonString()
	{
		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
	}

}
