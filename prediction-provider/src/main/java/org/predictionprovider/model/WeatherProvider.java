package org.predictionprovider.model;

import org.predictionprovider.variables.Location;
import org.predictionprovider.variables.Weather;
import java.util.List;

public interface WeatherProvider {
    List<Weather> getWeatherData(Location location);
}
