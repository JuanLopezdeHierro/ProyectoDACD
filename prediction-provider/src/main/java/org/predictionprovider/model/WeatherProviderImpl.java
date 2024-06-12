package org.predictionprovider.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.predictionprovider.variables.Location;
import org.predictionprovider.variables.Weather;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherProviderImpl implements WeatherProvider {
    private String apiKey;

    public WeatherProviderImpl(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Weather> getWeatherData(Location location) {
        String url = buildApiUrl(location);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error code : " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return parseWeatherData(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private String buildApiUrl(Location location) {
        return String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&units=metric&appid=%s",
                location.getLatitude(), location.getLongitude(), apiKey);
    }

    private List<Weather> parseWeatherData(String jsonData) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        JsonArray listArray = jsonObject.getAsJsonArray("list");

        List<Weather> weatherList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (JsonElement element : listArray) {
            JsonObject weatherObject = element.getAsJsonObject();
            JsonObject main = weatherObject.getAsJsonObject("main");
            JsonArray weatherArray = weatherObject.getAsJsonArray("weather");
            JsonObject weatherDetails = weatherArray.get(0).getAsJsonObject();
            JsonObject wind = weatherObject.getAsJsonObject("wind");
            JsonObject clouds = weatherObject.getAsJsonObject("clouds");

            String timestampString = weatherObject.get("dt_txt").getAsString();
            LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);

            if (timestamp.getHour() == 12 && timestamp.getMinute() == 0 && timestamp.getSecond() == 0
                    && now.plusDays(5).toLocalDate().compareTo(timestamp.toLocalDate()) >= 0) {
                double temp = main.get("temp").getAsDouble();
                double precipitationProbability = weatherObject.has("pop") ? weatherObject.get("pop").getAsDouble() : 0.0;
                double humidity = main.get("humidity").getAsDouble();
                double cloudiness = clouds.get("all").getAsDouble();
                double windSpeed = wind.get("speed").getAsDouble();
                String timestampFormatted = timestamp.format(formatter);

                Weather weather = new Weather(temp, precipitationProbability, humidity, cloudiness, windSpeed, timestampFormatted);
                weatherList.add(weather);
            }
        }
        return weatherList;
    }
}
