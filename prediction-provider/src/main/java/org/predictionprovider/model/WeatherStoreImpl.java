package org.predictionprovider.model;

import org.predictionprovider.variables.Location;
import org.predictionprovider.variables.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.*;
import java.util.List;

public class WeatherStoreImpl implements WeatherStore {
    private Connection connection;

    public WeatherStoreImpl(String dbUrl) {
        try {
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveWeatherData(Location location, List<Weather> weatherData) {
        String tableName = location.getName().toLowerCase().replace(" ", "_"); // Nombre de la tabla basado en el nombre de la isla
        createTable(tableName); // Crear tabla si no existe

        String selectSql = "SELECT COUNT(*) FROM " + tableName + " WHERE timestamp = ?";
        String insertSql = "INSERT INTO " + tableName + " (temp, precipitation_probability, humidity, clouds, wind_speed, timestamp) VALUES(?,?,?,?,?,?)";
        String updateSql = "UPDATE " + tableName + " SET temp = ?, precipitation_probability = ?, humidity = ?, clouds = ?, wind_speed = ? WHERE timestamp = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            for (Weather weather : weatherData) {
                selectStmt.setString(1, weather.getTimestamp());
                ResultSet rs = selectStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    // Update existing record
                    updateStmt.setDouble(1, weather.getTemp());
                    updateStmt.setDouble(2, weather.getPrecipitationProbability());
                    updateStmt.setDouble(3, weather.getHumidity());
                    updateStmt.setDouble(4, weather.getClouds());
                    updateStmt.setDouble(5, weather.getWindSpeed());
                    updateStmt.setString(6, weather.getTimestamp());
                    updateStmt.executeUpdate();
                } else {
                    // Insert new record
                    insertStmt.setDouble(1, weather.getTemp());
                    insertStmt.setDouble(2, weather.getPrecipitationProbability());
                    insertStmt.setDouble(3, weather.getHumidity());
                    insertStmt.setDouble(4, weather.getClouds());
                    insertStmt.setDouble(5, weather.getWindSpeed());
                    insertStmt.setString(6, weather.getTimestamp());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para crear una tabla para una isla específica si no existe
    private void createTable(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "temp REAL," +
                    "precipitation_probability REAL," +
                    "humidity REAL," +
                    "clouds REAL," +
                    "wind_speed REAL," +
                    "timestamp DATETIME UNIQUE" + // Asegura que los timestamps sean únicos
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Weather> getWeatherForecast(Location location) {
        List<Weather> weatherList = new ArrayList<>();
        String tableName = location.getName().toLowerCase().replace(" ", "_");
        String sql = "SELECT temp, precipitation_probability, humidity, clouds, wind_speed, timestamp FROM " + tableName + " WHERE timestamp >= datetime('now', 'start of day', '+1 day', '-1 second') LIMIT 5";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                double temp = rs.getDouble("temp");
                double precipitationProbability = rs.getDouble("precipitation_probability");
                double humidity = rs.getDouble("humidity");
                double clouds = rs.getDouble("clouds");
                double windSpeed = rs.getDouble("wind_speed");
                String timestamp = rs.getString("timestamp");
                weatherList.add(new Weather(temp, precipitationProbability, humidity, clouds, windSpeed, timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherList;
    }
}

