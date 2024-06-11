package org.predictionprovider.control;

import org.predictionprovider.model.WeatherController;
import org.predictionprovider.model.WeatherProviderImpl;
import org.predictionprovider.variables.Location;

import java.util.ArrayList;
import java.util.Timer;

public class Main {
    public static void main(String[] args){
        String apikey = args[0];
        ArrayList<Location> locations = Location.islandCoordinates();
        WeatherProviderImpl weatherApi = new WeatherProviderImpl(apikey);
        Timer timer = new Timer();
        WeatherController updater = new WeatherController(weatherApi, locations);
        timer.schedule(updater, 0, 21600000);
    }
}
