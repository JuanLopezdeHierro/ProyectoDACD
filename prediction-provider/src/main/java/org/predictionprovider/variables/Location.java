package org.predictionprovider.variables;

import java.util.ArrayList;

public class Location {
    private String name;
    private double latitude;
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static ArrayList<Location> islandCoordinates() {
        return new ArrayList<>() {
            {
                add(new Location("Gran_Canaria", 28.0997, -15.4134));
                add(new Location("Fuerteventura", 28.5004, -13.8627));
                add(new Location("Lanzarote", 28.963, -13.5477));
                add(new Location("La_Graciosa", 29.255217, -13.504110));
                add(new Location("Tenerife", 28.5392, -16.203));
                add(new Location("La_Gomera", 28.0916, -17.1133));
                add(new Location("La_Palma", 28.6835, -17.7642));
                add(new Location("El_Hierro", 27.8063, -17.9158));
            }
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
