package org.predictionprovider.variables;

public class Weather {
    private double temp;
    private double precipitationProbability;
    private double humidity;
    private double clouds;
    private double windSpeed;
    private String timestamp;

    public Weather(double temp, double precipitationProbability, double humidity, double clouds, double windSpeed, String timestamp) {
        this.temp = temp;
        this.precipitationProbability = precipitationProbability;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.timestamp = timestamp;
    }

    public double getTemp() {
        return temp;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getClouds() {
        return clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setPrecipitationProbability(double precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
