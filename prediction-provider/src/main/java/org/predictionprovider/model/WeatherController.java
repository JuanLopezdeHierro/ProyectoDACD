package org.predictionprovider.model;

import org.predictionprovider.variables.Location;
import org.predictionprovider.variables.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class WeatherController extends TimerTask {
    private WeatherProviderImpl weatherProvider;
    private ArrayList<Location> locations;

    public WeatherController(WeatherProviderImpl weatherProvider, ArrayList<Location> locations) {
        this.weatherProvider = weatherProvider;
        this.locations = locations;
    }

    @Override
    public void run() {
        for (Location location : locations) {
            List<Weather> weatherData = weatherProvider.getWeatherData(location);
            // Aquí puedes procesar los datos del clima, imprimirlos o almacenarlos
            System.out.println("Weather data for location " + location + ": " + weatherData);
        }
    }
}
