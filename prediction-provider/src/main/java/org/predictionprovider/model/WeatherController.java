package org.predictionprovider.model;

import org.predictionprovider.model.WeatherStore;
import org.predictionprovider.variables.Location;
import org.predictionprovider.variables.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class WeatherController extends TimerTask {
    private WeatherProviderImpl weatherProvider;
    private ArrayList<Location> locations;
    private WeatherStore weatherStore;

    public WeatherController(WeatherProviderImpl weatherProvider, ArrayList<Location> locations, WeatherStore weatherStore) {
        this.weatherProvider = weatherProvider;
        this.locations = locations;
        this.weatherStore = weatherStore;
    }

    @Override
    public void run() {
        for (Location location : locations) {
            List<Weather> weatherData = weatherProvider.getWeatherData(location);
            weatherStore.saveWeatherData(location, weatherData);
            System.out.println("Weather data for location " + location.getName() + " has been updated.");
        }
    }
}
