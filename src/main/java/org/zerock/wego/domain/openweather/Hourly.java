package org.zerock.wego.domain.openweather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class Hourly {

	private Long timestamp;
	private Double temp;
	@JsonProperty("feels_like")
	private Double feelsLike;
	private Long pressure;
	private Long humidity;
	@JsonProperty("dew_point")
	private Double dewPoint;
	private Double uvi;
	private Long clouds;
	private Long visibility;
	@JsonProperty("wind_speed")
	private Double windSpeed;
	@JsonProperty("wind_deg")
	private Long windDeg;
	@JsonProperty("wind_gust")
	private Double windGust;
	private Rain rain;
	private Snow snow;
	private List<Weather> weather;

	@JsonProperty("pop")
	private Double precipitationProbability;
}