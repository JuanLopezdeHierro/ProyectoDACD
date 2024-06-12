package org.predictionprovider.model;

import org.predictionprovider.variables.Location;
import org.predictionprovider.variables.Weather;

import java.util.List;

public interface WeatherStore {
    void saveWeatherData(Location location, List<Weather> weatherData);
    List<Weather> getWeatherForecast(Location location);
}
